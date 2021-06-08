package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.advice.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class OrderItem {

    @Id
    @JsonSerialize(using = LongToStringSerializer.class)
    private long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private MenuItem item;

    private int count;

    private double price;

}
