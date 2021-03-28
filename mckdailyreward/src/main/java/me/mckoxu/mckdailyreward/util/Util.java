package me.mckoxu.mckdailyreward.util;

public class Util {
    public static String convertTime(int input) {
        int numberOfDays;
        int numberOfHours;
        int numberOfMinutes;
        int numberOfSeconds;

        numberOfDays = input / 86400;
        numberOfHours = (input % 86400) / 3600;
        numberOfMinutes = ((input % 86400) % 3600) / 60;
        numberOfSeconds = ((input % 86400) % 3600) % 60;

        String output = "";
        if (numberOfDays > 0) output = numberOfDays + "d. ";
        if (numberOfHours > 0) output = output + numberOfHours + "h. ";
        if (numberOfMinutes > 0) output = output + numberOfMinutes + "m. ";
        output = output + numberOfSeconds + "s ";

        return output;
    }
}
