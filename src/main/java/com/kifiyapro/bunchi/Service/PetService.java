package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.PetIdResponseDto;
import com.kifiyapro.bunchi.dto.PetSearchDto;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.requestDto.PetRequestDto;
import com.kifiyapro.bunchi.dto.responseDto.PetResponseDto;
import com.kifiyapro.bunchi.modle.Pet;
import com.kifiyapro.bunchi.modle.Picture;
import com.kifiyapro.bunchi.repository.PetRepository;
import com.kifiyapro.bunchi.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PetService {


    private final PetRepository petRepository;
    private final PictureRepository pictureRepository;
    public final Path fileStorageLocationForPet;
    public String PetPath = System.getProperty("jboss.home.dir") + "/welcome-content/users/";//For Linux


    private final Path root = Paths.get("uploads");

    @Autowired
    public PetService(PetRepository petRepository, PictureRepository pictureRepository) {
        this.petRepository = petRepository;
        this.pictureRepository = pictureRepository;
        this.fileStorageLocationForPet = Paths.get(PetPath).toAbsolutePath().normalize();    }


    public PetIdResponseDto create_pet(PetRequestDto petRequestDto) {


        Pet pet = new Pet();
        pet.setType(petRequestDto.getType());
        pet.setAge(petRequestDto.getAge());
        pet.setGender(petRequestDto.getGender());
        pet.setCreated_on(Instant.now());
        pet.setPhoto("");
        pet.setGood_with_children(petRequestDto.getGood_with_children());

        petRepository.save(pet);
        return new PetIdResponseDto("success", pet.getPet_id());

    }


    public Baselist<PetResponseDto> get_pets(PetSearchDto petRequestDto) {
        Baselist<PetResponseDto> petResponseDtoBaselist = new Baselist();
        List<PetResponseDto> petResponseDtos = new ArrayList<>();
        List<Pet> pets = petRepository.listPetBy(petRequestDto.getType(), petRequestDto.getAge(), petRequestDto.getSize(), petRequestDto.getGood_with_children(), petRequestDto.getGender(), petRequestDto.getLimit());
        pets.forEach(pet -> {
            PetResponseDto petResponseDto = new PetResponseDto();
            petResponseDto.setPet_id(pet.getPet_id());
//                petResponseDto.set


            petResponseDtos.add(petResponseDto);
        });

        petResponseDtoBaselist.setDatas(petResponseDtos);
        petResponseDtoBaselist.setResponseDto(new ResponseDto(true, "listed"));
        petResponseDtoBaselist.setCount(petRepository.count());

        return petResponseDtoBaselist;

    }

    public String storeToDb(Long id, MultipartFile multipartFile,String whereToStore) {
        String fileName;

            Picture picture = new Picture();
            picture.setPet(petRepository.findByPet_id(id));
            fileName = storeFile(id, multipartFile, whereToStore);
            picture.setPricturePath("pet" + "/" + fileName);
            picture.setUploadedOn(Instant.now());
            pictureRepository.save(picture);
            return fileName;

    }

    private String storeFile(Long id, MultipartFile file, String whereToStore) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = "";
        try {
            if (originalFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            String replacedDate = date.trim().replaceAll(":", "-").replaceAll(" ", "");
            System.out.println("replaced -> " + replacedDate);
            fileName = id + "_" + replacedDate + fileExtension;
            Path targetLocation;
            if (whereToStore.equals("users")) {
                targetLocation = this.fileStorageLocationForPet.resolve(fileName);
            } else {
                targetLocation = this.fileStorageLocationForPet.resolve(fileName);
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;


        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }


    }
}