package com.kifiyapro.bunchi.Service;

import com.kifiyapro.bunchi.dto.AdoptResponseDto;
import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.ResponseDto;
import com.kifiyapro.bunchi.dto.SearchDto;
import com.kifiyapro.bunchi.dto.requestDto.AdoptRequestDto;
import com.kifiyapro.bunchi.dto.responseDto.*;
import com.kifiyapro.bunchi.modle.Adopt;
import com.kifiyapro.bunchi.modle.Customer;
import com.kifiyapro.bunchi.modle.Pet;
import com.kifiyapro.bunchi.repository.AdoptRepository;
import com.kifiyapro.bunchi.repository.CustomerRepository;
import com.kifiyapro.bunchi.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

        try {
            Optional<Customer> customer = customerRepository.findById(adoptRequestDto.getCustomer_Id());
            Optional<Pet> pet = petRepository.findById(adoptRequestDto.getPet_Id());
            Adopt adopt = new Adopt();
            if (!customer.isPresent() || !pet.isPresent()) {
                return new AdoptResponseDto("customer or pet id does  not exit", null);
            } else {
                adopt.setPet(pet.get());
                adopt.setCustomer(customer.get());
                adopt.setCreatedOn(new Date());
                adoptRepository.save(adopt);
                return new AdoptResponseDto("success", adopt.getAdoptionId());
            }
        } catch (Exception e) {
            return new AdoptResponseDto("failed to get the id", null);
        }
    }

    /**********
     * ***
     * response list of pet and customer
     * @Author kidus
     * @param searchDto
     * @return
     */
    public Baselist<AdoptResponseDtos> get_adoption_requests(SearchDto searchDto) throws ParseException {

        Baselist<AdoptResponseDtos> adoptResponseDtosBaselist = new Baselist();
        List<AdoptResponseDtos> adoptResponseDtos = new ArrayList<>();
//        List<Adopt> adopts = adoptRepository.findAllByCreatedOnBetween(from, to);
        List<Adopt> adopts = adoptRepository.findAllByCreatedOnBetween(searchDto.getFrom_date(), searchDto.getTo_date());
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

            petResponseDto.setStatus(adopt.getPet().getGoodWithChildren());
            petResponseDto.setSize(adopt.getPet().getSize());
            petResponseDto.setGender(adopt.getPet().getGender());
            petResponseDto.setPetId(adopt.getPet().getPetId());



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

    int petCount = 0;

    public AdoptCountResDto generate_report(SearchDto searchDto) throws ParseException {
        AdoptCountResDto adoptCountResDto = new AdoptCountResDto();
        List<String> petTypeList = new ArrayList<>();

        System.out.println("all duplicated pets -> " + petRepository.findAll().size());
        petRepository.findAll().forEach(pet -> {
            if (!petTypeList.contains(pet.getType())) {
                petTypeList.add(pet.getType());
            }
        });
        System.out.println("all distinct pets -> " + petTypeList.size());


        HashMap<String, Integer> map1 = new HashMap<>();
        petTypeList.forEach(petType -> {
            List<Pet> pets = petRepository.findPetsByType(petType);
            pets.forEach(pet -> {
                petCount = petCount + adoptRepository.countAdoptByPet(pet);
            });
            map1.put(petType, petCount);
        });

        long diff = searchDto.getTo_date().getTime() - searchDto.getFrom_date().getTime();

        TimeUnit time = TimeUnit.DAYS;
        int diffrence = (int) time.convert(diff, TimeUnit.MILLISECONDS);

        int week = diffrence / 7;
        List<Date> dates = new ArrayList<>();
        if (week != 0) {
            for (int i = 0; i <= week; i++) {
                if (i == 0) {
                    dates.add(searchDto.getFrom_date());
                } else {
                    LocalDate lastDate = dates.get(dates.size() - 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate lastDatePlusSeven = lastDate.plusWeeks(1);
                    Date date = Date.from(lastDatePlusSeven.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = simpleDateFormat.parse(getDate(date.toString()));
                    System.out.println("parsed date ---> " + parsedDate);

                    dates.add(parsedDate);
                }
            }
        }


        int count = 0;
        HashMap<String, Integer> map2 = new HashMap<>();
        for (int i = 0; i < dates.size(); i++) {


            if (i == 0) {
                count = adoptRepository.countAdoptByCreatedOnBetween(dates.get(i), dates.get(i));

            } else {
                count = adoptRepository.countAdoptByCreatedOnBetween(dates.get(i - 1), dates.get(i));
            }
            map2.put(getDate(dates.get(i).toString()), count);
        }


        adoptCountResDto.setStatus("success");
        adoptCountResDto.setData(new Data(map1, map2));
        return adoptCountResDto;
    }


    private String getDate(String dateStr) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = (Date) formatter.parse(dateStr);
        System.out.println(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        System.out.println("formatedDate : " + formatedDate);
        return formatedDate;

    }

}