package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.advice.serializer.LongToStringSerializer;
import calaveirosdoxunxo.adielson.enums.Role;
import calaveirosdoxunxo.adielson.models.OrderAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    private String name;
    private String address;
    private String email;
    private Long cpf;
    private Long phone;

    @Transient
    private OrderAddress orderAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
