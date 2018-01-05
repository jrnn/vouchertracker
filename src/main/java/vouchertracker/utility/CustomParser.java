package vouchertracker.utility;

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

}