package com.lld.hackathon.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
