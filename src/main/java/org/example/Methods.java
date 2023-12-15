package org.example;

import java.util.List;

public class Methods {
    public void announcement(List<Character> list, List<Character> promptedCharacters) {
        if (list.size() > 0) {
            for (Character characterName : list) {
                System.out.println("You can farm " + characterName.getBook().getBookName() + " from the city of " + characterName.getBook().getRegion() + " for " + characterName.getCharacterName());
            }
        } else {
            for (Character characterName : promptedCharacters) {
                System.out.println("You can farm " + characterName.getBossName() + " for " + characterName.getCharacterName());
            }

        }
    }
}
