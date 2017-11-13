import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws ParseException{

        //Create ItemCounter object
        ItemCounter itemCounter= new ItemCounter();

        //Create ride objects
        HashMap<String, Integer> items = new HashMap<>();
        items.put("apple", 2);
        items.put("brownie", 1);
        //Create ride object with given items
        Ride ride1 = new Ride(items);
        //Add ride object to be processed and added to the list in ItemCounter class
        String startTime = "7:00";
        String endTime = "7:30";
        itemCounter.process(ride1, startTime, endTime);

        items = new HashMap<>();
        items.put("apple", 1);
        items.put("carrot", 3);
        Ride ride2 = new Ride(items);
        //Add ride object to be processed and added to the list in ItemCounter class
        startTime = "7:10";
        endTime = "8:00";
        //itemCounter.process(ride2, startTime, endTime);

        items = new HashMap<>();
        items.put("apple", 1);
        items.put("brownie", 2);
        items.put("diamond", 4);
        Ride ride3 = new Ride(items);
        //Add ride object to be processed and added to the list in ItemCounter class
        startTime = "7:20";
        endTime = "7:45";
        itemCounter.process(ride3, startTime, endTime);

        //Print the resultant list of items per intervals
        itemCounter.printItemsPerInterval();
    }
}
