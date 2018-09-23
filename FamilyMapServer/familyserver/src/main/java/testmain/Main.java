package testmain;

import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        Time time = new Time(12, 21);
        Time time2 = new Time(1, 30);
        Time time3 = new Time(11, 20);
        Time time4 = new Time(12, 20);
        Time time5 = new Time(12, 21);
        Time time6 = new Time(12, 22);
        Time time7 = new Time(0, 30);
        time7.setMinutes(60);

        System.out.println(time2.compareTo(time));
        System.out.println(time3.compareTo(time));
        System.out.println(time4.compareTo(time));
        System.out.println(time5.compareTo(time));
        System.out.println(time6.compareTo(time));

        System.out.println(gson.toJson(time));
    }

}
