package com.pozwizd.prominadaadmin.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    public static String toFormatDateFromDB(LocalDate date, String outputFormat) {
        DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter output = DateTimeFormatter.ofPattern(outputFormat);
        LocalDate localDate = LocalDate.parse(date.toString(), input);
        return localDate.format(output);
    }

    public static LocalDate toFormatDateToDB(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
    }
}
