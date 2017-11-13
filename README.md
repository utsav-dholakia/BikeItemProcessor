# Bike Item Processor

This project is for counting at what intervals bikes are used to carry items in their basket. The project takes as an input various ride objects that have a map of items and count of those items in it along with the ride start and end time. 

## Itemcounter class

This class is used for processing and printing items per interval as output. 

#### Process method: 

This method takes as input a newRide object and start and end times of the ride in String with format "hh:mm". Then it starts arranging the list of ride objects (referenced by listRoot object) in a sorted blocks. While adding any newRide object, below cases are possible:

* list is empty or the ride object being added doesn't overlap with any objects and has end time such that it ends before the first ride in the list, so, the newRide object will be added at the first position in the list.
* Traverse over the list and find the position where the newRide starts before any of the existing ride ends, thus they will have overlap in them. If, while traversing, we reach the end of list, add the new ride object at the end of list.
* If we didn't reach the end of list, we have to add the newRide object such that, the overlapping ride object (current) and newRide object are split into separate intervals and are stored in the list along with appropriate item maps. This task is done by helper method.

#### Helper method: 

This method takes as input newRide, current ride and previous ride objects, where previous ride object is the ride object that is one before the current ride object in the list. If we split the overlapping intervals, they create up to 3 separate intervals at one time. 

Now the third interval may or may not overlap with the next interval (current -> nextRide in the list): If it doesn't, we just add the newly created intervals into the list. But if it does, we take the third interval as input, next (current -> nextRide) as current, and second / first (whichever was created last) as previous to do the same process again, as explained below.

The loop will run till either current doesn't reach the end of list or the input ends after the current starts. 

* First we will check if start time of current and newRide is not same, then we create a new interval which starts at the minimum of the start times of current and newRide and ends at maximum of the start times of current and newRide. Also, it will have items from only the one which started earlier.
* Then, we'll try to create a new interval that is actually the overlap between current and newRide intervals and merges items from both of them only if it doesn't have the same start and end time values. 
* Then, we will create new interval only if the end times of current and newRide intervals are not the same, and it will contain items from only the one which ends last.
* Then we'll call the interval which was created last as the new input (newRide object), current->nextRide as new current ride object and create a link between last created interval and next interval.
* We'll add the last created ride object into the list of rides only if we did create third interval and, it doesn't already have overlap with the next interval or there's no next ride interval(we reached the end of list).



## __ASSUMPTIONS__
* The input is done through creating Ride class objects from main method and adding start and end times as a string in "hh:mm" format and, a map of items and their count into it.


## __STEPS TO RUN__
* Copy the directoy into your machine and go to the root directory "BikeItemProcessor".
* Create an empty directory "out" into the src directory.
* From terminal give command javac -d src/out/ src/*.java
* Then give command java -cp src/out/ Main


## __Corner cases__
* Input: ride1: [7:00 - 7:30], ride2:[7:30 - 8:00], then the output is: 
[7:00 - 7:30] -> items of ride1,
[7:30 - 8:00] -> items of ride2,
* Input: ride1: [7:00 - 7:30], ride2:[7:10 - 7:30], then the output is:
[7:00 - 7:10] -> items of ride1,
[7:10 - 7:30] -> items of ride1 + ride2,
* Input: ride1: [7:00 - 7:30], ride2:[7:00 - 7:20], then the output is:
[7:00 - 7:20] -> items of ride1 + ride2,
[7:20 - 7:30] -> items of ride1,
