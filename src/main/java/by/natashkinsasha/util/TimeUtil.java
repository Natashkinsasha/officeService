package by.natashkinsasha.util;


import java.time.*;
import java.time.temporal.ChronoField;

public class TimeUtil {


    public static Long toSecond(LocalTime localTime){
        return localTime.getLong(ChronoField.SECOND_OF_DAY);
    }


    public static LocalTime toLocalTime(Long second){
        return LocalTime.ofSecondOfDay(second);
    }

    public static LocalDateTime toLocalDateTime(Long unixTime){
        return toLocalDateTime(unixTime, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(Long unixTime, ZoneId zoneId){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), zoneId);
    }


    public static LocalDate toLocalDate(Long unixTime, ZoneId zoneId){
        return Instant.ofEpochSecond(unixTime).atZone(zoneId).toLocalDate();
    }

    public static LocalDate toLocalDate(Long unixTime){
        return toLocalDate(unixTime, ZoneId.systemDefault());
    }

    public static Long toUnixTime(Instant instant){
        return instant.getEpochSecond();
    }


    public static Long toUnixTime(LocalDate localDate, ZoneOffset zoneOffset){
        return toUnixTime(localDate.atStartOfDay().toInstant(zoneOffset));
    }

    public static Long toUnixTime(LocalDate localDate){
        return toUnixTime(localDate, ZoneOffset.MIN);
    }

    public static boolean isOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }


}
