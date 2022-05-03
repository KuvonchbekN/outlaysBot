package by.kuvonchbekn.outlaysbot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean isSuccess;
    private Object object;

    public ApiResponse(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
