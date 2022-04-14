package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.PetIdResponseDto;
import com.kifiyapro.bunchi.dto.requestDto.PetRequestDto;
import com.kifiyapro.bunchi.modle.Pet;
import com.kifiyapro.bunchi.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
@Transactional
public class PetService {



    private final PetRepository petRepository;
    private final Path root = Paths.get("uploads");
    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    public PetIdResponseDto create_pet(PetRequestDto petRequestDto) {


        Pet pet=new Pet();
        pet.setType(petRequestDto.getType());
        pet.setAge(petRequestDto.getAge());
        pet.setGender(petRequestDto.getGender());
        pet.setCreated_on(Instant.now());

        pet.setPhoto("");



        pet.setGood_with_children(petRequestDto.getGood_with_children());

        petRepository.save(pet);
        return new PetIdResponseDto("success", pet.getPet_id());

    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
