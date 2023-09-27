package data.controller.file;

import data.dto.Response;
import data.service.file.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/wcs/image")
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<Response> uploadImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam("path") String path) throws IOException {

        Response res = new Response();
        try{
            String result = storageService.saveFile(file,path);
            res.setImageLocation("/" + path + "/" +result);
            res.setMessage("done");
            res.setSuccess(true);
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("failed");
            res.setSuccess(false);
            return new ResponseEntity<Response>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<Response> postImageUpload (@RequestParam("files") MultipartFile[] files,
                                                     @RequestParam("path") String path) {
        Response res = new Response();
        List<String> results = new ArrayList<>();
        List<String> imageLocations = new ArrayList<>();

        try {
            results = storageService.saveFiles(files,path);
            for(String result : results) {
                imageLocations.add("/" + path + "/" + result);
            }
            res.setImageLocations(imageLocations);
            res.setMessage("done");
            res.setSuccess(true);
            return new ResponseEntity<Response>(res,HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage("failed");
            res.setSuccess(false);
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}