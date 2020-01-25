package io.axoniq.labs.chat.commandmodel;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ChatRoom {

  @AggregateIdentifier
  private String roomId;
}
