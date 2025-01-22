package kg.org.glovoweb.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "establishments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image;

    private String name;

//    private double priceOfDelivery;
//
//    private int timeOdDelivery;
//
//    private int rating;
//
//    private int quantityOfRatings;

    //private boolean isOpen; //вопрос ребятам как это потом реализовать

    @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> product;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subcategory;
}
