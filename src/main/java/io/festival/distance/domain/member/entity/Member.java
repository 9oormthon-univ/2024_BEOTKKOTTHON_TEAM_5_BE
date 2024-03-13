package io.festival.distance.domain.member.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.member.dto.MemberInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "school_email")
    private String schoolEmail;

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

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void memberInfoUpdate(MemberInfoDto memberInfoDto){
        this.mbti=memberInfoDto.mbti();
        this.memberCharacter=memberInfoDto.memberCharacter();
    }
}