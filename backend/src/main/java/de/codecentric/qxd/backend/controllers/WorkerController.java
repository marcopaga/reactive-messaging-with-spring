package de.codecentric.qxd.backend.controllers;

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

  private Flux<Message> flux = Flux.just(new Message("hallo"));

  @GetMapping("/")
  public String workers() {
    workerMessaging.ping(new Message("ping"));
    return "Hello World!";
  }


   @GetMapping(value = "/sse", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
   public Flux<Message> serverSendEvents(){
     return flux;
   }

}
