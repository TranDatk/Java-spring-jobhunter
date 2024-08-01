package vn.datk.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.datk.jobhunter.service.FileService;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "${apiPrefix}/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @Value("${datk.upload-file.base-uri}")
    private String baseURI;
    @PostMapping("")
    public ResponseEntity<Void> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, IOException {
        this.fileService.createUploadFolder(baseURI + folder);
        this.fileService.store(file, folder);
        return ResponseEntity.ok().body(null);
    }
}
