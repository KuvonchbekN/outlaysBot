package by.kuvonchbekn.outlaysbot.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RegisterDto {

    private String username;
    private String password;
}
