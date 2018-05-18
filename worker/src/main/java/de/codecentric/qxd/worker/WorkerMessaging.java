package de.codecentric.qxd.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import de.codecentric.qxd.Message;

@Component
@EnableBinding(Processor.class)
public class WorkerMessaging {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerMessaging.class);

  @StreamListener(Processor.INPUT)
  @SendTo(Processor.OUTPUT)
  public Message onPing(Message message) {
    LOGGER.info("{} received. Sending pong", message);
    return new Message("pong");
  }

}
