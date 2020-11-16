package com.reactive.s3asyncdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {

  /**
   * Default S3 Client as bean. Change the default region as per your selection in AWS. S3 data will
   * sync across regions but there is some latency and it is recommended to use the selected region
   * as S3Client region.
   *
   * @return S3Client with default settings
   */
  @Bean
  public S3Client s3Client(AwsCredentialsProvider awsCredentialsProvider) {

    final Region defaultRegion = Region.AP_SOUTH_1;
    return S3Client.builder()
        .region(defaultRegion)
        .credentialsProvider(awsCredentialsProvider)
        .build();
  }

  /**
   * DefaultCredentialsProvider look for .credentials file and read the access key and access token
   * from it.
   *
   * <p>Check
   * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html#get-started-setup-credentials
   * for more details.
   *
   * @return AwsCredentialsProvider
   */
  @Bean
  public AwsCredentialsProvider credentialsProvider() {
    return DefaultCredentialsProvider.create();
  }

  @Bean
  public S3AsyncClient s3AsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
    return S3AsyncClient.builder().credentialsProvider(awsCredentialsProvider).build();
  }
}
