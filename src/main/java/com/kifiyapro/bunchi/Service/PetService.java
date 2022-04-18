package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.PetIdResponseDto;
import com.kifiyapro.bunchi.dto.PetSearchDto;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.responseDto.PetRes;
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

    public Baselist<PetRes> get_pets(PetSearchDto petRequestDto) {
        Baselist<PetRes> petResponseDtoBaselist = new Baselist();
        List<PetRes> petResponseDtos = new ArrayList<>();
        List<Pet> pets = petRepository.findAllByTypeAndAgeAndSizeAndGoodWithChildrenAndGender(petRequestDto.getType(), petRequestDto.getAge(), petRequestDto.getSize(), petRequestDto.getGoodWithChildren(),petRequestDto.getGender(),petRequestDto.getLimit());
        pets.forEach(pet -> {
            PetRes petResponseDto = new PetRes();
            petResponseDto.setPetId(pet.getPetId());
//            petResponseDto.setCreatedOn(pet.getCreatedOn());



            petResponseDtos.add(petResponseDto);
        });

        petResponseDtoBaselist.setDatas(petResponseDtos);
        petResponseDtoBaselist.setResponseDto(new ResponseDto(true, "listed"));
        petResponseDtoBaselist.setCount(petRepository.count());

        return petResponseDtoBaselist;

    }

    /**************
     * picture uploader for the pet and add in the databases
     * @param id
     * @param multipartFile
     * @param whereToStore
     * @return
     */
    public String storeToDb(Long id, MultipartFile multipartFile,String whereToStore) {
        String fileName;

            Picture picture = new Picture();
//            picture.setPet(petRepository.findPetsByPet_id(id));
            fileName = storeFile(id, multipartFile, whereToStore);
            picture.setPricturePath("pet" + "/" + fileName);
            picture.setUploadedOn(Instant.now());
            pictureRepository.save(picture);
            return fileName;

    }

    /***********
     *
     * @param id
     * @param file
     * @param whereToStore
     * @return
     */
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