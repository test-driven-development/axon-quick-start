package io.axoniq.labs.chat.query.rooms.summary;

import io.axoniq.labs.chat.coreapi.ParticipantJoinedRoomEvent;
import io.axoniq.labs.chat.coreapi.ParticipantLeftRoomEvent;
import io.axoniq.labs.chat.coreapi.RoomCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomSummaryProjection {
  private final RoomSummaryRepository roomSummaryRepository;

  @Autowired
  public RoomSummaryProjection(RoomSummaryRepository roomSummaryRepository) {
    this.roomSummaryRepository = roomSummaryRepository;
  }

  @EventHandler
  public void on(RoomCreatedEvent event) {
    this.roomSummaryRepository.save(new RoomSummary(event.getRoomId(), event.getName()));
  }

  @EventHandler
  public void on(ParticipantJoinedRoomEvent event) {
    this.roomSummaryRepository.findById(event.getRoomId())
      .ifPresent(RoomSummary::addParticipant);
  }

  @EventHandler
  public void on(ParticipantLeftRoomEvent event) {
    this.roomSummaryRepository.findById(event.getRoomId())
      .ifPresent(RoomSummary::removeParticipant);
  }

  // TODO: Create the query handler to read data from this model

}
