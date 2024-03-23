package io.festival.distance.domain.conversation.chatroom.service;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomInfoDto;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.conversation.waiting.repository.ChatWaitingRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomInfoDto> findAllRoom(String loginId) {
        Member member = memberRepository.findByLoginId(loginId) //현재 로그인한 객체
                .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));

        return roomMemberRepository.findAllByMember(member)
                .stream()
                .map(roomMember -> {
                    ChatRoom chatRoom = roomMember.getChatRoom();
                    Member opponent=memberRepository.findByNickName(roomMember.getMyRoomName());

                    System.out.println(">>>>>"+ opponent.getMemberId());

                    ChatMessage message = chatMessageRepository.findTop1ByChatRoomOrderByCreateDtDesc(chatRoom);

                    String lastMessage=Objects.isNull(message)?"새로운 채팅방이 생성되었습니다!": message.getChatMessage();
                    System.out.println(roomMember.getLastReadMessageId());
                    Integer count = roomMemberRepository.countByChatRoomAndLastReadMessageIdGreaterThan(chatRoom, roomMember.getLastReadMessageId());

                    return ChatRoomInfoDto.builder()
                            .chatRoomId(chatRoom.getChatRoomId())
                            .roomName(roomMember.getMyRoomName())
                            .createDt(roomMember.getCreateDt())
                            .modifyDt(roomMember.getModifyDt())
                            .opponentMemberId(opponent.getMemberId())
                            .memberCharacter(opponent.getMemberCharacter())
                            .lastMessage(lastMessage)
                            .askedCount(count)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void addRoomMember(Long chatRoomId, List<Member> member) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 방"));

        for (Member m : member) {
            Member opponent = member //상대방 찾음
                    .stream()
                    .filter(o -> !o.getMemberId().equals(m.getMemberId()))
                    .findFirst()
                    .orElseThrow(() -> new DistanceException(ErrorCode.NOT_EXIST_MEMBER));

            RoomMember roomMember = RoomMember.builder()
                    .chatRoom(chatRoom)
                    .myRoomName(opponent.getNickName())
                    .lastReadMessageId(0L)
                    .member(m)
                    .build();
            roomMemberRepository.save(roomMember);
        }
    }
    @Transactional
    public Long delete(Long roomId) {
        chatRoomRepository.deleteById(roomId);
        return roomId;
    }

    public List<ChatMessageResponseDto> getMessage(Long roomId) {
        ChatRoom chatRoom = findRoom(roomId);
        return chatMessageRepository.findByChatRoom(chatRoom)
                .stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
    }

    public ChatRoom findRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 방"));
    }
}
