package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.advice.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class MenuItem {

    @Id
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    private String name;
    private Double price;
    private String image;
    private String description;
    private Boolean inMenu;

    @ManyToOne
    private ItemCategory category;

}