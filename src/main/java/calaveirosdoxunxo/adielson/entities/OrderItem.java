package calaveirosdoxunxo.adielson.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class OrderItem {
    @Id
    long Id;

    @ManyToOne
    Order order;

    @ManyToOne
    MenuItem item;

    double price;
}
