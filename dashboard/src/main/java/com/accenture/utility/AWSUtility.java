package com.accenture.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;


@Component
public class AWSUtility {
	private static final Logger logger = LoggerFactory.getLogger(AWSUtility.class);

	private AmazonS3 s3client;

	@Autowired
	private Environment env;
	
	//@Value("${aws.accesskey}")
	String accessKey;

	@Value("${aws.bucketname}")
	String bucketName;

	//@Value("${aws.secretkey}")
	String secretKey;

	@Value("${aws.ses.region}")
	String region;

	@Value("${download.dir}")
	String downloadDir;

	@Value("${aws.template.path}")
	String awsDownloadDir;

	@Value("${temp.root.dir}")
	String tempRootDir;

	TransferManager xferMgr;
	
	public void createBucket() {
		if(env.getProperty("aws.accesskey")!=null)
			accessKey=env.getProperty("aws.accesskey");
		if(env.getProperty("aws.secretkey")!=null)
			secretKey=env.getProperty("aws.secretkey");
		if(secretKey==null || accessKey==null)
		{
			s3client = AmazonS3ClientBuilder.standard().withRegion(region).build();
		}
		else
		{
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
			s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(region).build();
		}
		xferMgr = TransferManagerBuilder.standard().withS3Client(s3client).build();
	}

	public File downloadFile(String templateFileName)
			throws AmazonClientException, InterruptedException {

		File directory = new File(downloadDir);
		directory.mkdirs();
		logger.info(downloadDir + " directory created");
		File downloadedFile = new File(downloadDir +"/" + templateFileName);

		Download xfer = xferMgr.download(bucketName, awsDownloadDir +"/" + templateFileName, downloadedFile);
		xfer.waitForCompletion();

		boolean success = directory.exists() && directory.canRead();
		logger.info("Directory created and files placed = " + success);

		return downloadedFile;
	}

	public void uploadDirectoryOrFile(final File source, final String virtualDirectoryKeyPrefix) throws IOException {

		long partSize = Files.size(source.toPath());

		List<PartETag> partETags = new ArrayList<>();

		// Initiate the multipart upload.
		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName,
				virtualDirectoryKeyPrefix);
		InitiateMultipartUploadResult initResponse = s3client.initiateMultipartUpload(initRequest);

		UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(bucketName)
				.withKey(virtualDirectoryKeyPrefix).withUploadId(initResponse.getUploadId()).withPartNumber(1)
				.withFileOffset(0).withFile(source).withPartSize(partSize);

		// Upload the part and add the response's ETag to our list.
		logger.info("S3 upload started at:{} ", new Date());
		UploadPartResult uploadResult = s3client.uploadPart(uploadRequest);
		partETags.add(uploadResult.getPartETag());

		// Complete the multipart upload.
		CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName,
				virtualDirectoryKeyPrefix, initResponse.getUploadId(), partETags);
		s3client.completeMultipartUpload(compRequest);
		logger.info("S3 upload ended at: {}", new Date());

	}

	public String getS3FileDownloadUrl(String objectKey) {
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		// Generate the presigned URL.
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
				.withMethod(HttpMethod.GET).withExpiration(expiration);
		URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

		logger.info("Pre-Signed URL: " + url.toString());
		return url.toString();

	}

	public List<String> listObjects(String objectKey) {
		ListObjectsV2Result result =s3client.listObjectsV2(bucketName, objectKey);
		List<String> objList = new ArrayList<>();
		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
			objList.add(objectSummary.getKey());
		}
		return objList;
	}
	
	public String getCurrentTime() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date();
		date.setTime(ts.getTime());
		return new SimpleDateFormat("yyyyMMddHHmmSS").format(date);
	}

	public void deleteTemplateFile(String objectKey) {
		
		s3client.deleteObject(bucketName, objectKey);
		
	}
	
	public Map<Date,String> listObjectsMap(String objectKey) {
		ListObjectsV2Result result =s3client.listObjectsV2(bucketName, objectKey);
		Map<Date,String> objMap = new HashMap<>();
		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
			objMap.put(objectSummary.getLastModified(), objectSummary.getKey());
		}
		return objMap;
	}
}
