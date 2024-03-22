package io.festival.distance.authuniversity.config.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnivMailDto {
    private String mailAddress;
    private String title;
    private String message;
}
