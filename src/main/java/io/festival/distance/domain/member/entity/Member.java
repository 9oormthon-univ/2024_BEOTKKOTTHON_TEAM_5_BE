package io.festival.distance.domain.member.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.gps.dto.GpsDto;
import io.festival.distance.domain.member.dto.AccountRequestDto;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Base64;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "school_email")
    private String schoolEmail;

    @Column(name = "login_id")
    @Valid
    @NotNull
    private String loginId;

    @Column(name = "encrypted_password")
    private String password;

    @Column(name="mbti")
    private String mbti;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "tel_num")
    private String telNum;

    @Column(name = "school")
    private String school;

    @Column(name = "college")
    private String college;

    @Column(name = "department")
    private String department;

    @Column(name = "member_image")
    private String memberCharacter;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "client_token")
    private String clientToken;

    @Column(name = "declaration_count")
    private Integer declarationCount;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void memberInfoUpdate(MemberInfoDto memberInfoDto) {
        this.mbti = memberInfoDto.mbti();
        this.memberCharacter = memberInfoDto.memberCharacter();
    }
    public void memberGpsUpdate(GpsDto gpsDto) {
        this.latitude = gpsDto.latitude();
        this.longitude = gpsDto.longitude();
    }

    public void memberNicknameUpdate(String nickName){
        this.nickName=nickName;
    }

    public void memberAccountModify(AccountRequestDto accountRequestDto,String encrypted_password){
        this.loginId=accountRequestDto.loginId();
        this.password=encrypted_password;
        this.gender=accountRequestDto.gender();
        this.telNum=accountRequestDto.telNum();
    }
    public void clientTokenUpdate(String clientToken){
        this.clientToken=clientToken;
    }

    public void updateDeclare(){
        this.declarationCount+=1;
    }

    public void disableAccount(){
        this.activated=false;
    }
}
