package com.example.xm.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@RequestMapping("/file")
public class FileDownloadController {
    @GetMapping("/zip")
    public ResponseEntity<byte[]> downloadFolder(@RequestParam("path") String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return ResponseEntity.notFound().build();
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            // Compress the folder
            compressFolder(folder, "", zos);
            // Ensure all data is written to the stream
            zos.finish();
            // Prepare the response
            byte[] zipBytes = baos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "archive.zip");
            headers.setContentLength(zipBytes.length);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(zipBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private void compressFolder(File folder, String baseName, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String entryName = baseName.isEmpty() ? file.getName() : baseName + "/" + file.getName();
            if (file.isDirectory()) {
                compressFolder(file, entryName, zos);
            } else {
                ZipEntry zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);
                try (InputStream is = new FileInputStream(file)) {
                    IOUtils.copy(is, zos);
                }
                zos.closeEntry();
            }
        }
    }
}
