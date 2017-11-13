import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class ItemCounter {
    Ride listRoot;

    void process(Ride newRide, String startTime, String endTime) throws ParseException{
        //Process the input ride object and convert the date strings into long values
        processDate(newRide, startTime, endTime);

        //Add the first ride into list or ride that ends before the first ride into list
        if(listRoot == null || newRide.endTime < listRoot.startTime){
            newRide.nextRide = listRoot;
            listRoot = newRide;
            return;
        }

        //Traverse the list till the newRide is not overlapping with ride objects in the list and
        // assign current and previous pointers
        Ride curr = listRoot, prev = new Ride();
        while(curr != null && curr.endTime < newRide.startTime){
            prev = curr;
            curr = curr.nextRide;
        }

        //If traversing the list reaches the end point, add newRide object at the end of list
        if(curr == null){
            prev.nextRide = newRide;
            return;
        }

        //Call helper function to merge newRide object with current ride in the list
        helper(newRide, curr, prev);
    }

    void processDate(Ride rideObj, String start, String end) throws ParseException{
        //To parse hour:minute value into date object
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        //Get start and end times from string and convert them to long values
        Date startTime = sdf.parse(start);
        Date endTime = sdf.parse(end);
        //Add long values of the input start and end times
        rideObj.startTime = startTime.getTime();
        rideObj.endTime = endTime.getTime();
    }

    void helper(Ride input, Ride curr, Ride prev){
        //Ride first = null, second, third;
        Ride temp = null;
        //Loop till either current reaches null or the input interval overlaps with current interval
        while(curr != null && input.endTime > curr.startTime){
            //If the start time of input interval and current interval is not the same, create new interval
            if(input.startTime != curr.startTime){
                temp = new Ride();
                //Create new interval from min of start times of current and input intervals upto max of
                // start times of current and input intervals, new interval: [min(startTimes), max(startTimes)]
                temp.startTime = Math.min(input.startTime, curr.startTime);
                temp.endTime = Math.max(input.startTime, curr.startTime);
                //Add items to new interval from input interval if input interval has smaller start time
                // or current interval if current interval has smaller start time
                temp.items = input.startTime < curr.startTime ?
                        new HashMap<>(input.items) :
                        new HashMap<>(curr.items);
                //If the list had only one ride object and that was conflicting with input object,
                // add the new element as the new first ride object into the list
                if(curr == listRoot){
                    listRoot = temp;
                }
                //Set next pointer of previous pointer and move it ahead
                prev.nextRide = temp;
                prev = temp;
            }

            //Create new interval which merges 2 overlapping intervals, input and current
            temp = new Ride();
            //Create new interval from max of start times of current and input intervals upto max of
            // end times of current and input intervals, new interval: [max(startTimes), min(endTimes)]
            temp.startTime = Math.max(input.startTime, curr.startTime);
            temp.endTime = Math.min(input.endTime, curr.endTime);
            //Add interval into list only if its start and end times are not the same
            // (avoiding adding intervals like, [7:00-7:00])
            if(temp.startTime != temp.endTime){
                //Merge item lists from input and current intervals into newly created interval temp
                mergeItemLists(input, curr, temp);
                //Set next pointer of previous pointer and move it ahead
                prev.nextRide = temp;
                prev = temp;
            }

            //Create new interval from min of end times of current and input intervals upto max of
            // end times of current and input intervals, new interval: [min(endTimes), max(endTimes)]
            if(input.endTime != curr.endTime){
                temp = new Ride();
                temp.startTime = Math.min(input.endTime, curr.endTime);
                temp.endTime = Math.max(input.endTime, curr.endTime);
                //Add items to new interval from input interval if input interval has smaller end time
                // or current interval if current interval has smaller end time
                temp.items = input.endTime < curr.endTime ?
                        new HashMap<>(curr.items) :
                        new HashMap<>(input.items);
            }

            //Newly created interval will be new input now for checking whether it overlaps with the next interval
            input = temp;
            //Next interval will be the new current interval in next iteration
            curr = curr.nextRide;
            //Set next pointer from new interval to the next interval
            temp.nextRide = curr;

            //Add new interval into the list only if the third interval was created as endTimes were not the same and
            // it will be the last element in the list or
            // when it doesn't overlap with the next interval
            if (prev != temp && (curr == null || temp.endTime < curr.startTime)) {
                prev.nextRide = temp;
            }
        }

    }

    //Function to merge item lists from 2 rides(input and current) into merged ride object when they overlap
    void mergeItemLists(Ride input, Ride curr, Ride merged){
        //Take items from current ride as is
        merged.items = new HashMap<>(curr.items);
        Set<String> keySet = input.items.keySet();
        //Traverse over the keys from map of input ride object
        for(String key : keySet){
            //If the key is already there into merged ride, add values from both rides
            if(merged.items.containsKey(key)){
                merged.items.put(key, merged.items.get(key) + input.items.get(key));
            }
            //Create new entry into merged ride items list if not already there
            else{
               merged.items.put(key, input.items.get(key));
            }
        }
    }

    //Function to print items per interval
    void printItemsPerInterval(){
        //Iterate over the list starting at listRoot
        Ride iter = listRoot;
        while(iter != null){
            DateFormat formatter = new SimpleDateFormat("hh:mm");
            Date time1 = new Date(iter.startTime);
            Date time2 = new Date(iter.endTime);
            //Parse long values to produce readable time value in hh:mm format
            System.out.print(formatter.format(time1) + " - " + formatter.format(time2) + " -> ");
            Set<String> keys = iter.items.keySet();
            StringBuilder str = new StringBuilder();
            for(String key : keys){
                if(iter.items.get(key) > 1){
                    str.append(iter.items.get(key) + " " + key + "s, ");
                }
                else{
                    str.append(iter.items.get(key) + " " + key + ", ");
                }
            }
            System.out.println(str);
            iter = iter.nextRide;
        }
    }
}
