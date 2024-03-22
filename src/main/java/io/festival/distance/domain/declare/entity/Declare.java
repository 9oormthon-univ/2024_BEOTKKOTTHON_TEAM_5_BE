package io.festival.distance.domain.declare.entity;

import io.festival.distance.domain.base.BaseTimeEntity;
import io.festival.distance.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "declared")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class Declare extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declared_id")
    private Long declareId;

    @Column(name = "declare_content")
    private String declareContent;

    @JoinColumn(name = "my_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member me;

    @JoinColumn(name = "opponent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member opponent;
}
