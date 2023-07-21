// package com.ArkJJ.ChatApp.handler;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.CloseStatus;
// import org.springframework.web.socket.TextMessage;
// import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.handler.TextWebSocketHandler;

// import com.ArkJJ.ChatApp.model.Message;

// @Component
// public class SessionHandler extends TextWebSocketHandler {

// @Autowired
// @Qualifier("ids")
// public List<String> ids;

// private static ArrayList<WebSocketSession> sessions = new ArrayList<>();

// @Override
// public void afterConnectionEstablished(WebSocketSession session) throws
// Exception {
// System.out.println("User connected: " + session.getId());
// sessions.add(session);
// ids.add(session.getId());
// sendMessageToAll(new Message("Connected"));

// }

// @Override
// protected void handleTextMessage(WebSocketSession session, TextMessage
// message) throws Exception {
// System.out.println("Received: " + message.getPayload());
// sendMessageToAll(new Message(message.getPayload(), (String)
// session.getAttributes().get("sender")));
// }

// @Override
// public void handleTransportError(WebSocketSession session, Throwable
// exception) throws Exception {
// }

// @Override
// public void afterConnectionClosed(WebSocketSession session, CloseStatus
// status) throws Exception {
// sessions.remove(session);
// ids.remove(session.getId());
// }

// public void sendMessageToAll(Message message) throws Exception {
// for (WebSocketSession session : sessions) {
// session.sendMessage(new TextMessage(message.toString()));
// }
// }

// }
