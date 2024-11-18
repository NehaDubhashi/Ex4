import java.util.List;

public class RangeTree{
    private OrderedDeletelessDictionary<Double, Range> byStart;
    private OrderedDeletelessDictionary<Double, Range> byEnd;
    private int size;

    public RangeTree(){
        byStart = new AVLTree<>();
        byEnd = new AVLTree<>();
        size = 0;
    }
    
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // Return the Range which starts at the given time
    // The running time is O(log n)
    public Range findByStart(Double start){
        return byStart.find(start);
    }

    // Return the Range which ends at the given time
    // The running time is O(log n)
    public Range findByEnd(Double end){
        return byEnd.find(end);
    }

    // Gives a list of Ranges sorted by start time.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Range> getRanges(){
        return byStart.getValues();
    }

    // Gives a sorted list of start times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getStartTimes(){
        return byStart.getKeys();
    }

    // Gives a sorted list of end times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getEndTimes(){
        return byEnd.getKeys();
    }

    // Identifies whether or not the given range conflicts with any
    // ranges that are already in the data structure.
    // If the data structure is empty, then it does not conflict
    // with any ranges, so we should return false.
    // The running time of this method should be O(log n)
   
    public boolean hasConflict(Range query){
        if (byEnd.isEmpty() && byStart.isEmpty()) {
            return false;
        } 
        // Find closest start point that is less than or equal to query.end
        Double startBeforeQueryEnd = byStart.findPrevKey(query.end);
        // Find closest end point that is greater than or equal to query.start
        Double endAfterQueryStart = byEnd.findNextKey(query.start);

    
        // Case 1 overlap: A range starts before query's end & ends after query's start
        if (startBeforeQueryEnd != null && startBeforeQueryEnd < query.end) {
            // Check if any range with this start could overlap query
            Double correspondingEnd = byEnd.findNextKey(startBeforeQueryEnd);
            if (correspondingEnd != null && correspondingEnd > query.start) {
                return true; 
            }
        }

        // Case 2 overlap: An event ends after query's start & starts before query's end
        if (endAfterQueryStart != null && endAfterQueryStart > query.start) {
            // Check if any range with this end could overlap query
            Double correspondingStart = byStart.findPrevKey(endAfterQueryStart);
            if (correspondingStart != null && correspondingStart < query.end) {
                return true;
            }
        }

        return false; // No conflict found
}

    // Inserts the given range into the data structure if it has no conflict.
    // Does not modify the data structure if it does have a conflict.
    // Return value indicates whether or not the item was successfully
    // added to the data structure.
    // Running time should be O(log n)
    public boolean insert(Range query){
        if (hasConflict(query)) {
            return false;
        }
        byStart.insert(query.start, query);
        byEnd.insert(query.end, query); 
        size++;
        return true;
    }
}
