package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.PetIdResponseDto;
import com.kifiyapro.bunchi.dto.PetSearchDto;
import com.kifiyapro.bunchi.dto.responseDto.PetResponseDto;
import com.kifiyapro.bunchi.dto.responseDto.ResponseDto;
import com.kifiyapro.bunchi.modle.Pet;
import com.kifiyapro.bunchi.repository.PetRepository;
import com.kifiyapro.bunchi.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {


    private final PetRepository petRepository;
    private final PictureRepository pictureRepository;


    @Autowired
    RestTemplate restTemplate;
    private final Path root = Paths.get("uploads");

    @Autowired
    public PetService(PetRepository petRepository, PictureRepository pictureRepository) {
        this.petRepository = petRepository;
        this.pictureRepository = pictureRepository;}
//        this.fileStorageLocationForPet = Paths.get(PetPath).toAbsolutePath().normalize();    }

    /***********
     * create pet and uploade picture
     * @param pets and multipartFile
     * @return
     */
    public PetIdResponseDto create_pet(Pet pets ,MultipartFile multipartFile) {
        Pet pet = new Pet();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {


            pet.setType(pets.getType());
            pet.setAge(pets.getAge());
            pet.setGender(pets.getGender());
            pet.setCreatedOn(Instant.now());
            pet.setSize(pets.getSize());
            pet.setPhoto(fileName);
            pet.setGoodWithChildren(pets.getGoodWithChildren());
            petRepository.save(pet);
            String uploadDir = "pet-photos/" + pet.getPetId();

            FileUploader.saveFile(uploadDir, fileName, multipartFile);

            return new PetIdResponseDto("success", pet.getPetId());

        }catch (Exception e){
        return new PetIdResponseDto("failed to get the id",pet.getPetId());
        }

        }



    /***********************
     * resopnse for pet from local database  and form petfinder
     * @param petRequestDto
     * @return
     */

    public List<PetResponseDto> get_pets(PetSearchDto petRequestDto) {

        List<PetResponseDto> petResponseDtos = new ArrayList<>();
        List<Pet> pets = petRepository.findAllByTypeAndAgeAndSizeAndGoodWithChildrenAndGender(petRequestDto.getType(), petRequestDto.getAge(), petRequestDto.getSize(), petRequestDto.getGoodWithChildren(),petRequestDto.getGender(),petRequestDto.getLimit());
        pets.forEach(pet -> {
            PetResponseDto petRes=new PetResponseDto();
            petRes.setPetId(pet.getPetId());
            petRes.setAge(pet.getAge());
            petRes.setSize(pet.getSize());
            petRes.setType(pet.getType());
            petRes.setGender(pet.getGender());
            pet.setGoodWithChildren(pet.getGoodWithChildren());
            petResponseDtos.add(petRes);
        });

        if(pets.size()>=petRequestDto.getLimit()){
            //mapping pets->pet\Responsedto



        }else{

            Long remaining=petRequestDto.getLimit()-pets.size();
            //mapping pets->pet\Responsedto
            String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJIa0NZZThLTHkxYkk0Q05rbHMxWnBOR0xtejNzcTltT05lc0ozNEFkbUtTU29GSG5QWiIsImp0aSI6ImM4ODUwMWY3NDg1MTE5ZTRjYjI1ZWZhODYyOWVlMmI1MTcwYjE3ZjRmOTdiMTdmNTJhODY4NmI3MmY4OTg0OGE1YmE2OTA4ZDFlMTE2NGMzIiwiaWF0IjoxNjUwMzY1NDIzLCJuYmYiOjE2NTAzNjU0MjMsImV4cCI6MTY1MDM2OTAyMywic3ViIjoiIiwic2NvcGVzIjpbXX0.tgiIIAsPj7mPKptjb8_YCmNZXO8abUBJOkMv18hmnL3ebAGPsdrVVu7DFknCns9noQOybxXPz_8hh4nylYg__PQ_3xSCAB1TBHd4R1krtdiy9hqmaC52M1eIegM5kUwOgo6LeKrV3Oys3E2BhpZ_Sl2EAjooI44FRiMqJ_ngPT_hY667udyjA5sURQ7yz3gr9vIqAS60ngIGPIppa3MUyjJLcUYvBQ2fc7oWJ96_Z2R8Jy1r5-abmsibcXxTcMkpwKU31fd4CiD8TxWt7UaBawDrBX274Vbb7HE1gIXY9qw6s8tzwioFSgfJ64iPaQdkhzPjMFx8Z-WFhgY-h6JrTA";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            String t="https://api.petfinder.com/v2/animals?type=dog&limit="+String.valueOf(remaining)+"&page=2";
            HttpEntity<String> entity = new HttpEntity <> (headers);
            ResponseEntity<ResponseDto> exchange =
                    restTemplate.exchange(t, HttpMethod.GET, entity, com.kifiyapro.bunchi.dto.responseDto.ResponseDto.class);
            exchange.getBody().getAnimals().forEach(animals -> {
                if(animals.getPhotos()!=null && animals.getPhotos().length>0) {
//                    System.out.println(animals.getPhotos()[0].getSmall());
                    //mapping
                    PetResponseDto petResponseDto=new PetResponseDto();
                    petResponseDto.setPhotos(animals.getPhotos());
                    petResponseDto.setPetId(animals.getId());
                    petResponseDto.setType(animals.getType());
                    petResponseDto.setGender(animals.getGender());
                    petResponseDto.setAge(animals.getAge());
                    petResponseDto.setSize(animals.getSize());
                    petResponseDto.setStatus(animals.getStatus());


                    petResponseDtos.add(petResponseDto);
                }
            });

        }



        return petResponseDtos;

    }


}