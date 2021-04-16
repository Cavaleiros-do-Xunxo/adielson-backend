package calaveirosdoxunxo.adielson.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ItemCategory {
    @Id
    private long id;
    private String name;
    private String description;
}
