package org.example;

import java.util.List;

public class Character {
    private Books book;
    private String bossName;
    private String characterName;

    public Character(String characterName, String bossName, Books book) {
        this.characterName = characterName;
        this.bossName = bossName;
        this.book = book;
    }

    public Books getBook() {
        return book;
    }

    public String getBossName() {
        return bossName;
    }

    public String getCharacterName() {
        return characterName;
    }
}
