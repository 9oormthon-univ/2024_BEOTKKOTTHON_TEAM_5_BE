package io.festival.distance.domain.conversation.chatroom.service;

import io.festival.distance.domain.conversation.chat.dto.ChatMessageResponseDto;
import io.festival.distance.domain.conversation.chat.entity.ChatMessage;
import io.festival.distance.domain.conversation.chat.repository.ChatMessageRepository;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomDto;
import io.festival.distance.domain.conversation.chatroom.dto.ChatRoomInfoDto;
import io.festival.distance.domain.conversation.chatroom.entity.ChatRoom;
import io.festival.distance.domain.conversation.chatroom.repository.ChatRoomRepository;
import io.festival.distance.domain.conversation.roommember.entity.RoomMember;
import io.festival.distance.domain.conversation.roommember.repository.RoomMemberRepository;
import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.member.repository.MemberRepository;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    public ChatRoomDto generateRoom(ChatRoomDto chatRoomDto, Principal principal) {
        Member member1 = memberRepository.findById(chatRoomDto.getMemberId())
                .orElseThrow(() -> new DistanceException(ErrorCode.EXIST_NICKNAME));
        Member member2 = memberRepository.findBySchoolEmail(principal.getName());

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(chatRoomDto.getRoomName())
                .build();

        Long chatRoomId = chatRoomRepository.save(chatRoom).getChatRoomId();
        addRoomMember(chatRoomId, List.of(member1,member2));
        return chatRoomDto;
    }

    public List<ChatRoomInfoDto> findAllRoom() {
        return chatRoomRepository.findAll()
                .stream()
                .map(ChatRoomInfoDto::new)
                .collect(Collectors.toList());
    }

    public void addRoomMember(Long chatRoomId,List<Member> member){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 방"));
        for(Member m:member){
            RoomMember roomMember = RoomMember.builder()
                    .chatRoom(chatRoom)
                    .lastReadMessageId(0L)
                    .member(m)
                    .build();
            roomMemberRepository.save(roomMember);
        }
    }

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
                .orElseThrow(()->new IllegalStateException("존재하지 않는 방"));
    }
}
