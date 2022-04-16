package com.kifiyapro.bunchi.dto.responseDto;

import com.kifiyapro.bunchi.modle.Animals;

import java.util.List;

public class ResponseDto {

    private List<Animals> animals;

    public ResponseDto(List<Animals> animals) {
        this.animals = animals;
    }

    public ResponseDto() {
    }

    public List<Animals> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animals> animals) {
        this.animals = animals;
    }
}
