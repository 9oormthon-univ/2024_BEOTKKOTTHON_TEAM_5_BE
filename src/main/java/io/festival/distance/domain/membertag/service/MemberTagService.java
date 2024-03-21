package io.festival.distance.domain.membertag.service;

import io.festival.distance.domain.member.dto.MemberHobbyDto;
import io.festival.distance.domain.member.dto.MemberTagDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.membertag.entity.MemberTag;
import io.festival.distance.domain.membertag.repository.MemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MemberTagService {
    private final MemberTagRepository memberTagRepository;

    /**
     * NOTE
     * 사용자가 고른 태그를 갯수와 상관없이 MemberTag테이블에 저장
     */
    @Transactional
    public void updateTag(Member member, List<MemberTagDto> memberTagDto) {
        List<MemberTag> memberTagList = new ArrayList<>();

        for (MemberTagDto tagDto : memberTagDto) {
            MemberTag memberTag = MemberTag.builder()
                    .tagName(tagDto.tag())
                    .member(member)
                    .build();
            memberTagList.add(memberTag);
        }
        memberTagRepository.saveAll(memberTagList);
    }


    @Transactional(readOnly = true)
    public List<MemberTagDto> showTag(Member member) {
        List<MemberTag> allByMember = memberTagRepository.findAllByMember(member);
        List<MemberTagDto> tagDtoList = new ArrayList<>();
        for (MemberTag tag : allByMember) {
            MemberTagDto tagDto = MemberTagDto.builder()
                    .tag(tag.getTagName())
                    .build();
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }

    @Transactional
    public void modifyTag(Member member, List<MemberTagDto> memberTagDto) {
        List<MemberTag> allByMember = memberTagRepository.findAllByMember(member);

        for (int i = 0; i < memberTagDto.size(); i++) {
            MemberTagDto dto = memberTagDto.get(i);
            if (allByMember.size() > i) {
                allByMember.get(i).modifyTag(dto.tag());
            } else {
                MemberTag tag = MemberTag.builder()  //새롭게 생성
                        .member(member)
                        .tagName(dto.tag())
                        .build();
                memberTagRepository.save(tag);
            }
        }

        if (allByMember.size() > memberTagDto.size()) { //기존의 태그 개수가 더 많은 경우
            memberTagRepository.deleteAll(allByMember.subList(memberTagDto.size(), allByMember.size()));
        }
    }
}
