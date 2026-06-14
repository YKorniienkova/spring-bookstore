package mate.academy.springintro.service;

import mate.academy.springintro.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.springintro.dto.category.CategoryDto;
import mate.academy.springintro.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);

    Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);
}
