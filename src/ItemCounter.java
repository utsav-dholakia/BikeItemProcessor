import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ItemCounter {
    Ride listRoot;

    void process(Ride newRide){

        if(listRoot == null || newRide.endTime < listRoot.startTime){
            newRide.nextRide = listRoot;
            listRoot = newRide;
            return;
        }
        Ride curr = listRoot, prev = new Ride();
        while(curr != null && curr.endTime < newRide.startTime){
            prev = curr;
            curr = curr.nextRide;
        }

        if(curr == null){
            prev.nextRide = newRide;
            return;
        }

        helper(newRide, curr, prev);
    }

    void helper(Ride input, Ride curr, Ride prev){

        //Ride first = null, second, third;
        Ride temp = null;

        while(curr != null && input.endTime > curr.startTime){
            if(input.startTime != curr.startTime){
                temp = new Ride();
                temp.startTime = Math.min(input.startTime, curr.startTime);
                temp.endTime = Math.max(input.startTime, curr.startTime);
                temp.start = input.startTime < curr.startTime ? input.start : curr.start;
                temp.end = input.startTime < curr.startTime ? curr.start : input.start;

                temp.items = input.startTime < curr.startTime ?
                        new HashMap<>(input.items)
                        : new HashMap<>(curr.items);
                if(curr == listRoot){
                    listRoot = temp;
                }
                prev.nextRide = temp;
                prev = temp;
            }
            temp = new Ride();
            temp.startTime = Math.max(input.startTime, curr.startTime);
            temp.endTime = Math.min(input.endTime, curr.endTime);
            temp.start = input.startTime < curr.startTime ? curr.start : input.start;
            temp.end = input.endTime < curr.endTime ? input.end : curr.end;

            mergeItemLists(input, curr, temp);
            prev.nextRide = temp;
            prev = temp;

            if(input.endTime != curr.endTime){
                temp = new Ride();
                temp.startTime = Math.min(input.endTime, curr.endTime);
                temp.endTime = Math.max(input.endTime, curr.endTime);
                temp.start = input.endTime < curr.endTime ? input.end : curr.end;
                temp.end = input.endTime < curr.endTime ? curr.end : input.end;
                temp.items = input.endTime < curr.endTime ?
                        new HashMap<>(curr.items) :
                        new HashMap<>(input.items);
            }

            input = temp;
            curr = curr.nextRide;
            temp.nextRide = curr;

            if (prev != temp && (curr == null || temp.endTime < curr.startTime)) {
                prev.nextRide = temp;
            }
        }

    }

    void mergeItemLists(Ride input, Ride curr, Ride merged){
        merged.items = new HashMap<>(curr.items);
        Set<String> keySet = input.items.keySet();
        for(String key : keySet){
            if(merged.items.containsKey(key)){
                merged.items.put(key, merged.items.get(key) + input.items.get(key));
            }
            else{
               merged.items.put(key, input.items.get(key));
            }
        }
    }

    void printItemsPerInterval(){
        Ride iter = listRoot;
        while(iter != null){
            System.out.print(iter.start + " - " + iter.end + " -> ");
            Set<String> keys = iter.items.keySet();
            for(String key : keys){
                if(iter.items.get(key) > 1){
                    System.out.print(iter.items.get(key) + " " + key + "s, ");
                }
                else{
                    System.out.print(iter.items.get(key) + " " + key + ", ");
                }
            }
            System.out.println();
            iter = iter.nextRide;
        }
    }
}
