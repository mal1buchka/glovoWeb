package kg.org.glovoweb.DTOs;

import lombok.Data;

import java.util.Set;

@Data
public class SubCategoryDTO {
    private String id;
    private String name;
    private String image;
    private String categoryId;
    private Set<EstablishmentDTO> establishments;
}
