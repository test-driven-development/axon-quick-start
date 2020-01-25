package io.axoniq.labs.chat.restapi;

import io.axoniq.labs.chat.coreapi.CreateRoomCommand;
import io.axoniq.labs.chat.coreapi.JoinRoomCommand;
import io.axoniq.labs.chat.coreapi.PostMessageCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
public class CommandController {

  @SuppressWarnings( {"unused", "FieldCanBeLocal"})
  private final CommandGateway commandGateway;

  public CommandController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("/rooms")
  public Future<String> createChatRoom(@RequestBody @Valid Room room) {
    String roomId =
      room.getRoomId() == null ? UUID.randomUUID().toString() : room.getRoomId();

    return this.commandGateway.send(new CreateRoomCommand(roomId, room.getName()));
  }

  @PostMapping("/rooms/{roomId}/participants")
  public Future<Void> joinChatRoom(
    @PathVariable String roomId, @RequestBody @Valid Participant participant) {
    return this.commandGateway.send(new JoinRoomCommand(participant.getName(), roomId));
  }

  @PostMapping("/rooms/{roomId}/messages")
  public Future<Void> postMessage(
    @PathVariable String roomId, @RequestBody @Valid PostMessageRequest message) {
    return this.commandGateway.send(
      new PostMessageCommand(message.getParticipant(), roomId, message.getMessage()));
  }

  @DeleteMapping("/rooms/{roomId}/participants")
  public Future<Void> leaveChatRoom(
    @PathVariable String roomId, @RequestBody @Valid Participant participant) {
    return null;
  }

  public static class PostMessageRequest {
    @NotEmpty private String participant;
    @NotEmpty private String message;

    public String getParticipant() {
      return participant;
    }
    public void setParticipant(String participant) { this.participant = participant; }
    public String getMessage() {
      return message;
    }
    public void setMessage(String message) {
      this.message = message;
    }
  }

  public static class Participant {
    @NotEmpty private String name;
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
  }

  public static class Room {
    private String roomId;
    @NotEmpty private String name;

    public String getRoomId() {
      return roomId;
    }
    public void setRoomId(String roomId) {
      this.roomId = roomId;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
  }
}
