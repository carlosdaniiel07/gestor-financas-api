package com.carlos.gestorfinancas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
@Configuration
public class S3Config {

	@Value("${aws.access-key}")
	private String accessKey;

	@Value("${aws.secret-access-key}")
	private String secretAccessKey;

	@Value("${s3.bucket-region}")
	private String s3Region;

	@Bean
	public AmazonS3 amazonS3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.fromName(s3Region)).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		return s3Client;
	}
}