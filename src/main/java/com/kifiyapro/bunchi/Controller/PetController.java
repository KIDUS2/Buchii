package com.kifiyapro.bunchi.Controller;

import com.kifiyapro.bunchi.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import springfox.documentation.service.ResponseMessage;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/pet")
@Controller


public class PetController {


    @Autowired
    PetService petService;




    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
//                petService.save(file);
                fileNames.add(file.getOriginalFilename());
            });

            fileNames.forEach(fileNames1 -> {
                System.out.println("weeeeeeeee" + fileNames1.getBytes());
                fileNames.add("1");

            });
            message = "Uploaded the files successfully: " + fileNames;
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("444"));
            return "yes";
        } catch (Exception e) {
            message = "Fail to upload files!";
            return "no";
        }
    }
}
