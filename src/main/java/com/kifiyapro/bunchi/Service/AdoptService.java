package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.AdoptResponseDto;
import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.SearchDto;
import com.kifiyapro.bunchi.dto.requestDto.AdoptRequestDto;
import com.kifiyapro.bunchi.dto.responseDto.AdoptResponseDtos;
import com.kifiyapro.bunchi.dto.responseDto.CustomerResponseDto;
import com.kifiyapro.bunchi.dto.responseDto.PetResponseDto;
import com.kifiyapro.bunchi.modle.Adopt;
import com.kifiyapro.bunchi.modle.Customer;
import com.kifiyapro.bunchi.modle.Pet;
import com.kifiyapro.bunchi.repository.AdoptRepository;
import com.kifiyapro.bunchi.repository.CustomerRepository;
import com.kifiyapro.bunchi.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdoptService {


    private final AdoptRepository adoptRepository;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    @Autowired
    public AdoptService(AdoptRepository adoptRepository, CustomerRepository customerRepository, PetRepository petRepository) {
        this.adoptRepository = adoptRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }


    public AdoptResponseDto adopt(AdoptRequestDto adoptRequestDto) {
        Customer customer = customerRepository.getById(adoptRequestDto.getCustomer_Id());
        Pet pet = petRepository.getById(adoptRequestDto.getPet_Id());
        Adopt adopt = new Adopt();
//        try{

        if (customer == null || pet == null) {
            return new AdoptResponseDto("customer or pet id does  not exit", null);
        }

        adopt.setPet(pet);
        adopt.setCustomer(customer);
        adopt.setCreated_on(Instant.now());
        adoptRepository.save(adopt);
        return new AdoptResponseDto("success", adopt.getAdoption_id());
    }
//    catch (Exception e){
//        return new AdoptResponseDto("failed to get the id",adopt.getAdoption_id());
//        }
//    }



    public Baselist<AdoptResponseDtos> get_adoption_requests(SearchDto searchDto) {
        Baselist<AdoptResponseDtos> adoptResponseDtosBaselist = new Baselist();
        List<AdoptResponseDtos> adoptResponseDtos = new ArrayList<>();
        List<Adopt> adopts = adoptRepository.findAllByAdoptTimeBetween(searchDto.getFrom_date(), searchDto.getTo_date());
        adopts.forEach(adopt -> {
        CustomerResponseDto customerResponseDto=new CustomerResponseDto();
            customerResponseDto.setCustomer_id(adopt.getCustomer().getCustomer_id());
            customerResponseDto.setCreated_on(adopt.getCustomer().getCreated_on());
            customerResponseDto.setPhonenumber(adopt.getCustomer().getPhonenumber());
            customerResponseDto.setFullname(adopt.getCustomer().getFullname());
            customerResponseDto.setUpdated_on(adopt.getCustomer().getUpdated_on());
        PetResponseDto petResponseDto=new PetResponseDto();
            petResponseDto.setName(adopt.getPet().getType());
            petResponseDto.setAge(adopt.getPet().getAge());
            petResponseDto.setCreated_on(adopt.getPet().getCreated_on());
            petResponseDto.setStatus(adopt.getPet().getGood_with_children());
            petResponseDto.setSize(adopt.getPet().getSize());
            petResponseDto.setGender(adopt.getPet().getGender());
            petResponseDto.setPet_id(adopt.getPet().getPet_id());
            petResponseDto.setUpdated_on(adopt.getPet().getUpdated_on());


            AdoptResponseDtos adoptResponseDto = new AdoptResponseDtos();
            adoptResponseDto.setAdopt_Id(adopt.getAdoption_id());
            adoptResponseDto.setCreated_on(adopt.getCreated_on());
            adoptResponseDto.setUpdated_on(adopt.getUpdated_on());
            adoptResponseDto.setPetResponseDto(petResponseDto);
            adoptResponseDto.setCustomerResponseDto(customerResponseDto);
            adoptResponseDtos.add(adoptResponseDto);
        });

        adoptResponseDtosBaselist.setDatas(adoptResponseDtos);
        adoptResponseDtosBaselist.setResponseDto(new ResponseDto(true, "listed"));
        adoptResponseDtosBaselist.setCount(adoptRepository.count());

        return adoptResponseDtosBaselist;

    }
//    public Baselist<AdoptResponseDtos> generate_report(SearchDto searchDto) {
//        Baselist<AdoptResponseDtos> adoptResponseDtosBaselist = new Baselist();
//        List<AdoptResponseDtos> adoptResponseDtos = new ArrayList<>();
//        List<Adopt> adopts = adoptRepository.findAllByAdoptTimeBetween(searchDto.getFrom_date(), searchDto.getTo_date());
//        adopts.forEach(adopt -> {
}