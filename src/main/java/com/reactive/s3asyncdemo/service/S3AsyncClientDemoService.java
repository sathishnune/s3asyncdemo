package com.reactive.s3asyncdemo.service;

import com.google.gson.Gson;
import com.reactive.s3asyncdemo.ServiceException;
import com.reactive.s3asyncdemo.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class S3AsyncClientDemoService implements IS3AsyncClientDemoService {

  @Autowired private S3Client s3Client;

  @Autowired private S3AsyncClient s3AsyncClient;

  // Bucket name same. Change it if you have different bucket in S3.
  private static final String BUCKET_NAME = "s3asyncdemo";

  public S3AsyncClientDemoService() {
    super();
  }

  @Override
  public List<String> listOfBuckets() {
    return s3Client.listBuckets().buckets().stream().map(Bucket::name).collect(Collectors.toList());
  }

  @Override
  public Mono<ResponseEntity<List<String>>> asyncListOfBuckets() {
    final CompletableFuture<ListBucketsResponse> listBucketsResponse = s3AsyncClient.listBuckets();

    return Mono.fromFuture(
        listBucketsResponse.thenApply(
            response ->
                ResponseEntity.ok(
                    response.buckets().stream().map(Bucket::name).collect(Collectors.toList()))));
  }

  @Override
  public Employee getObjectFromS3byName(String fileName) {
    Employee result;
    if (StringUtils.isEmpty(fileName)) {
      throw new ServiceException("File name can not be blank..");
    }
    final GetObjectRequest getObjectRequest =
        GetObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build();
    try {
      final String getObjectResponse =
          s3Client.getObjectAsBytes(getObjectRequest).asString(Charset.defaultCharset());

      result = new Gson().fromJson(getObjectResponse, Employee.class);
    } catch (NoSuchKeyException noSuchKeyException) {
      result = Employee.builder().build();
    }
    return result;
  }

  @Override
  public Mono<ResponseEntity<Employee>> asyncGetObjectFromS3byName(final String fileName) {
    if (StringUtils.isEmpty(fileName)) {
      throw new ServiceException("File name can not be blank..");
    }
    final GetObjectRequest getObjectRequest =
        GetObjectRequest.builder().bucket(BUCKET_NAME).key(fileName).build();
    try {
      final CompletableFuture<String> getObjectResponse =
          s3AsyncClient
              .getObject(getObjectRequest, AsyncResponseTransformer.toBytes())
              .thenApply(ResponseBytes::asUtf8String);
      return Mono.fromFuture(
          getObjectResponse.thenApply(
              content -> {
                log.info("Received Emp response: {}", content);
                return ResponseEntity.ok(new Gson().fromJson(content, Employee.class));
              }));

    } catch (NoSuchKeyException noSuchKeyException) {
      return Mono.just(ResponseEntity.ok(Employee.builder().build()));
    }
  }
}
