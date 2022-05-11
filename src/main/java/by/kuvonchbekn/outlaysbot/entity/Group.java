package by.kuvonchbekn.outlaysbot.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "botGroup")
public class Group extends BaseEntity{
    @JsonProperty(value = "member_number")
    private long memberNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;

    @JsonProperty(value = "group_name")
    private String groupName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> admins;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
