package de.codecentric.qxd.backend.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import de.codecentric.qxd.Message;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.WorkQueueProcessor;

@Component
@EnableBinding(Processor.class)
public class WorkerMessaging {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerMessaging.class);

  @Autowired
  private MessageChannel output;

  public final WorkQueueProcessor<Message> queueProcessor = WorkQueueProcessor.create();
  public final FluxSink<Message> sink = queueProcessor.sink();

  public void ping(Message message) {
    LOGGER.info("Sending message: {}", message);
    output.send(MessageBuilder.withPayload(message).build());
  }

  @StreamListener(Processor.INPUT)
  public void onPong(Message message) {
    sink.next(message);
  }

}
