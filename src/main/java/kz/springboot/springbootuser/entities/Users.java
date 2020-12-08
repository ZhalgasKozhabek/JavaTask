package kz.springboot.springbootuser.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    public Users(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="email", length=500)
    private String email;

    @Column(name="password", length=500)
    private String password;

    @Column(name="fullname", length=500)
    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;
}
