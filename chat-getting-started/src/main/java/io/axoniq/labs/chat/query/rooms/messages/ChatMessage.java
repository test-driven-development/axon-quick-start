package io.axoniq.labs.chat.query.rooms.messages;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("unused")
@Entity
public class ChatMessage {
  @Id
  @GeneratedValue
  private Long id;

  private String participant;
  private String roomId;
  private String message;
  private long timestamp;

  public ChatMessage() {}

  public ChatMessage(String participant, String roomId, String message, long timestamp) {
    this.participant = participant;
    this.roomId = roomId;
    this.message = message;
    this.timestamp = timestamp;
  }

  public long getTimestamp() { return timestamp; }
  public String getMessage() { return message; }
  public String getParticipant() { return participant; }
}
