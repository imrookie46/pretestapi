package id.hci.api.helper;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi {

    int status;
    String message;
    Object data;

    public ResponseEntity toOk() {
        return ResponseEntity
                .ok()
                .body(this);
    }

    public ResponseEntity toBadRequest() {
        return  ResponseEntity
                .badRequest()
                .body(this);
    }

}
