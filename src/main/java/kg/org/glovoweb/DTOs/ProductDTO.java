package kg.org.glovoweb.DTOs;

import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private double price;
    private String description;
    private String image;
    private String establishmentId;
}
