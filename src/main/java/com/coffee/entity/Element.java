package com.coffee.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Element {
    private int id;
    private String name;
    private int price;
    private String category;
    private int stock;
    private String image;
    private String description;
}
