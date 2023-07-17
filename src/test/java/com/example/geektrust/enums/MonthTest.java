package com.example.geektrust.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonthTest {

    @Test
    void getMonthNumber_shouldReturnCorrectMonthNumber() {
        assertEquals(1, Month.JANUARY.getMonthNumber());
        assertEquals(2, Month.FEBRUARY.getMonthNumber());
        assertEquals(3, Month.MARCH.getMonthNumber());
        assertEquals(4, Month.APRIL.getMonthNumber());
        assertEquals(5, Month.MAY.getMonthNumber());
        assertEquals(6, Month.JUNE.getMonthNumber());
        assertEquals(7, Month.JULY.getMonthNumber());
        assertEquals(8, Month.AUGUST.getMonthNumber());
        assertEquals(9, Month.SEPTEMBER.getMonthNumber());
        assertEquals(10, Month.OCTOBER.getMonthNumber());
        assertEquals(11, Month.NOVEMBER.getMonthNumber());
        assertEquals(12, Month.DECEMBER.getMonthNumber());
    }

    @Test
    void values_shouldReturnAllMonths() {
        Month[] months = Month.values();
        assertEquals(12, months.length);
        assertEquals(Month.JANUARY, months[0]);
        assertEquals(Month.FEBRUARY, months[1]);
        assertEquals(Month.MARCH, months[2]);
        assertEquals(Month.APRIL, months[3]);
        assertEquals(Month.MAY, months[4]);
        assertEquals(Month.JUNE, months[5]);
        assertEquals(Month.JULY, months[6]);
        assertEquals(Month.AUGUST, months[7]);
        assertEquals(Month.SEPTEMBER, months[8]);
        assertEquals(Month.OCTOBER, months[9]);
        assertEquals(Month.NOVEMBER, months[10]);
        assertEquals(Month.DECEMBER, months[11]);
    }

    @Test
    void valueOf_shouldReturnCorrectMonth() {
        assertEquals(Month.JANUARY, Month.valueOf("JANUARY"));
        assertEquals(Month.FEBRUARY, Month.valueOf("FEBRUARY"));
        assertEquals(Month.MARCH, Month.valueOf("MARCH"));
        assertEquals(Month.APRIL, Month.valueOf("APRIL"));
        assertEquals(Month.MAY, Month.valueOf("MAY"));
        assertEquals(Month.JUNE, Month.valueOf("JUNE"));
        assertEquals(Month.JULY, Month.valueOf("JULY"));
        assertEquals(Month.AUGUST, Month.valueOf("AUGUST"));
        assertEquals(Month.SEPTEMBER, Month.valueOf("SEPTEMBER"));
        assertEquals(Month.OCTOBER, Month.valueOf("OCTOBER"));
        assertEquals(Month.NOVEMBER, Month.valueOf("NOVEMBER"));
        assertEquals(Month.DECEMBER, Month.valueOf("DECEMBER"));
    }

}