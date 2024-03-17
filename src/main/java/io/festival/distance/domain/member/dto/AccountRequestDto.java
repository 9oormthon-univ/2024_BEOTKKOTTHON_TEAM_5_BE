package io.festival.distance.domain.member.dto;

public record AccountRequestDto(String loginId,
                                String password,
                                String checkPassword,
                                String gender,
                                String telNum) {
}
