package org.example;

public class Books {
    private int daysOfWeek;
    private String region;
    private String bookName;

    private final int MONDAY_THURSDAY_SUNDAY = 1;
    private final int TUESDAY_FRIDAY_SUNDAY = 2;
    private final int WEDNESDAY_SATURDAY_SUNDAY = 3;
    public Books(int daysOfWeek, String region, String bookName) {
        this.daysOfWeek = daysOfWeek;
        this.region = region;
        this.bookName = bookName;

    }

    public int getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getRegion() {
        return region;
    }

    public String getBookName() {
        return bookName;
    }
}
