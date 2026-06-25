package mate.academy.springintro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import mate.academy.springintro.dto.category.CategoryDto;
import mate.academy.springintro.dto.category.CreateCategoryRequestDto;
import mate.academy.springintro.exception.EntityNotFoundException;
import mate.academy.springintro.mapper.BookMapper;
import mate.academy.springintro.mapper.CategoryMapper;
import mate.academy.springintro.model.Category;
import mate.academy.springintro.repository.BookRepository;
import mate.academy.springintro.repository.CategoryRepository;
import mate.academy.springintro.service.impl.CategoryServiceImpl;
import mate.academy.springintro.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

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
    @DisplayName("Delete category by valid id")
    void deleteById_ValidCategoryId_DeletesCategory() {
        Long categoryId = 1L;

        categoryService.deleteById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    @DisplayName("Throw exception when updating category with invalid id")
    void update_InvalidCategoryId_ThrowsException() {
        Long categoryId = 100L;
        CreateCategoryRequestDto requestDto = TestUtil.createCategoryRequestDto();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.update(categoryId, requestDto)
        );

        assertEquals("Can't find category by id: " + categoryId, exception.getMessage());
    }

    @Test
    @DisplayName("Find category by valid id")
    void getById_ValidCategoryId_ReturnsCategoryDto() {
        Long categoryId = 1L;
        Category category = TestUtil.createCategory();
        CategoryDto expected = TestUtil.createCategoryDto();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.getById(categoryId);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Save new category")
    void save_ValidRequestDto_ReturnsCategoryDto() {
        CreateCategoryRequestDto requestDto = TestUtil.createCategoryRequestDto();
        Category category = TestUtil.createCategory();
        Category savedCategory = TestUtil.createCategory();
        CategoryDto expected = TestUtil.createCategoryDto();

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(expected);

        CategoryDto actual = categoryService.save(requestDto);

        assertEquals(expected, actual);
    }

}
