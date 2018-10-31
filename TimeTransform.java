public class TimeTransform {
    private static int DAYS = 24*3600;

    private static int norMoth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int leapMoth[] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int year, month, day, hour, minute, second;
    private long stampTime;
    private boolean isLeap(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) return true;
        else return false;
    }
    public TimeTransform(String newDate){
        String[] daysAndTimes = newDate.split("-|\\:");
        year = Integer.parseInt(daysAndTimes[0]);
        month = Integer.parseInt(daysAndTimes[1]);
        day = Integer.parseInt(daysAndTimes[2]);
        hour = Integer.parseInt(daysAndTimes[3]);
        minute = Integer.parseInt(daysAndTimes[4]);
        second = Integer.parseInt(daysAndTimes[5]);

    }
    public TimeTransform(long newStampTime) {
        stampTime = newStampTime;
    }
    public long dateToStamptime() {
        int getStampTime;
        int countDays = 0;
        int countLeapYear = 0;
        int countYear = year - 1970;
        for (int i = 1970; i < year; i++) {
            if (isLeap(i)) countLeapYear++;
        }
        countDays = countYear * 365 + countLeapYear;
        if (isLeap(year)) {
            for (int i = 0; i < month - 1; i++) {
                countDays += leapMoth[i];
            }
        } else {
            for (int i = 0; i < month - 1; i++) {
                countDays += norMoth[i];
            }
        }
        countDays += day;
        getStampTime = (countDays - 1) * DAYS  + hour * 60 * 60 + minute * 60 + second - 8 * 60 * 60;
        return getStampTime;
    }
    public String stampTimeToDate() {
        int countDays = Integer.parseInt(String.valueOf(stampTime / DAYS));
        int countTimes = Integer.parseInt(String.valueOf(stampTime % DAYS));
        int countLeapYear = 0;
        int months = 1;
        int days = 1;
        int years = 1970;
        int hours = countTimes / 3600;
        int minutes = (countTimes - hours * 3600) / 60;
        int seconds = countTimes % 60;
        String date;

        hours += 8;//北京时间
        if (hours >= 24) {
            hours -= 24;
            days++;
        }
        years += countDays / 365;//大体计算年数
        for (int i = 1970; i < years; i++) {
            if (isLeap(i)) {
                countLeapYear++;
            }
        }
        countDays -= 365 * (years - 1970) + countLeapYear;//将剩余天数减去已计入年数的天数
        if (countDays <= 0) {
            years--;
            if (isLeap(years)) {
                countDays += 366;
            } else {
                countDays += 365;
            }
        }
        if (isLeap(years)) {
            for (int i = 0; countDays > leapMoth[i]; i++) {
                countDays -= leapMoth[i];
                months++;
            }
        } else {
            for (int i = 0; countDays > norMoth[i]; i++) {
                countDays -= norMoth[i];
                months++;
            }
        }
        days += countDays;
        if (isLeap(years)) {
            if (days > leapMoth[months - 1]) {
                days -= leapMoth[months - 1];
                months++;
                if (months > 12) {
                    months -= 12;
                    years++;
                }
            }
        } else {
            if (days > norMoth[months - 1]) {
                days -= norMoth[months - 1];
                months++;
                if (months > 12) {
                    months -= 12;
                    years++;
                }
            }
        }
        date = String.format("%d-%02d-%02d %02d:%02d:%02d", years, months, days, hours, minutes, seconds);
        return date;
    }

}
