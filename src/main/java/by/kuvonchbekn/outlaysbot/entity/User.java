package by.kuvonchbekn.outlaysbot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String telegramId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isAdmin;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private Date startTime;

    @JsonIgnore
    private String password;

    private Boolean isActive;
}
