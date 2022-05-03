package by.kuvonchbekn.outlaysbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleToUserForm{
    private String username;
    @JsonProperty(value = "role_name")
    private String roleName;
}
