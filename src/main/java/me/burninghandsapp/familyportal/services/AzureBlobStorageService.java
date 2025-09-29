package me.burninghandsapp.familyportal.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AzureBlobStorageService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    private BlobContainerClient getBlobContainerClient() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        BlobContainerClient containerClient = getBlobContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        
        try (InputStream inputStream = file.getInputStream()) {
            blobClient.upload(inputStream, file.getSize(), true);
        }
        
        return blobClient.getBlobUrl();
    }

    public InputStream downloadFile(String blobUrl) {
        String fileName = extractFileNameFromUrl(blobUrl);
        BlobContainerClient containerClient = getBlobContainerClient();
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        return blobClient.openInputStream();
    }

    public void deleteFile(String blobUrl) {
        if (blobUrl != null && !blobUrl.isEmpty()) {
            String fileName = extractFileNameFromUrl(blobUrl);
            BlobContainerClient containerClient = getBlobContainerClient();
            BlobClient blobClient = containerClient.getBlobClient(fileName);
            blobClient.deleteIfExists();
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + fileExtension;
    }

    private String extractFileNameFromUrl(String blobUrl) {
        return blobUrl.substring(blobUrl.lastIndexOf("/") + 1);
    }
}