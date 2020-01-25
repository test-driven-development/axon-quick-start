package io.axoniq.labs.chat.commandmodel;

import io.axoniq.labs.chat.coreapi.CreateRoomCommand;
import io.axoniq.labs.chat.coreapi.RoomCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ChatRoom {

  @AggregateIdentifier
  private String roomId;

  @CommandHandler
  public ChatRoom(CreateRoomCommand cmd){
    apply(new RoomCreatedEvent(cmd.getRoomId(), cmd.getName()));
  }
}
