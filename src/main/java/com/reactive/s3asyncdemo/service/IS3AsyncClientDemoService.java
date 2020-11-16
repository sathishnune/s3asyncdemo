package com.reactive.s3asyncdemo.service;

import com.reactive.s3asyncdemo.pojo.Employee;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IS3AsyncClientDemoService {

  List<String> listOfBuckets();

  Mono<ResponseEntity<List<String>>> asyncListOfBuckets();

  Employee getObjectFromS3byName(String fileName);

  Mono<ResponseEntity<Employee>> asyncGetObjectFromS3byName(String fileName);
}
