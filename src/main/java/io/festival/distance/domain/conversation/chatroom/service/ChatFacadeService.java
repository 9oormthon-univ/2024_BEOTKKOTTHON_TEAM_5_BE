package io.festival.distance.domain.conversation.chatroom.service;

import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomDto;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.conversation.chatroom.validroomcount.ValidRoomCount;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.domain.conversation.waiting.entity.ChatWaiting;
import io.festival.distance.domain.conversation.waiting.repository.ChatWaitingRepository;
import io.festival.distance.exception.ChatRoomException;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatFacadeService {
    private final ChatRoomService chatRoomService;
    private final ValidRoomCount validRoomCount;

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatWaitingRepository chatWaitingRepository;

    @Transactional(noRollbackFor = ChatRoomException.class)
    public Long generateRoom(ChatRoomDto chatRoomDto, Principal principal, boolean flag) {
        Member opponent = memberRepository.findById(chatRoomDto.getMemberId())
                .orElseThrow(() -> new DistanceException(ErrorCode.EXIST_NICKNAME)); //상대방 7

        Member me = memberRepository.findByLoginId(principal.getName())
                .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER)); //나 2

        validRoomCount.checkRoom(opponent, me, flag);

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(opponent.getNickName())
                .build();

        Long chatRoomId = chatRoomRepository.save(chatRoom).getChatRoomId();
        chatRoomService.addRoomMember(chatRoomId, List.of(opponent, me));
        return chatRoomId;
    }

    @Transactional
    public Long approveRoom(Long waitingRoomId, Principal principal) {
        ChatWaiting chatWaiting = chatWaitingRepository.findById(waitingRoomId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 대기열방입니다!"));

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .memberId(chatWaiting.getLoveSender().getMemberId())
                .build();

        Long generateRoomId = generateRoom(chatRoomDto, principal,false);
        chatWaitingRepository.deleteById(waitingRoomId);
        return generateRoomId;
    }
}