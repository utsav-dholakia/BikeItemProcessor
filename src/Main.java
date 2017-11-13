import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws ParseException{

        //Create ride objects

        //To parse hour:minute value into date object
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        HashMap<String, Integer> items = new HashMap<>();
        Date startTime = null;
        Date endTime = null;

        String time1 = "7:00";
        String time2 = "7:30";
        startTime = sdf.parse(time1);
        endTime = sdf.parse(time2);
        items.put("apple", 2);
        items.put("brownie", 1);
        Ride ride1 = new Ride(startTime.getTime(), endTime.getTime(), items);
        ride1.start = time1;
        ride1.end = time2;

        time1 = "6:30";
        time2 = "6:59";
        items = new HashMap<>();

        startTime = sdf.parse(time1);
        endTime = sdf.parse(time2);
        items.put("apple", 1);
        items.put("carrot", 3);
        Ride ride2 = new Ride(startTime.getTime(), endTime.getTime(), items);
        ride2.start = time1;
        ride2.end = time2;


        time1 = "7:50";
        time2 = "8:45";
        items = new HashMap<>();

        startTime = sdf.parse(time1);
        endTime = sdf.parse(time2);
        items.put("apple", 1);
        items.put("brownie", 2);
        items.put("diamond", 4);
        Ride ride3 = new Ride(startTime.getTime(), endTime.getTime(), items);
        ride3.start = time1;
        ride3.end = time2;


        ItemCounter counter = new ItemCounter();
        counter.process(ride1);
        counter.process(ride2);
        counter.process(ride3);

        counter.printItemsPerInterval();
    }
}
