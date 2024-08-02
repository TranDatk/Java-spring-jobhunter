package vn.datk.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.datk.jobhunter.domain.res.files.UploadFileResponse;
import vn.datk.jobhunter.service.FileService;
import vn.datk.jobhunter.util.annotation.ApiMessage;
import vn.datk.jobhunter.util.error.StorageException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "${apiPrefix}/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @Value("${datk.upload-file.base-uri}")
    private String baseURI;

    @PostMapping("")
    @ApiMessage("Upload single file")
    public ResponseEntity<UploadFileResponse> upload(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException, StorageException {
        if(file == null || file.isEmpty()){
            throw new StorageException("File is empty, please up load a file");
        }
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtensions.stream().anyMatch(
                item -> fileName.toLowerCase().endsWith(item)
        );

        if(!isValid){
            throw new StorageException("Invalid file extension. Only allows " + allowedExtensions.toString());
        }

        this.fileService.createUploadFolder(baseURI + folder);
        return ResponseEntity.ok().body(this.fileService.store(file, folder));
    }

    @GetMapping("/files")
    @ApiMessage("Download a file")
    public ResponseEntity<Resource> download(
            @RequestParam(name = "fileName", required = false) String fileName,
            @RequestParam(name = "folder", required = false) String folder)
            throws StorageException, URISyntaxException, FileNotFoundException {
        if (fileName == null || folder == null) {
            throw new StorageException("Missing required params : (fileName or folder) in query params.");
        }

        // check file exist (and not a directory)
        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0) {
            throw new StorageException("File with name = " + fileName + " not found.");
        }

        // download a file
        InputStreamResource resource = this.fileService.getResource(fileName, folder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
