package mate.academy.springintro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
    private static final int EXPECTED_BOOKS_COUNT = 2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create a new book")
    @Sql(scripts = "classpath:db/categories/add-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/books/remove-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto();

        BookDto expected = TestUtil.createBookDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Delete book")
    @Sql(scripts = "classpath:db/books/add-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/books/remove-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_ValidBookId_Success() throws Exception {
        Long bookId = 1L;

        MvcResult result = mockMvc.perform(delete("/books/{id}", bookId))
                .andReturn();

        int actual = result.getResponse().getStatus();
        assertEquals(HttpStatus.NO_CONTENT.value(), actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Update book")
    @Sql(scripts = "classpath:db/books/add-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/books/remove-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateById_ValidRequestDto_Success() throws Exception {
        Long bookId = 1L;

        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto();

        BookDto expected = TestUtil.createBookDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/books/{id}", bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Find all books")
    @Sql(scripts = "classpath:db/books/add-two-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/books/remove-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode root = objectMapper.readTree(result.getResponse().getContentAsString());

        int actual = root.get("content").size();
        assertEquals(EXPECTED_BOOKS_COUNT, actual);

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Create book with invalid request")
    void createBook_InvalidRequestDto_ReturnsBadRequest() throws Exception {
        CreateBookRequestDto requestDto = TestUtil.createBookRequestDto();
        requestDto.setTitle("");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
