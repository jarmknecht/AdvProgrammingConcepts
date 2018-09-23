package testmain;
import java.lang.IllegalArgumentException;


public class Time implements Comparable<Time>  {
    private int hours;
    private int minutes;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        try {
            if(hours>12||hours< 1) {
                throw new IllegalArgumentException();
            }
            this.hours=hours;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Hour wrong");
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        try {
            if(minutes>59||minutes< 0) {
                throw new IllegalArgumentException();
            }
            this.minutes = minutes;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Min wrong");
        }

    }

    public Time(int hours, int minutes) {
        try {
            if(hours>12||hours< 1) {
                throw new IllegalArgumentException();
            }
            this.hours=hours;

            if(minutes>59||minutes< 0) {
                throw new IllegalArgumentException();
            }
            this.minutes = minutes;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Hours or min wrong");
        }
    }

    @Override
    public String toString() {
        return "Hour: " + getHours() + " Minutes: " + getMinutes() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Time other = (Time) o;

        Integer xhours = this.getHours();
        Integer yhours = other.getHours();
        if (!xhours.equals(yhours)) {
            return false;
        }
        Integer xmin = this.getMinutes();
        Integer ymin = other.getMinutes();

        if (!xmin.equals(ymin)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int primeNum = 17;
        return (this.getHours() + this.getMinutes()) * primeNum;
    }

    @Override
    public int compareTo(Time other) {
        if (this.getHours() < other.getHours()) {
            return -1;
        }
        else if (this.getHours() > other.getHours()) {
            return 1;
        }
        else if (this.getHours() == other.getHours()) {
            if (this.getMinutes() < other.getMinutes()) {
                return -1;
            }
            else if (this.getMinutes() > other.getMinutes()) {
                return 1;
            }
        }
        return 0;
    }
}
