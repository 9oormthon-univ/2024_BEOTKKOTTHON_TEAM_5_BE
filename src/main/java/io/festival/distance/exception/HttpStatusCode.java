package io.festival.distance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpStatusCode {
    CREATED(201),
    OK(200),
    CONFLICT(409),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    CONTAIN_BADWORD(451),
    NOT_FOUND_MESSAGE(406);

    private final int status;
}
