package mate.academy.springintro.controller;

import static mate.academy.springintro.util.TestUtil.createCategoryDto;
import static mate.academy.springintro.util.TestUtil.createCategoryRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mate.academy.springintro.dto.category.CategoryDto;
import mate.academy.springintro.dto.category.CreateCategoryRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {
    private static final int EXPECTED_CATEGORIES_COUNT = 2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create a new category")
    @Sql(scripts = "classpath:db/categories/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategory_WithValidRequestDto_Success() throws Exception {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        CategoryDto expected = createCategoryDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update category")
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = "classpath:db/categories/add-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/categories/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategory_WithValidRequestDto_Success() throws Exception {
        Long categoryId = 1L;
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        CategoryDto expected = createCategoryDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/categories/{id}", categoryId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete category")
    @WithMockUser(roles = "ADMIN")
    @Sql(scripts = "classpath:db/categories/add-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/categories/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCategory_ValidCategoryId_Success() throws Exception {
        Long categoryId = 1L;

        MvcResult result = mockMvc.perform(delete("/categories/{id}", categoryId))
                .andReturn();

        assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Find all categories")
    @Sql(scripts = "classpath:db/categories/add-two-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/categories/remove-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        int actual = objectMapper.readTree(content).get("content").size();
        assertEquals(EXPECTED_CATEGORIES_COUNT, actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create a new category with invalid request")
    void createCategory_WithInvalidRequestDto_ReturnsBadRequest() throws Exception {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        requestDto.setName("");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
