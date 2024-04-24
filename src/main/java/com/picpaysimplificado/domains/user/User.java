package com.picpaysimplificado.domains.user;

import com.picpaysimplificado.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fisrtName;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(precision = 11, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO userdto){
        this.fisrtName = userdto.firstName();
        this.lastName = userdto.lastName();
        this.document = userdto.document();
        this.email = userdto.email();
        this.password = userdto.password();
        this.balance = userdto.balance();
        this.userType = userdto.userType();
    }

}
