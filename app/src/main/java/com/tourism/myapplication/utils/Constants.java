package com.tourism.myapplication.utils;

public abstract class Constants {


    public enum HotelType {
        CAMPING,
        PARAGLIDING,
        TREKKING,
        HOLY_PLACES,
        FAIRS_AND_FESTIVALS,
        HIMACHAL_FOOD
    }

    public static boolean isValidCardNumber(String cardNumber) {
        // Clean up the card number by removing any non-digit characters
        String cleanCardNumber = cardNumber.replaceAll("\\D", "");

        // Convert the card number to an integer array
        int[] digits = new int[cleanCardNumber.length()];
        for (int i = 0; i < cleanCardNumber.length(); i++) {
            digits[i] = Character.getNumericValue(cleanCardNumber.charAt(i));
        }

        // Double every second digit starting from the right
        for (int i = digits.length - 2; i >= 0; i -= 2) {
            int doubledDigit = digits[i] * 2;

            // If the doubled digit is greater than 9, subtract 9 from it
            if (doubledDigit > 9) {
                doubledDigit -= 9;
            }

            digits[i] = doubledDigit;
        }

        // Sum up all the digits
        int sum = 0;
        for (int digit : digits) {
            sum += digit;
        }

        // The card number is valid if the sum is divisible by 10
        return sum % 10 == 0;
    }

    public static boolean isValidUpiNumber(String upiNumber) {
        String upiPattern = "^\\w+@(\\w+\\.?)+$";

        return upiNumber.matches(upiPattern);
    }
}
