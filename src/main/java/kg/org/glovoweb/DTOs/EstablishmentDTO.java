package kg.org.glovoweb.DTOs;

import lombok.Data;

import java.util.Set;

@Data
public class EstablishmentDTO {
    private String id;
    private String name;
    private String image;
    private String subCategoryId; // ID подкатегории, к которой относится заведение
    private Set<ProductDTO> products;
}
