package com.kifiyapro.bunchi.Controller;

import com.kifiyapro.bunchi.Service.PetService;
import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.PetIdResponseDto;
import com.kifiyapro.bunchi.dto.PetSearchDto;
import com.kifiyapro.bunchi.dto.requestDto.PetRequestDto;
import com.kifiyapro.bunchi.dto.responseDto.PetRes;
import com.kifiyapro.bunchi.dto.responseDto.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/pet")
@Controller


public class PetController {

    private final PetService petService;
    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }



    @PostMapping("/create_pet")
    public ResponseEntity<PetIdResponseDto> create_pet(@RequestBody PetRequestDto petRequestDto) {
        return ResponseEntity.ok().body(petService.create_pet(petRequestDto));
    }


    @GetMapping("/get_pets")

    public Baselist<PetRes> get_pets(PetSearchDto p) {
        return petService.get_pets(p);
    }

    /*************************
     * file uploader
     * @param id
     * @param files
     * @return
     */
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("id") Long id,
                                                        @RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponse> uploadFileResponses = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {
            String fileName = petService.storeToDb(id, file, "pet");
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(petService.PetPath)
                    .path("/")
                    .path(fileName)
                    .toUriString();

            UploadFileResponse uploadFileResponse = new UploadFileResponse();
            uploadFileResponse.setFileName(fileName);
            uploadFileResponse.setFileDownloadUri(fileDownloadUri);
            uploadFileResponse.setFileName(file.getContentType());
            uploadFileResponse.setSize(file.getSize());

            uploadFileResponses.add(uploadFileResponse);
        });
        return uploadFileResponses;

    }






}
