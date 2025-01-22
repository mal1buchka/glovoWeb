package kg.org.glovoweb.Models;

import jakarta.persistence.*;
import kg.org.glovoweb.constanst.OrderStatusE;
import kg.org.glovoweb.constanst.TypeE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusE status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeE type;

    @Column(name = "totalPrice")
    private double totalPrice;
}
