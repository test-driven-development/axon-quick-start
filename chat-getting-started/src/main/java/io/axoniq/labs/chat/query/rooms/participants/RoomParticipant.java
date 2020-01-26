package io.axoniq.labs.chat.query.rooms.participants;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"roomId", "participant"}))
public class RoomParticipant {

  @Id
  @GeneratedValue
  private Long id;
  private String roomId;
  private String participant;

  public RoomParticipant() {}

  public RoomParticipant(String roomId, String participant) {
    this.roomId = roomId;
    this.participant = participant;
  }

  @SuppressWarnings("unused")
  public String getParticipant() { return this.participant; }
  public String getRoomId() { return this.roomId; }
}
