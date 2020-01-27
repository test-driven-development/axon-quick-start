package io.axoniq.labs.chat.query.rooms.participants;

import io.axoniq.labs.chat.coreapi.ParticipantJoinedRoomEvent;
import io.axoniq.labs.chat.coreapi.ParticipantLeftRoomEvent;
import io.axoniq.labs.chat.coreapi.RoomParticipantsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomParticipantsProjection {

  private final RoomParticipantsRepository repository;

  public RoomParticipantsProjection(RoomParticipantsRepository repository) {
    this.repository = repository;
  }

  @EventHandler
  public void on(ParticipantJoinedRoomEvent event) {
    this.repository.save(new RoomParticipant(event.getRoomId(), event.getParticipant()));
  }

  @EventHandler
  public void on(ParticipantLeftRoomEvent event) {
    this.repository.deleteByParticipantAndRoomId(event.getRoomId(), event.getParticipant());
  }

  @QueryHandler
  public List<RoomParticipant> handle(RoomParticipantsQuery query) {
    return this.repository.findRoomParticipantsByRoomId(query.getRoomId());
  }
}
