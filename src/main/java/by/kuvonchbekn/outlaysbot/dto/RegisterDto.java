package by.kuvonchbekn.outlaysbot.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDto {

    private String username;
    private String password;
    @JsonProperty(value = "telegram_id")
    private String telegramId;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;
    @JsonProperty(value = "phone_number")
    private String phoneNumber;
}