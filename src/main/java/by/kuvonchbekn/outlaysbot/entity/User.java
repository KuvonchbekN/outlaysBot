package by.kuvonchbekn.outlaysbot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseEntity implements UserDetails {
    @Column(unique = true)
    private String telegramId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Role> roles;
    @JsonIgnore
    private String password;
    private Boolean isActive = true;

    private boolean accountNonExpired = isActive;
    private boolean accountNonLocked = isActive;
    private boolean credentialNonExpired = isActive;
    private boolean enabled = isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
