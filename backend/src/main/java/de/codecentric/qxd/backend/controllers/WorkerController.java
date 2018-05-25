package de.codecentric.qxd.backend.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codecentric.qxd.Message;
import de.codecentric.qxd.backend.mq.WorkerMessaging;
import reactor.core.publisher.Flux;

@RestController
public class WorkerController {

  @Autowired
  private WorkerMessaging workerMessaging;

  @GetMapping("/")
  public String workers() {
    workerMessaging.ping(new Message("ping-" + UUID.randomUUID().toString()));
    return "Hello World!";
  }


   @GetMapping(value = "/sse", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
   public Flux<Message> serverSendEvents(){
     final Message message = new Message("ping-" + UUID.randomUUID().toString());
     workerMessaging.ping(message);
     return workerMessaging.queueProcessor;
   }

}
