package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.advice.serializer.LongToStringSerializer;
import calaveirosdoxunxo.adielson.enums.DeliveryType;
import calaveirosdoxunxo.adielson.enums.Status;
import calaveirosdoxunxo.adielson.models.OrderAddress;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    @ManyToOne
    private User user;

    @Transient
    private List<OrderAddress> orderAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryType delivery;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double total;
    private Long orderTime;
    private Long deliveryTime;

}
