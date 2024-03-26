package io.festival.distance.domain.conversation.waiting.service;

import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingCountDto;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.service.MemberService;
import io.festival.distance.domain.conversation.waiting.dto.ChatWaitingDto;
import io.festival.distance.domain.conversation.waiting.entity.ChatWaiting;
import io.festival.distance.domain.conversation.waiting.repository.ChatWaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatWaitingService {
    private final ChatWaitingRepository chatWaitingRepository;
    private final MemberService memberService;

    @Transactional
    public void saveWaitingRoom(Member opponent, Member me) {
        if(!chatWaitingRepository.existsByLoveSenderAndLoveReceiver(me,opponent)){
            ChatWaiting chatWaiting = ChatWaiting.builder()
                .loveReceiver(opponent) //상대방
                .loveSender(me) //내가 좋아요
                .myRoomName(me.getNickName())
                .opponentRoomName(opponent.getNickName())
                .build();
            Long waitingId = chatWaitingRepository.save(chatWaiting).getWaitingId();
            System.out.println(waitingId);
        }
    }

    @Transactional(readOnly = true)
    public List<ChatWaitingDto> getWaitingRoom(String loginId) {
        Member member = memberService.findByLoginId(loginId); //나
        List<ChatWaiting> allByLoveReceiver = chatWaitingRepository.findAllByLoveReceiver(member);
        List<ChatWaitingDto> chatWaitingDtoList = new ArrayList<>();
        for (ChatWaiting chatWaiting : allByLoveReceiver) {
            Member opponent = memberService.findMember(chatWaiting.getLoveSender().getMemberId());
            ChatWaitingDto dto = ChatWaitingDto.builder()
                    .loveReceiverId(chatWaiting.getLoveReceiver().getMemberId()) //나
                    .loveSenderId(chatWaiting.getLoveSender().getMemberId()) //상대방
                    .myRoomName(chatWaiting.getMyRoomName())
                    .waitingRoomId(chatWaiting.getWaitingId())
                    .memberCharacter(opponent.getMemberCharacter())
                    .build();
            chatWaitingDtoList.add(dto);
        }
        return chatWaitingDtoList;
    }

    @Transactional(readOnly = true)
    public ChatWaitingCountDto countingWaitingRoom(String loginId) {
        Member member = memberService.findByLoginId(loginId);
        Integer count = chatWaitingRepository.countByLoveReceiver(member);

        return ChatWaitingCountDto.builder()
                .waitingCount(count)
                .build();
    }

    @Transactional
    public void deleteRoom(Long waitingRoodId,String loginId) {
        Member member = memberService.findByLoginId(loginId);
        chatWaitingRepository.deleteByWaitingIdAndLoveReceiver(waitingRoodId,member);
    }
}
