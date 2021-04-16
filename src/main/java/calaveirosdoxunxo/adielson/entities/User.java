package calaveirosdoxunxo.adielson.entities;

import calaveirosdoxunxo.adielson.enums.Role;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String address;
    private String email;
    private double cpf;
    private String password;
    private double phone;

    @Enumerated(EnumType.STRING)
    private Role role;
}
