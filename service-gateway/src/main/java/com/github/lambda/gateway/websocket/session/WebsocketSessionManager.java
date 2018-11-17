package com.github.lambda.gateway.websocket.session;

import com.github.lambda.gateway.websocket.exception.WebsocketRegistryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebsocketSessionManager implements WebsocketSessionRegistry {

  // username -> session id -> session
  private final Map<String, Map<String, WebSocketSession>> sessions =
      new ConcurrentHashMap<>();

  private final Object lock = new Object();

  public static String extractSessionId(WebSocketSession session) {
    return session.getId();
  }

  public static String extractUsername(WebSocketSession session) {
    Principal raw = session.getPrincipal();

    String username = raw.getName();

    if (StringUtils.isEmpty(username)) {
      return null;
    }

    return username;
  }

  /**
   * Add new session.
   * If there is an existing session with the same session id (at a low probability)
   * will overwrite it and close the existing one with `CloseStatus.SERVER_ERROR`
   *
   * @param session
   */
  public void addSession(WebSocketSession session) {
    String sessionId = extractSessionId(session);
    String username = extractUsername(session);

    if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(username)) {
      throw WebsocketRegistryException.create(
          "Failed to register websocket session due to invalid principal");
    }

    synchronized (lock) {
      if (!sessions.containsKey(username)) {
        sessions.put(username, new ConcurrentHashMap<>());
      }

      Map<String, WebSocketSession> userSessions = sessions.get(username);
      if (userSessions.containsKey(sessionId)) {
        log.warn("Session {} already exist. will overwrite and close the existing one.",
                 sessionId);
      }

      WebSocketSession previous = userSessions.put(sessionId, session);

      // close the existing session.
      if (previous != null) {
        try {
          previous.close(CloseStatus.SERVER_ERROR);
        } catch (IOException e) {
          log.error("Failed to close exiting websocket session {}", previous.getId());
        }
      }
    }

    if (log.isDebugEnabled()) {
      int userCount = getUserCount();
      int sessionCount = getSessionCount();

      log.debug("Websocket session was added: {} / {} (users {} / sessions {})",
                username, sessionId, userCount, sessionCount);
    }
  }

  private int getUserCount() {
    return sessions.size();
  }

  private int getSessionCount() {
    int sessionCount = 0;

    for (Map.Entry<String, Map<String, WebSocketSession>> userSessions : sessions.entrySet()) {
      sessionCount += userSessions.getValue().size();
    }

    return sessionCount;
  }


  /**
   * Remove the passed websocket session from registry.
   *
   * @param session
   */
  public void removeSession(WebSocketSession session) {
    String sessionId = extractSessionId(session);
    String username = extractUsername(session);

    if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(username)) {
      throw WebsocketRegistryException.create(
          "Failed to remove websocket session from registry due to invalid principal");
    }

    synchronized (lock) {
      if (!sessions.containsKey(username)) {
        log.warn("Websocket registry doesn't contain username for session {}", sessionId);
        return;
      }

      Map<String, WebSocketSession> userSessions = sessions.get(username);
      if (!userSessions.containsKey(sessionId)) {
        log.warn("Websocket registry doesn't contain session {}", sessionId);
        return;
      }

      userSessions.remove(sessionId);
      if (userSessions.isEmpty()) {
        sessions.remove(username);
      }
    }

    if (log.isDebugEnabled()) {
      int userCount = getUserCount();
      int sessionCount = getSessionCount();

      log.debug("Websocket session was removed: {} /{} (users {} / sessions {})",
                username, sessionId, userCount, sessionCount);
    }
  }

  @Override
  public List<WebSocketSession> getUserSessions(String username) {
    if (StringUtils.isEmpty(username)) {
      return Collections.emptyList();
    }

    Map<String, WebSocketSession> userSessions = sessions.get(username);
    if (userSessions == null || userSessions.isEmpty()) {
      return Collections.emptyList();
    }

    return new ArrayList<>(userSessions.values());
  }
}
