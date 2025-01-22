package kg.org.glovoweb.DTOs;

import lombok.Data;

import java.util.Set;

@Data
public class CategoryDTO {
    private String id;
    private String name;
    private String image;
    private Set<SubCategoryDTO> subCategories;
}
