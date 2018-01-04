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

}