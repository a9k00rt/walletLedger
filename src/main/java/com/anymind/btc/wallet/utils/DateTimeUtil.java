package com.anymind.btc.wallet.utils;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter parser = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXXXX");

    public static long getUnixTimeEpoc(String dateTime) {
        return OffsetDateTime.parse(dateTime, parser).withOffsetSameInstant(ZoneOffset.UTC).toEpochSecond();
    }

    public static String getUTCFromUnixTimeEpoc(long epoch) {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneOffset.UTC).toZonedDateTime().toString();
    }


}
