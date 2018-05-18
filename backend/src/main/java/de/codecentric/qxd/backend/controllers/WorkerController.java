package de.codecentric.qxd.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.codecentric.qxd.backend.mq.WorkerMessaging;

@RestController
public class WorkerController {

  @Autowired
  private WorkerMessaging workerMessaging;

  @GetMapping("/")
  public String workers() {
    workerMessaging.ping("ping");
    return "Hello World!";
  }



}
