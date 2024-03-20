package io.festival.distance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomException extends RuntimeException{
    ErrorCode errorCode;
}
