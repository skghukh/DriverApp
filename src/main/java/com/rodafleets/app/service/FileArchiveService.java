package com.rodafleets.app.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rodafleets.app.model.DriverDocs;

@Service
public class FileArchiveService {

	@Autowired
	private AmazonS3Client s3Client;

	private static final Logger log = LoggerFactory.getLogger(FileArchiveService.class);
	private static final String S3_BUCKET_NAME = "roda-driver-docs";


	/**
	 * Save image to S3 and return public URL
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public String saveFileToS3(MultipartFile multipartFile, String fileName) throws FileArchiveServiceException {

		try{
			File fileToUpload = convertFromMultiPart(multipartFile);
			String key;
			if(fileName != null) {
				key = fileName;
			} else {
				key =  fileToUpload.getName();
			}
			key = Instant.now().getEpochSecond() + "_" + key;
			/* save file */
			// also set the filef ro public access.
			PutObjectRequest request = new PutObjectRequest(S3_BUCKET_NAME, key, fileToUpload).withCannedAcl(CannedAccessControlList.PublicRead);
			
			log.info("s3 bucket name = " + request.getBucketName());
			log.info("s3 filename to save = " + request.getKey());
			
			//Need to be done when s3 is done.
			//s3Client.putObject(request);
			
			String publicUrl = s3Client.getResourceUrl(S3_BUCKET_NAME, key);
			
			return publicUrl;
			
		} catch(Exception ex){			
			throw new FileArchiveServiceException("An error occurred saving file to S3", ex);
		}		
	}
	
	/**
	 * Save image to S3 and return public URL
	 * 
	 * @param File
	 * @return
	 * @throws IOException
	 */
	public String saveFileToS3(File fileToUpload, String fileName) throws FileArchiveServiceException {

		try{
//			File fileToUpload = convertFromMultiPart(multipartFile);
			String key;
			if(fileName != null) {
				key = fileName;
			} else {
				key =  fileToUpload.getName();
			}
			key = Instant.now().getEpochSecond() + "_" + key;
			/* save file */
			// also set the filef ro public access.
			PutObjectRequest request = new PutObjectRequest(S3_BUCKET_NAME, key, fileToUpload).withCannedAcl(CannedAccessControlList.PublicRead);
			
			log.info("s3 bucket name = " + request.getBucketName());
			log.info("s3 filename to save = " + request.getKey());
			
			s3Client.putObject(request);
			
			String publicUrl = s3Client.getResourceUrl(S3_BUCKET_NAME, key);
			
			return publicUrl;
			
		} catch(Exception ex){			
			throw new FileArchiveServiceException("An error occurred saving file to S3", ex);
		}		
	}

	/**
	 * Delete image from S3 using specified key
	 * 
	 * @param customerImage
	 */
	public void deleteImageFromS3(DriverDocs customerImage){
		//s3Client.deleteObject(new DeleteObjectRequest(S3_BUCKET_NAME, customerImage.getKey()));	
	}

	/**
	 * Convert MultiPartFile to ordinary File
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	private File convertFromMultiPart(MultipartFile multipartFile) throws IOException {

		File file = new File(multipartFile.getOriginalFilename());
		file.createNewFile(); 
		FileOutputStream fos = new FileOutputStream(file); 
		fos.write(multipartFile.getBytes());
		fos.close(); 

		return file;
	}
}

class FileArchiveServiceException extends RuntimeException {

	private static final long serialVersionUID = 2468434988680850339L;	
	
	public FileArchiveServiceException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}