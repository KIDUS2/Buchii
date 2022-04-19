package com.kifiyapro.bunchi.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiyapro.bunchi.Service.PetService;
import com.kifiyapro.bunchi.dto.PetIdResponseDto;
import com.kifiyapro.bunchi.dto.PetSearchDto;
import com.kifiyapro.bunchi.dto.responseDto.PetResponseDto;
import com.kifiyapro.bunchi.modle.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<PetIdResponseDto> create_pet(@RequestParam(value = "pet", required = false)  String pet , @RequestParam("image")MultipartFile multipartFile) {
        {
            ObjectMapper om = new ObjectMapper();
            Pet pet1 = null;
            try {
                pet1 = om.readValue(pet, Pet.class);   //string st -> MyInput input
            } catch (IOException e) {
                e.printStackTrace();
            }


            return ResponseEntity.ok().body(petService.create_pet(pet1, multipartFile));
        }
    }

    @GetMapping("/get_pets")

    public List<PetResponseDto> get_pets(PetSearchDto p) {
        return petService.get_pets(p);
    }

    /*************************
     * file uploader
     * @param id
     * @param files
     * @return
     */
//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("id") Long id,
//                                                        @RequestParam("files") MultipartFile[] files) {
//        List<UploadFileResponse> uploadFileResponses = new ArrayList<>();
//
//        Arrays.asList(files).stream().forEach(file -> {
//            String fileName = petService.storeToDb(id, file, "pet");
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path(petService.PetPath)
//                    .path("/")
//                    .path(fileName)
//                    .toUriString();
//
//            UploadFileResponse uploadFileResponse = new UploadFileResponse();
//            uploadFileResponse.setFileName(fileName);
//            uploadFileResponse.setFileDownloadUri(fileDownloadUri);
//            uploadFileResponse.setFileName(file.getContentType());
//            uploadFileResponse.setSize(file.getSize());
//
//            uploadFileResponses.add(uploadFileResponse);
//        });
//        return uploadFileResponses;
//
//    }






}
