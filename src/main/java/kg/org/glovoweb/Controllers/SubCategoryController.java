package kg.org.glovoweb.Controllers;

import kg.org.glovoweb.Models.SubCategory;
import kg.org.glovoweb.Repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/non-secured/subcategories")
public class SubCategoryController {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @GetMapping
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<SubCategory> getSubCategoryById(@PathVariable String id) {
        return subCategoryRepository.findById(id);
    }
}
