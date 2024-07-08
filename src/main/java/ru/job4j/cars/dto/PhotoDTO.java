package ru.job4j.cars.dto;

import lombok.Data;

@Data
public class PhotoDTO {
    private String name;

    private byte[] content;
}
