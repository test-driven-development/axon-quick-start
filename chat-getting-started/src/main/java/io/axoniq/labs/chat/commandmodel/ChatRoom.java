package io.axoniq.labs.chat.commandmodel;

import io.axoniq.labs.chat.coreapi.CreateRoomCommand;
import io.axoniq.labs.chat.coreapi.JoinRoomCommand;
import io.axoniq.labs.chat.coreapi.ParticipantJoinedRoomEvent;
import io.axoniq.labs.chat.coreapi.RoomCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ChatRoom {
  public ChatRoom() {
  }

  @AggregateIdentifier
  private String roomId;
  private Set<String> participants = new HashSet<>();

  @CommandHandler
  public ChatRoom(CreateRoomCommand cmd) {
    apply(new RoomCreatedEvent(cmd.getRoomId(), cmd.getName()));
  }

  @EventSourcingHandler
  public void on(RoomCreatedEvent event) {
    this.roomId = event.getRoomId();
  }

  @CommandHandler
  public void handle(JoinRoomCommand cmd) {
    if(this.participants.contains(cmd.getParticipant()))
      throw new IllegalStateException("cannot join same room twice");
    apply(new ParticipantJoinedRoomEvent(cmd.getParticipant(), cmd.getRoomId()));
  }

  @EventSourcingHandler
  public void on(ParticipantJoinedRoomEvent event) {
    this.participants.add(event.getParticipant());
  }
}
