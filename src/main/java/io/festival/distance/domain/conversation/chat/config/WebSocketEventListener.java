package io.festival.distance.domain.conversation.chat.config;

import io.festival.distance.domain.conversation.chatroomsession.entity.ChatRoomSession;
import io.festival.distance.domain.conversation.chatroomsession.service.ChatRoomSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
     final ChatRoomSessionService chatRoomSessionService;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        GenericMessage generic = (GenericMessage) accessor.getHeader("simpConnectMessage");
        Map nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");
        if (nativeHeaders.containsKey("chatRoomId") && nativeHeaders.containsKey("memberId")) {
            Long chatRoomId = Long.parseLong((String) ((List) nativeHeaders.get("chatRoomId")).get(0));
            Long memberId = Long.parseLong((String) ((List) nativeHeaders.get("memberId")).get(0));
            String sessionName = (String) generic.getHeaders().get("simpSessionId");
            chatRoomSessionService.createChatRoomSession(chatRoomId,memberId, sessionName);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionName = headerAccessor.getSessionId();
        ChatRoomSession session = chatRoomSessionService.findSession(sessionName);
        if(!Objects.isNull(session)) {
            chatRoomSessionService.deleteChatRoomSession(session);
        }
    }
}
