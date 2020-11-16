package com.reactive.s3asyncdemo.controller;

import com.reactive.s3asyncdemo.pojo.Employee;
import com.reactive.s3asyncdemo.service.S3AsyncClientDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@RestController
@RequestMapping("/s3")
@Slf4j
public class S3AsyncController {

  @Autowired S3AsyncClientDemoService s3AsyncClientDemoService;

  @Autowired private S3Client s3Client;

  @Autowired private S3AsyncClient s3AsyncClient;

  private static String BUCKET_NAME = "s3asyncdemo";

  public S3AsyncController() {
    super();

    log.debug("Default constructor...");
  }

  /**
   * Returns the list of buckets from S3 account.
   *
   * <p>It is synchronous and traditional way of handling the threads. Request thread will be
   * waiting for response from S3 client. Once it receives the response, it process with in same
   * thread and return the response back to UI.
   *
   * <p>Though request thread is not doing any stuff, it is blocked.
   *
   * @return List of bucket names..
   */
  @GetMapping("/listofbuckets")
  public List<String> listOfBuckets() {
    return s3AsyncClientDemoService.listOfBuckets();
  }

  /**
   * Return the list of bucket names from AWS S3 account.
   *
   * <p>It uses Async client and main request thread will be released as soon as it gives control to
   * S3 Client as nothing else there to process. Rest of the logic will be processed by async client
   * and notifies netty when it receives the response.
   *
   * <p>Response will be processed and returned by another thread.
   *
   * @return Response Entity with List of bucket names.
   */
  @GetMapping("/async/listofbuckets")
  public Mono<ResponseEntity<List<String>>> asyncListOfBuckets() {
    return s3AsyncClientDemoService.asyncListOfBuckets();
  }

  /**
   * Traditional way of getting the file context from S3 Bucket. Only drawback is request thread
   * blocked until the processing is complete..
   *
   * @param fileName File Name
   * @return Employee object from S3
   * @throws RuntimeException if the key doesn't exist or key empty.
   */
  @GetMapping("getObject/{fileName}")
  public Employee getObjectFromS3byName(@PathVariable("fileName") String fileName) {
    return s3AsyncClientDemoService.getObjectFromS3byName(fileName);
  }

  /**
   * Traditional way of getting the file context from S3 Bucket. Only drawback is request thread
   * blocked until the processing is complete..
   *
   * @param fileName File Name
   * @return Employee object from S3
   * @throws RuntimeException if the key doesn't exist or key empty.
   */
  @GetMapping("/async/getObject/{fileName}")
  public Mono<ResponseEntity<Employee>> asyncGetObjectFromS3byName(
      @PathVariable("fileName") String fileName) {
    return s3AsyncClientDemoService.asyncGetObjectFromS3byName(fileName);
  }
}
