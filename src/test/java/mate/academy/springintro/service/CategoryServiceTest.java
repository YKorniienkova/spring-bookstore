package mate.academy.springintro.service;

import mate.academy.springintro.dto.category.CategoryDto;
import mate.academy.springintro.dto.category.CreateCategoryRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.mapper.CategoryMapper;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Find category by valid id")
    void deleteById_ValidCategoryId_DeletesCategory() {
        Long categoryId = 1L;

        categoryService.deleteById(categoryId);

        verify(categoryRepository).deleteById(categoryId);

    }

    @Test
    @DisplayName("Throw exception for invalid category id")
    void update_InvalidCategoryId_ThrowsException() {
        Long categoryId = 100L;
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.update(categoryId, requestDto)
        );

        assertEquals("Can't find category by id: " + categoryId, exception.getMessage());
    }

    @Test
    @DisplayName("""
            Delete category
            when category id is valid
            """)
    void getById_ValidCategoryId_ReturnsCategoryDto() {
        Long categoryId = 1L;
        Category category = createCategory();
        CategoryDto expected = createCategoryDto();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.getById(categoryId);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @DisplayName("Save new category")
    void save_ValidRequestDto_ReturnsCategoryDto() {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        Category category = createCategory();
        Category savedCategory = createCategory();
        CategoryDto expected = createCategoryDto();

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(expected);

        CategoryDto actual = categoryService.save(requestDto);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    private CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Programming");
        requestDto.setDescription("Programming books");
        return requestDto;
    }

    private Category createCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");
        category.setDescription("Programming books");
        return category;
    }

    private CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Programming");
        categoryDto.setDescription("Programming books");
        return categoryDto;
    }
}
