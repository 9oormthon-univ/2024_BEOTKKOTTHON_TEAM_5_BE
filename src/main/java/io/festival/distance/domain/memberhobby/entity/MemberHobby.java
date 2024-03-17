package io.festival.distance.domain.memberhobby.entity;


import io.festival.distance.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "member_hobby")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private Long hobbyId;

    @Column(name = "hobby_name")
    private String hobbyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void modifyHobby(String hobbyName) {
        this.hobbyName=hobbyName;
    }
}
