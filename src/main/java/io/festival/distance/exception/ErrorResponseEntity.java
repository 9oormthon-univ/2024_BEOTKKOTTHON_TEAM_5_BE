package io.festival.distance.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> responseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseEntity.builder()
                        .code(e.name())
                        .message(e.getMessage())
                        .build());
    }
}
