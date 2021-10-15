
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



public class Convert {
	
	
	
	public static Map<String, Integer> lowestNotes(){
		
	    Map<String, Integer> x = new HashMap<String, Integer>();
	    for(int i = 0, j = 24; i < 14; i++, j++) {
	    	x.put(Note.chromatic[i], j);
	    } return x;
    }	
	
	public static int random(int min, int max) { // generates random int in range of min to max-1
	    return (int) ((Math.random() * (max - min)) + min);
	}

	public static int[] midiNums(String note){ // return int[] of 7 octaves of MIDI#s for a given note
		int[] mN = new int[7];
		for(int i = lowestNotes().get(note), j = 0; j <=6; i+=12, j++) {
			mN[j] = i; 
		} 
		   return mN;
		
	   
    
	}
	
	public static <T> List<T> flatten( // flattens nested lists
		    
			List<List<T>> nestedList) {
		    List<T> ls = new ArrayList<>();
		    nestedList.forEach(ls::addAll);
		    return ls;
		}
	
	public static Map<String, int[]> allMidiNums(){
		Map<String, int[]> all = new HashMap<String, int[]>();
		
		for(int i = 0; i < 12; i++) {
			all.put(Scale.chromatic[i], midiNums(Scale.chromatic[i]));
		}
		
		return all;
			
		
	}
	public static List<int[]> toNums(List<String> scale){ //returns int[] of each midi numeric value for each octave in given scale.
		
		List<int[]> nums = new ArrayList<int[]>();
		
		for(int i = 0; i < scale.size(); i++) {
			nums.add(midiNums(scale.get(i)));
		
		} return nums;
	}
	
	
	public static List<Integer> merge(ArrayList<String> chord){ // can also be a scale
			
			ArrayList<Integer> lst = new ArrayList<Integer>();
			
			for(String note : chord) {
				for(int x : Convert.midiNums(note)) {
					lst.add(x);
				}
			} //Collections.sort(lst);
			  return lst.stream()
					    .distinct()
					    .sorted()
					    .collect(Collectors.toList());
			  
					    
	}
	
	public static void randomTester() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for(int i = 0; i < 1000000; i++) {
				int x = random(1,5);
				AtomicInteger y = new AtomicInteger(map.containsKey(x) ? map.get(x) : 0);
		        map.put(x, y.incrementAndGet());
			
			} System.out.println(map);
		
	}
		
		
		
		
	 
}
