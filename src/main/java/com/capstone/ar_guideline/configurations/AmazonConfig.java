package com.capstone.ar_guideline.configurations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  //    @Value("${cloud.aws.credentials.access-key}")
  //    private String awsAccessKey;
  //
  //   @Value("${cloud.aws.credentials.secret-key}")
  //    private String awsSecretKey;
  //    @Bean
  //    public AmazonS3 S3() {
  //        AWSCredentials awsCredentials = new BasicAWSCredentials(
  //                awsAccessKey   ,
  //                awsSecretKey
  //        );
  //        return AmazonS3ClientBuilder
  //                .standard()
  //                .withRegion(Regions.AP_SOUTHEAST_1)
  //                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
  //                .build();
  //    }

}
