package kg.org.glovoweb.Controllers;

import kg.org.glovoweb.DTOs.CategoryDTO;
import kg.org.glovoweb.DTOs.SubCategoryDTO;
import kg.org.glovoweb.Models.Category;
import kg.org.glovoweb.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/non-secured/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImage(category.getImage());
        // Если нужно включить подкатегории
        dto.setSubCategories(category.getSubCategory().stream()
                .map(subCategory -> {
                    SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
                    subCategoryDTO.setId(subCategory.getId());
                    subCategoryDTO.setName(subCategory.getName());
                    subCategoryDTO.setImage(subCategory.getImage());
                    subCategoryDTO.setCategoryId(subCategory.getCategory().getId());
                    return subCategoryDTO;
                })
                .collect(Collectors.toSet()));
        return dto;
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }
}
