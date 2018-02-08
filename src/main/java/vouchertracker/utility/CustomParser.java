package vouchertracker.utility;

import java.time.LocalDateTime;

public class CustomParser {

    public static Double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim().replace(",", "."));
        } catch (NumberFormatException e) {
        }

        return null;
    }

    public static Long parseCurrency(String s) {
        try {
            return Math.round(100 * parseDouble(s));
        } catch (Exception e) {
        }

        return null;
    }

    public static String parseCurrency(Long l) {
        String s = "" + (1.0 * l / 100);

        return (s.substring(s.indexOf(".")).length() == 2
                ? s += "0"
                : s);
    }

    public static String parseTimestamp(LocalDateTime d) {
        int n;

        n = d.getMonthValue();
        String mo = n < 10 ? "0" + n : "" + n;
        n = d.getDayOfMonth();
        String da = n < 10 ? "0" + n : "" + n;
        n = d.getHour();
        String ho = n < 10 ? "0" + n : "" + n;
        n = d.getMinute();
        String mi = n < 10 ? "0" + n : "" + n;
        n = d.getSecond();
        String se = n < 10 ? "0" + n : "" + n;

        return d.getYear() + "-" + mo + "-" + da + " " + ho + ":" + mi + ":" + se;
    }

}