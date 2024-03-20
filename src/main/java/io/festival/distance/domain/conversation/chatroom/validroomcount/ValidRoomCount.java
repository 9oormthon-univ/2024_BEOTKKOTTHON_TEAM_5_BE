package io.festival.distance.domain.conversation.chatroom.validroomcount;

import io.festival.distance.domain.member.entity.Member;
import io.festival.distance.domain.waiting.service.ChatWaitingService;
import io.festival.distance.exception.ChatRoomException;
import io.festival.distance.exception.DistanceException;
import io.festival.distance.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ValidRoomCount {
    private final ValidMyRoomCount validMyRoomCount;
    private final ValidOpponentRoom validOpponentRoom;
    private final ChatWaitingService chatWaitingService;

    public void checkRoom(Member opponent, Member me,boolean flag){
        if(validMyRoomCount.checkMyRoom(me)>=3L){
            throw new DistanceException(ErrorCode.TOO_MANY_MY_CHATROOM);
        }

        if (validOpponentRoom.checkOpponentRoom(opponent) >= 3L) {
            if (flag) {
                chatWaitingService.saveWaitingRoom(opponent, me);
            }
            throw new ChatRoomException(ErrorCode.TOO_MANY_OPPONENT_CHATROOM);
        }
    }
}
