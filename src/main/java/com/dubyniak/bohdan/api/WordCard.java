package com.dubyniak.bohdan.api;

public class WordCard implements GeneralEntity {
    private Long id;
    private String frontSide;
    private String backSide;

    public WordCard() {
    }

    public WordCard(Long id, String frontSide, String backSide) {
        this.id = id;
        this.frontSide = frontSide;
        this.backSide = backSide;
    }

    public Long getId() {
        return id;
    }

    public String getFrontSide() {
        return frontSide;
    }

    public String getBackSide() {
        return backSide;
    }
}
