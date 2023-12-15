package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    boolean doWeeklyBoss = false;
    final int MONDAY_THURSDAY_SUNDAY = 1;
    final int TUESDAY_FRIDAY_SUNDAY = 2;
    final int WEDNESDAY_SATURDAY_SUNDAY = 3;
    Map<String, Books> books = new HashMap<>();
    String[] daysOfWeekArray = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Map<String, List<Character>> daysOfWeekMap = new HashMap<>();
    Map<String, Character> characters = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    List<Character> promptedCharacters = new ArrayList<>();
    List<String> listOfCharacters = new ArrayList<>();

    public static void main(String[] args) {

        Main app = new Main();
        app.run();
    }

    private void run() {
        initializeTalentBooks();

        initializeCharacters();

        initializeDaysOfWeek();

        promptForCharacters();

        promptForBossDrops();

        /*  Characters can only be farmed on specific days of the week, so this method
        sets the prompted characters to which day of the week their
        materials are available on.  */
        calculateSchedule();

        announceCalculatedSchedule();
    }


    public void promptForCharacters(){

        System.out.println("Hello! Welcome to the Genshin Impact Resource Management Optimizer! (GIRMO) ");
        System.out.println("What characters are you needing to build? (separate with spaces)");

        Collections.addAll(listOfCharacters, scanner.nextLine().toLowerCase().split(" "));
    }

    public void promptForBossDrops(){
        System.out.println("Would you like to collect weekly boss drops? (yes/no)");
        String yesNo = scanner.nextLine();

        if (yesNo.equalsIgnoreCase("yes")) {
            doWeeklyBoss = true;
        }
    }

    private void calculateSchedule() {

        /*
        creates a for each loop that goes through each character given earlier
        and adds the character to the given days of the week their Talent Books are available
         */

        Books currentBook;
        int currentDaysOfWeek;

        for (String characterName : listOfCharacters) {
            Character currentCharacter = characters.get(characterName);
            promptedCharacters.add(currentCharacter);
            currentBook = currentCharacter.getBook();
            currentDaysOfWeek = currentBook.getDaysOfWeek();
            if (currentDaysOfWeek == MONDAY_THURSDAY_SUNDAY) {
                daysOfWeekMap.get("Monday").add(currentCharacter);
                daysOfWeekMap.get("Thursday").add(currentCharacter);
            }
            if (currentDaysOfWeek == TUESDAY_FRIDAY_SUNDAY) {
                daysOfWeekMap.get("Tuesday").add(currentCharacter);
                daysOfWeekMap.get("Friday").add(currentCharacter);
            }
            if (currentDaysOfWeek == WEDNESDAY_SATURDAY_SUNDAY) {
                daysOfWeekMap.get("Wednesday").add(currentCharacter);
                daysOfWeekMap.get("Saturday").add(currentCharacter);
            }
            //everything is available on Sunday, so every character is added to the Sunday list
            daysOfWeekMap.get("Sunday").add(currentCharacter);
        }
    }

    private void initializeDaysOfWeek() {

        //each list will be filled with characters later to correspond
        //with what day of the week their Talent Books are available on

        daysOfWeekMap.put("Monday", new ArrayList<Character>());
        daysOfWeekMap.put("Tuesday", new ArrayList<Character>());
        daysOfWeekMap.put("Wednesday", new ArrayList<Character>());
        daysOfWeekMap.put("Thursday", new ArrayList<Character>());
        daysOfWeekMap.put("Friday", new ArrayList<Character>());
        daysOfWeekMap.put("Saturday", new ArrayList<Character>());
        daysOfWeekMap.put("Sunday", new ArrayList<Character>());
    }

    public void initializeCharacters() {

        final File characterFile = new File("ListOfCharacters.txt");
        try {
            Scanner fileReader = new Scanner(characterFile);
            while(fileReader.hasNextLine()){
                String lineOfText = fileReader.nextLine();
                String [] arr = lineOfText.split("\\|");
                characters.put(arr[0],new Character(arr[1],arr[2],books.get(arr[3])));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeTalentBooks() {

        final File bookFile = new File("ListOfBooks.txt");
        try {
            Scanner fileReader = new Scanner(bookFile);
            while(fileReader.hasNextLine()){
                String lineOfText = fileReader.nextLine();
                String[] arr;
                arr = lineOfText.split("\\|");
                books.put(arr[0], new Books(Integer.parseInt(arr[1]), arr[2], arr[3]));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void announceCalculatedSchedule() {
        System.out.println("Monday: ");
        if (doWeeklyBoss) {
            System.out.println("Farm weekly bosses");

        } else {
            announcement(daysOfWeekMap.get("Monday"), promptedCharacters);
        }

        for (int i = 1; i < daysOfWeekArray.length; i++) {
            System.out.println(daysOfWeekArray[i] + ":");
            announcement(daysOfWeekMap.get(daysOfWeekArray[i]), promptedCharacters);
        }
    }

    public void announcement(List<Character> day, List<Character> promptedCharacters) {
        if (day.size() > 0) {
            for (Character characterName : day) {
                System.out.println("You can farm " + characterName.getBook().getBookName() + " from the city of " + characterName.getBook().getRegion() + " for " + characterName.getCharacterName());
            }
        } else {
            for (Character characterName : promptedCharacters) {
                System.out.println("You can farm " + characterName.getBossName() + " for " + characterName.getCharacterName());
            }
        }
    }
}