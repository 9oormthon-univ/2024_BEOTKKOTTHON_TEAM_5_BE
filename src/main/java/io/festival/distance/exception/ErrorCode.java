package io.festival.distance.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXIST_NICKNAME(HttpStatusCode.BAD_REQUEST.getStatus(),"이미 존재하는 닉네임입니다!"),
    NOT_NULL_NICKNAME(HttpStatusCode.BAD_REQUEST.getStatus(),"아무것도 입력하지 않았습니다!"),
    EXIST_LOGIN_ID(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 ID 입니다!"),
    EXIST_EMAIL(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 Email 입니다!"),
    NOT_CORRECT_PASSWORD(HttpStatusCode.BAD_REQUEST.getStatus(),"비밀번호가 일치하지 않습니다!"),
    NOT_EXIST_ADMIN(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 관리자입니다!"),
    NOT_EXIST_MEMBER(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!"),
    NOT_EXIST_MEMBER1(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!!"),
    NOT_EXIST_MEMBER2(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!!!"),
    NOT_EXIST_MEMBER3(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 유저입니다!!!"),
    NOT_NULL_MEMBER_INFO(HttpStatusCode.LENGTH_REQUIRED.getStatus(), "멤버 프로필을 등록하지 않았습니다!"),

    NOT_EXIST_AUTHENTICATION(HttpStatusCode.UNAUTHORIZED.getStatus(), "Security Context에 인증 정보가 없습니다!");
    private final int status;
    private final String message;
}
