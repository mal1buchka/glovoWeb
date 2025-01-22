package kg.org.glovoweb.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;
}
