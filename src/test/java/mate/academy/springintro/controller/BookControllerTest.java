package mate.academy.springintro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import mate.academy.springintro.dto.book.BookDto;
import mate.academy.springintro.dto.book.CreateBookRequestDto;
import mate.academy.springintro.security.JwtAuthenticationFilter;
import mate.academy.springintro.security.JwtUtil;
import mate.academy.springintro.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("Create a new book")
    void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Java");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("9781234567897");
        requestDto.setPrice(BigDecimal.valueOf(50));
        requestDto.setDescription("Java book");
        requestDto.setCoverImage("img.jpg");
        requestDto.setCategoryIds(Set.of(1L));

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle("Java");
        expected.setAuthor("Author");
        expected.setIsbn("9781234567897");
        expected.setPrice(BigDecimal.valueOf(50));
        expected.setDescription("Java book");
        expected.setCoverImage("img.jpg");
        expected.setCategoryIds(Set.of(1L));

        when(bookService.save(any(CreateBookRequestDto.class)))
                .thenReturn(expected);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    @DisplayName("Delete book")
    void delete_ValidBookId_Success() throws Exception {
        Long bookId = 1L;

        MvcResult result = mockMvc.perform(delete("/books/{id}", bookId))
                .andReturn();

        assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Update book")
    void updateById_ValidRequestDto_Success() throws Exception {
        Long bookId = 1L;

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Java");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("9781234567897");
        requestDto.setPrice(BigDecimal.valueOf(50));
        requestDto.setDescription("Updated Java book");
        requestDto.setCoverImage("img.jpg");
        requestDto.setCategoryIds(Set.of(1L));

        BookDto expected = new BookDto();
        expected.setId(bookId);
        expected.setTitle("Java");
        expected.setAuthor("Author");
        expected.setIsbn("9781234567897");
        expected.setPrice(BigDecimal.valueOf(50));
        expected.setDescription("Updated Java book");
        expected.setCoverImage("img.jpg");
        expected.setCategoryIds(Set.of(1L));

        when(bookService.update(any(Long.class), any(CreateBookRequestDto.class)))
                .thenReturn(expected);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/books/{id}", bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
    }
}
