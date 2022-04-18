package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.AdoptResponseDto;
import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.SearchDto;
import com.kifiyapro.bunchi.dto.requestDto.AdoptRequestDto;
import com.kifiyapro.bunchi.dto.responseDto.AdoptResponseDtos;
import com.kifiyapro.bunchi.dto.responseDto.CustomerResponseDto;
import com.kifiyapro.bunchi.dto.responseDto.PetResponseDto;
import com.kifiyapro.bunchi.dto.responseDto.ReportResponseDto;
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

    /***************************
     *
     * adopt take pet and customer id
     * @Author kidus
     * @param adoptRequestDto
     * @return
     */
    public AdoptResponseDto adopt(AdoptRequestDto adoptRequestDto) {
        Customer customer = customerRepository.findById(adoptRequestDto.getCustomer_Id()).get();
        Pet pet = petRepository.findById(adoptRequestDto.getPet_Id()).get();

        try{

            Adopt adopt = new Adopt();
        if ( customer.getCustomerId() == 0 ||  pet.getPetId() == 0) {
            return new AdoptResponseDto("customer or pet id does  not exit", null);
        }
                if(adopt.getCustomer().getCustomerId()!=0 || adopt.getPet().getPetId()!=0){
                return new AdoptResponseDto("new or pet id does  not exit", null);

            }

        adopt.setPet(pet);
        adopt.setCustomer(customer);
        adopt.setCreatedOn(Instant.now());
        adoptRepository.save(adopt);
        return new AdoptResponseDto("success", adopt.getAdoptionId());
    }
    catch (Exception e){
        return new AdoptResponseDto("failed to get the id",null);
        }
    }

    /**********
     * ***
     * response list of pet and customer
     * @Author kidus
     * @param searchDto
     * @return
     */
    public Baselist<AdoptResponseDtos> get_adoption_requests(SearchDto searchDto) {
        Baselist<AdoptResponseDtos> adoptResponseDtosBaselist = new Baselist();
        List<AdoptResponseDtos> adoptResponseDtos = new ArrayList<>();
        List<Adopt> adopts = adoptRepository.findAllByCreatedOnBetween(searchDto.getFrom_date(), searchDto.getTo_date());
//        List<Adopt> adopts = adoptRepository.findByCreated_onBetween(searchDto.getFrom_date(),searchDto.getTo_date());
        adopts.forEach(adopt -> {
            CustomerResponseDto customerResponseDto = new CustomerResponseDto();
            customerResponseDto.setCustomer_id(adopt.getCustomer().getCustomerId());
            customerResponseDto.setCreated_on(adopt.getCustomer().getCreatedOn());
            customerResponseDto.setPhonenumber(adopt.getCustomer().getPhonenumber());
            customerResponseDto.setFullname(adopt.getCustomer().getFullname());
            customerResponseDto.setUpdated_on(adopt.getCustomer().getUpdatedOn());
            PetResponseDto petResponseDto = new PetResponseDto();
            petResponseDto.setType(adopt.getPet().getType());
            petResponseDto.setAge(adopt.getPet().getAge());
            petResponseDto.setCreatedOn(adopt.getPet().getCreatedOn());
            petResponseDto.setStatus(adopt.getPet().getGoodWithChildren());
            petResponseDto.setSize(adopt.getPet().getSize());
            petResponseDto.setGender(adopt.getPet().getGender());
            petResponseDto.setPetId(adopt.getPet().getPetId());
            petResponseDto.setUpdatedOn(adopt.getPet().getUpdatedOn());


            AdoptResponseDtos adoptResponseDto = new AdoptResponseDtos();
            adoptResponseDto.setAdopt_Id(adopt.getAdoptionId());
            adoptResponseDto.setCreated_on(adopt.getCreatedOn());
            adoptResponseDto.setUpdated_on(adopt.getUpdatedOn());
            adoptResponseDto.setPetResponseDto(petResponseDto);
            adoptResponseDto.setCustomerResponseDto(customerResponseDto);
            adoptResponseDtos.add(adoptResponseDto);
        });

        adoptResponseDtosBaselist.setDatas(adoptResponseDtos);
        adoptResponseDtosBaselist.setResponseDto(new ResponseDto(true, "listed"));
        adoptResponseDtosBaselist.setCount(adoptRepository.count());

        return adoptResponseDtosBaselist;

    }

    /*************
     * response for list of adoption
     * @param searchDto
     * @return
     */
    public ReportResponseDto generate_report(SearchDto searchDto) {

        List<AdoptRepository.ListweeklyandTotalCount> listweeklyandTotalCounts = adoptRepository.listweeklyandTotalCounts(searchDto.getFrom_date(), searchDto.getTo_date());
        listweeklyandTotalCounts.forEach(listweeklyandTotalCount -> {
            ReportResponseDto reportResponseDto = new ReportResponseDto();

                Datas datas =new Datas();
            datas.setWeekly_adoption_requests(listweeklyandTotalCounts);
//            datas.setAdopted_pet_types(listweeklyandTotalCounts);
            reportResponseDto.setData(datas);
            listweeklyandTotalCounts.add(listweeklyandTotalCount);

        });
        return new ReportResponseDto("sucess",null);
    }
}