import java.util.HashMap;

public class Ride {
    //String start, end;
    //Start and End time of the ride
    long startTime, endTime;
    //Items carried during the ride <Item Name, Number of items>
    HashMap<String, Integer>  items;

    //This pointer is used to process and print the rides
    Ride nextRide;

    Ride(){}

    Ride(HashMap<String, Integer> items){
        this.items = items;
    }
}
