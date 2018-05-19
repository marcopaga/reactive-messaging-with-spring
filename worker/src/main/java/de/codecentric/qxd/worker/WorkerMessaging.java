package de.codecentric.qxd.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import de.codecentric.qxd.Message;
import reactor.core.publisher.Flux;

@Component
@EnableBinding(Processor.class)
public class WorkerMessaging {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerMessaging.class);

  @StreamListener
  @SendTo(Processor.OUTPUT)
  public Flux<Message> onPing(@Input(Processor.INPUT) Flux<Message> message) {
    LOGGER.info("Worker message pipeline set up");
    return message.map( input -> new Message("Pong for " + input.getMessage()));
  }

}
