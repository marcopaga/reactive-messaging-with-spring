package de.codecentric.qxd;

public class Message {

  String message;

  public Message() {
  }

  public Message(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Message{" +
      "message='" + message + '\'' +
      '}';
  }
}
