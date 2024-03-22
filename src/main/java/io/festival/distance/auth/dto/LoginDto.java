package io.festival.distance.auth.dto;

import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String clientToken;
}
