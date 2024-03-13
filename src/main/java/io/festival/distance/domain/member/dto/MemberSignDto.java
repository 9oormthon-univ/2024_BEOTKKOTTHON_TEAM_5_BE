package io.festival.distance.domain.member.dto;

/** NOTE
 * 불변 객체를 생성하기 위해 record 사용
 */
public record MemberSignDto(
        String schoolEmail,
        String password,
        String checkPassword,
        String gender,
        String nickName,
        String telNum,
        String school,
        String college,
        String department) {
}
