package io.festival.distance.auth.utils;

public class SecurityMessage {
    public final static String SUCCESS_AUTHENTICATION="Security Context 에 인증 정보를 저장했습니다.";
    public final static String NOT_EXIST_AUTHENTICATION="Security Context에 인증 정보가 없습니다.";
    public final static String MALFORMED_JWT="잘못된 JWT 서명입니다.";
    public final static String EXPIRED_JWT="만료된 JWT 토큰입니다.";
    public final static String UNSUPPORT_JWT="지원되지 않는 JWT 토큰입니다.";
    public final static String WRONG_JWT="JWT 토큰이 잘못되었습니다.";
    private SecurityMessage() {
    }
}
