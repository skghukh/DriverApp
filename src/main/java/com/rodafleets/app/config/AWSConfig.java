package com.rodafleets.app.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

//	@Value("${cloud.aws.credentials.accessKey}")
//	private String accessKey;
//
//	@Value("${cloud.aws.credentials.secretKey}")
//	private String secretKey;
//
//	@Value("${cloud.aws.region}")
//	private String region;

	private static final String ACCESS_KEY = "AKIAJJGEBXWP2ESGS7SA";
	private static final String SECRET_KEY = "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";
//	private static final String REGION = "US_WEST_2";
	
	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	}

	@Bean
	public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		amazonS3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
		return amazonS3Client;
	}
}