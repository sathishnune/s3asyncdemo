package com.reactive.s3asyncdemo.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
  private String firstName;
  private String lastName;
  private String email;
  private String country;
  private String profession;
}
