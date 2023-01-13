package id.web.ilham.inventory.common.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Data
public class ResponseMessage<T extends CommonResponse> implements Serializable {

    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    private ResponseMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T extends CommonResponse> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T extends CommonResponse> ResponseMessage<T> error(int code, String message, T data) {
        return new ResponseMessage<>(code, message, data);
    }

    public static ResponseMessage<CommonResponse> error(int code, String message) {
        return error(code, message, null);
    }

    public String toJson() {
        return new StringJoiner(", ", "{", "}")
                .add("\"code\": " + code)
                .add("\"message\": \"" + message + "\"")
                .add("\"data\": \"" + data + "\"")
                .add("\"timestamp\": \"" + timestamp + "\"").toString();
    }
}
