package by.kuvonchbekn.outlaysbot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String getAuthority() {
        return roleType.name();
    }
}
