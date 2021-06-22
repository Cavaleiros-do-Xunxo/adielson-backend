package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.advice.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    @ManyToOne
    private User user;

    private String address;
    private String complement;

}
