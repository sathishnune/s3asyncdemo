# AWS S3 with Sync and Async clients - Spring WebFlux.
S3 with Async Client and Spring Webflux.

---
Sample demo application to demonstrate Async and traditional way of 
handling S3 actions like list of buckets, get Object.

Pre-requisites
1. Should have basic knowledge on Java and Spring.
2. Should have AWS Account with read access to S3.

Steps Required:
1. Create a bucket with name "s3asyncdemo" or change the bucket name in S2AsyncClientDemoService.
    - Place the file attached in created bucket.
2. Configure AWS credentials - https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
3. Clone the project and run "mvn clean install" and run the application

Steps to access the urls:
1. Default port is 8080 unless you change it.
2. Access from Below urls
- http://localhost:8080/s3/async/listofbuckets -> To list buckets in Async way
- http://localhost:8080/s3/listofbuckets -> Traditional way of getting bucket list.
- http://localhost:8080/s3/getObject/empobject -> Get Object from S3 bucket "s3asyncdemo" and file name "empobject"
- http://localhost:8080/s3/async/getObject/empobject -> Get Object from S3 bucket "s3asyncdemo" and file name "empobject" in Async way.

Formatter: https://github.com/google/google-java-format

References: 
- https://aws.amazon.com/blogs/developer/aws-sdk-for-java-2-x-released/
- https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html