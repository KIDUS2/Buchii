package com.kifiyapro.bunchi.modle;

import com.kifiyapro.bunchi.dto.TaskDTO;
import com.kifiyapro.bunchi.dto.responseDto.ResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl {
//    @Value("${resource.tasks}")
    private String resource;
//    @Value("${resource.tasks}/{id}")
    private String idResource;
//    @Autowired
    private RestTemplate restTemplate;

    public List<TaskDTO> findAll() {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJIa0NZZThLTHkxYkk0Q05rbHMxWnBOR0xtejNzcTltT05lc0ozNEFkbUtTU29GSG5QWiIsImp0aSI6ImViYWY0ZGI0ZWE1ZDQ4MGUyNDc4M2NlMTQzZmQ5OGFiNWY0NzNhNzk5OTg4YTViZmMxMmQ4YmEwYTc3MzkxODNiNDM4MDU2YTc0NDM4NGY2IiwiaWF0IjoxNjUwMDUxMDM3LCJuYmYiOjE2NTAwNTEwMzcsImV4cCI6MTY1MDA1NDYzNywic3ViIjoiIiwic2NvcGVzIjpbXX0.DAySZpczG902BHZogUMhYMw9sc_f-y_cCdEvibcC74BmAxllOVmBg8UpFpf1IPDyoszPKUZo13Ob_J0SOfdiT0q8FWOi9co34wpZhxmtA5fzdIEy-VPDIdWjrv9I_kdEAnkgwgEgDW-MiNMuocmaIcn7vvM6gaxNYR8d2CCkBVj7b1E6d6KiABUkU52CNEz3IrfZoP1OWSFa_0k2GE9uGyeuXdjiEbJZwD5h0tZ1bgR-8DU9sA0xBTbdomggwNjN8hAkZ3wRra0JVW9ieI0epkfnr6mWgKmaDbnHTzAqIYLyaaQ4KRKoEvxYT2Gophkeq9Rk86BfRGRyQBnhQ13RYg";
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + token);
        String t="https://api.petfinder.com/v2/animals?type=dog&page=2";
//        Animals[] forObject = restTemplate.getForObject(t, Animals[].class);
        HttpEntity <String> entity = new HttpEntity <> (headers);
        ResponseEntity<ResponseDto> exchange = restTemplate.exchange(t, HttpMethod.GET, entity, ResponseDto.class);

        exchange.getBody().getAnimals().forEach(animals -> {
            if(animals.getPhotos()!=null && animals.getPhotos().length>0) {
                System.out.println(animals.getPhotos()[0].getSmall());
            }
        });
        //List<Animals> petResDtoList=Arrays.stream(restTemplate.getForObject(t, Animals[].class,headers)).collect(Collectors.toList());

//        petResDtoList.forEach(petResDto -> {
//            System.out.println(petResDto.getPhotos().get(0));
//        });
        System.out.println("111");
        List<TaskDTO> taskDTOList=Arrays.stream(restTemplate.getForObject(resource, TaskDTO[].class)).collect(Collectors.toList());
//        return Arrays.stream(restTemplate.getForObject(resource, TaskDTO[].class)).collect(Collectors.toList());
        return taskDTOList;
    }

    public TaskDTO update(Long id, TaskDTO task) {
        return restTemplate.exchange(idResource, HttpMethod.PUT, new HttpEntity<>(task), TaskDTO.class, id).getBody();
    }

    public void delete(Long id) {
        restTemplate.delete(idResource, id);
    }

    public TaskDTO create(TaskDTO task) {
        return restTemplate.postForObject(resource, task, TaskDTO.class);
    }
}
