package de.codecentric.qxd.backend.controllers;

import java.time.Duration;
import java.util.function.Consumer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.WorkQueueProcessor;
import reactor.util.function.Tuple2;

public class WorkerControllerTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerControllerTest.class);

  @Test
  public void tinkering() throws Exception {

    final Consumer<FluxSink<Object>> consumer = fluxSink -> {
      fluxSink.next("Test");
      fluxSink.next("Test");
      fluxSink.complete();
    };

    final Flux<String> result = Flux.create(consumer)
      .zipWith(Flux.just("Hallo", "Welt"))
      .map(tuple -> tuple.getT2());
    result.subscribe(LOGGER::info);
    result.blockLast();
  }

  @Test
  public void processor() throws Exception {
    WorkQueueProcessor<String> processor = WorkQueueProcessor.create();
    FluxSink<String> sink = processor.sink();

    sink.next("hallo");
    sink.next("welt");

    processor
      .zipWith(Flux.interval(Duration.ofSeconds(1)))
      .map(Tuple2::getT1)
      .subscribe(LOGGER::info);

    sink.next("out of order");

    processor.blockLast();
  }

}