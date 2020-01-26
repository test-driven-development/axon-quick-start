package io.axoniq.labs.chat.query.rooms.summary;

import io.axoniq.labs.chat.coreapi.ParticipantJoinedRoomEvent;
import io.axoniq.labs.chat.coreapi.ParticipantLeftRoomEvent;
import io.axoniq.labs.chat.coreapi.RoomCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoomSummaryProjection {
  private final RoomSummaryRepository roomSummaryRepository;

  public RoomSummaryProjection(RoomSummaryRepository roomSummaryRepository) {
    this.roomSummaryRepository = roomSummaryRepository;
  }

  @EventHandler
  public void on(RoomCreatedEvent event) {
    RoomSummary summary = this.roomSummaryRepository.save(new RoomSummary(event.getRoomId(), event.getName()));
  }

  @EventHandler
  public void on(ParticipantJoinedRoomEvent event) {
    Optional<RoomSummary> summary = this.roomSummaryRepository.findById(event.getRoomId());
    summary.ifPresent(RoomSummary::addParticipant);
  }

  @EventHandler
  public void on(ParticipantLeftRoomEvent event) {
    Optional<RoomSummary> summary = this.roomSummaryRepository.findById(event.getRoomId());
    summary.ifPresent(RoomSummary::removeParticipant);
  }

  // TODO: Create the query handler to read data from this model

}
