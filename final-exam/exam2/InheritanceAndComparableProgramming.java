import java.util.ArrayList;
import java.util.List;

public class InheritanceAndComparableProgramming {

    public class Date {
        int month;
        int day;
        List<Integer> thirtyOneDays = new ArrayList<>(List.of(1, 3, 5, 7, 8, 10, 12));
        List<Integer> thirtyDays = new ArrayList<>(List.of(4, 6, 9, 11));

        public Date(int month, int day) {
            this.month = month;
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int daysInMonth() {
            if (this.month == 2) return 28;
            if (thirtyDays.contains(this.month)) return 30;
            return 31;
        }

        public String toString() {
            String sub = (this.month < 10) ? "0" : "";
            return sub + this.month + "/" + this.day;
        }

        public void nextDay() {
            if (this.month == 2 && this.day == 28) {
                this.day = 1;
                this.month = 3;
            } else if (thirtyDays.contains(this.month) && this.day == 30) {
                this.day = 1;
                this.month++;
            } else if (thirtyOneDays.contains(this.month) && this.day == 31) {
                this.day = 1;
                if (this.month == 12) {
                    this.month = 1;
                } else {
                    this.month++;
                }
            }
            this.day++;
        }
    }


    public class CanlendarDate extends Date implements Comparable<CanlendarDate> {
        int year;

        public CanlendarDate(int year, int month, int day) {
            super(month, day);
            this.year = year;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public void nextDay() {
            if (this.month == 12 && this.day == 31) {
                this.year++;
            }
            super.nextDay();
        }

        @Override
        public String toString() {
            return this.year + super.toString();
        }

        @Override
        public int compareTo(CanlendarDate o) {
            if (this.year != o.getYear()) {
                return this.year - o.getYear();
            } else if (this.month != o.getMonth()) {
                return this.month - o.getMonth();
            } else {
                return this.day - o.getDay();
            }
        }
    }
}
