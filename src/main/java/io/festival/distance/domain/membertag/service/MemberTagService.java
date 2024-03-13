package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.entity.MemberTag;
import io.festival.distance.domain.membertag.repository.MemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberTagService {
    private final MemberTagRepository memberTagRepository;

    /** NOTE
     * 사용자가 고른 태그를 갯수와 상관없이 MemberTag테이블에 저장
     */
    @Transactional
    public void generateTag(Member member, List<MemberTagDto> memberTagDto){
        List<MemberTag> memberTagList=new ArrayList<>();

        for (MemberTagDto tagDto : memberTagDto) {
            MemberTag memberTag = MemberTag.builder()
                    .tagName(tagDto.tag())
                    .member(member)
                    .build();
            memberTagList.add(memberTag);
        }
        memberTagRepository.saveAll(memberTagList);
    }
}
