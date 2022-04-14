package com.kifiyapro.bunchi.Controller;

import com.kifiyapro.bunchi.Service.AdoptService;
import com.kifiyapro.bunchi.dto.AdoptResponseDto;
import com.kifiyapro.bunchi.dto.Baselist;
import com.kifiyapro.bunchi.dto.SearchDto;
import com.kifiyapro.bunchi.dto.requestDto.AdoptRequestDto;
import com.kifiyapro.bunchi.dto.responseDto.AdoptResponseDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/adopt")
@Controller
public class AdoptController {
    @Autowired
    private AdoptService adoptService;

    @PostMapping("/adopt")
    public ResponseEntity<AdoptResponseDto> adopt(@RequestBody AdoptRequestDto adoptRequestDto) {
        return ResponseEntity.ok().body(adoptService.adopt(adoptRequestDto));
    }
// @PostMapping("/generate_report")
//    public ResponseEntity<AdoptResponseDto> generate_report(@RequestBody AdoptRequestDto adoptRequestDto) {
//        return ResponseEntity.ok().body(adoptService.generate_report(adoptRequestDto));
//    }

    @GetMapping("/get_adoption_requests")

    public Baselist<AdoptResponseDtos> get_adoption_requests(@RequestBody SearchDto s) {
        return adoptService.get_adoption_requests(s);
    }
}
