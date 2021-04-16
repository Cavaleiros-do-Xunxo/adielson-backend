package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.enums.DeliveryType;
import calaveirosdoxunxo.adielson.enums.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Order {
    @Id
    long id;

    @ManyToOne
    User user;

    @Enumerated
    DeliveryType delivery;

    @Enumerated
    Status status;

    Double total;
    Long orderTime;
    Long deliveryTime;
}
