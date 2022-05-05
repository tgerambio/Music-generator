package musicGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class Modulation {
	
	public static ArrayList<ArrayList<String>> circleOfFifths(String note){
		
		ArrayList<ArrayList<String>> circle = new ArrayList<ArrayList<String>>();
		ArrayList<String> majMinDim = new ArrayList<String>();
		
		majMinDim.add(note);
		majMinDim.add(Scale.major(note).get(5));
		majMinDim.add(Scale.major(note).get(6));
		circle.add(new ArrayList<String>(majMinDim));
		
		for(int i = 1; i<12; i++) {
			
			majMinDim.clear();
			majMinDim.add(Scale.major(circle.get(i-1).get(0)).get(4));
			majMinDim.add(Scale.minor(circle.get(i-1).get(1)).get(4));
			majMinDim.add(Scale.major(majMinDim.get(0)).get(6));
			
			circle.add(new ArrayList<String>(majMinDim));
		}
	        return circle;
		
		
	}
	public static ArrayList<ArrayList<ArrayList<String>>> circ5Scales(String note){
		
		ArrayList<ArrayList<ArrayList<String>>> list = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> scales = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> entry : circleOfFifths(note)) {
			scales.clear();
			
			
			scales.add(new ArrayList<String>(Scale.major(entry.get(0))));
			scales.add(new ArrayList<String>(Scale.minor(entry.get(1))));
			list.add(new ArrayList<ArrayList<String>>(scales));
			
			
		}
		return list;
		
	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> circ5Chords(String note){ //returns circle of fifthList((major)(minor)(diminished))
		
		ArrayList<ArrayList<ArrayList<String>>> cir = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> slice = new ArrayList<ArrayList<String>>();
		List<String> chord = new ArrayList<String>();
		
		for(List<String> roots : circleOfFifths(note)) {
			
			for(int i = 0; i < roots.size(); i++) {
					chord = i==0? Chord.major(roots.get(i)) : i==1? Chord.minor(roots.get(i)) : Chord.dim(roots.get(i));
					
					slice.add(new ArrayList<String>(chord));
					
				
				
			} cir.add(new ArrayList<ArrayList<String>>(slice));
			slice.clear();
		} 
		return cir;
		
	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> progList(ArrayList<String> scale){
		
		ArrayList<ArrayList<ArrayList<String>>> list = new ArrayList<ArrayList<ArrayList<String>>>();
       
		for(int i = 0; i < 5; i++) {
        		list.add(new ArrayList<ArrayList<String>>());
       		 }
		
		list.get(0).add(Chord.major(scale.get(0)));
		list.get(0).add(Chord.minor(scale.get(5)));
		list.get(1).add(Chord.dom7(scale.get(4)));
        	list.get(1).add(Chord.dim(scale.get(6)));
        	list.get(2).add(Chord.minor(scale.get(1)));
        	list.get(2).add(Chord.major(scale.get(3)));
        	list.get(3).add(Chord.minor(scale.get(5)));
        	list.get(4).add(Chord.minor(scale.get(2)));
        
        	return list;

	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> bigProg(ArrayList<String> scale){
		
		ArrayList<ArrayList<ArrayList<String>>> list = new ArrayList<ArrayList<ArrayList<String>>>();
       
		for(int i = 0; i < 5; i++) {
        		list.add(new ArrayList<ArrayList<String>>());
        	}
		
		list.get(0).add(Chord.major(scale.get(0)));
		list.get(0).add(Chord.seventh(scale.get(0)));
		list.get(0).add(Chord.maj7(scale.get(0)));
		list.get(0).add(Chord.minor(scale.get(5)));
		list.get(0).add(Chord.min7(scale.get(5)));
		list.get(1).add(Chord.dom7(scale.get(4)));
        	list.get(1).add(Chord.dim(scale.get(6)));
        	list.get(2).add(Chord.minor(scale.get(1)));
        	list.get(2).add(Chord.min7(scale.get(1)));

        	list.get(2).add(Chord.major(scale.get(3)));
        	list.get(2).add(Chord.maj7(scale.get(3)));

       		 list.get(3).add(Chord.minor(scale.get(5)));
       		 list.get(3).add(Chord.min7(scale.get(5)));

        	list.get(4).add(Chord.minor(scale.get(2)));
        	list.get(4).add(Chord.min7(scale.get(2)));

        	return list;

	}
	
	public static ArrayList<ArrayList<String>> modes(ArrayList<String> scale){
		
		ArrayList<ArrayList<String>> modal = new ArrayList<ArrayList<String>>();
		
		for(int i =0; i < scale.size(); i++) {
			modal.add(new ArrayList<>(scale));
			Compose.leftShift(scale, 1);  // should not get this from Class compose
		
		} 
		return modal;
	}
	
    	public static ArrayList<String> keyId(ArrayList<String> chord){// returns a list of keys input chord is in
		
	        ArrayList<String> keys = new ArrayList<String>();
		Map<String, ArrayList<ArrayList<String>>> all = Modulation.allKeyChords(false);
		
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : all.entrySet()) {
			 if(entry.getValue().contains(chord)) {
				 keys.add(entry.getKey());
	      		 } 
       		} 
	    	return  keys;
		
   }	
    
   public static ArrayList<String> secondaryDominant(List<String> chord){ 
    	
    	return Chord.dom7(circleOfFifths(chord.get(0)).get(1).get(0));
    }
    
    public static ArrayList<String> keyIdMult(ArrayList<ArrayList<String>> chords){
		
    	Map<String, Integer> possKeys = new HashMap<String, Integer>();
		ArrayList<String> allPossKeys = new ArrayList<String>(); 
		
		for(ArrayList<String> chord : chords) {  
			for(String id : keyId(chord)) {  //mapping the keys each chord is in, adding number of occurences as value
			
				possKeys.merge(id, 1, Integer::sum);
				
			} 
		}     //System.out.println("test" + possKeys);
		
		possKeys.entrySet() // sorting  key occurences by number, and indexing into list, *low to high
	                .stream()
	            	.sorted(Map.Entry.comparingByValue())
	            	.forEachOrdered(x -> allPossKeys.add(x.getKey()));
		
	   	Collections.reverse(allPossKeys);  //reverses(highest number should be first)
	    
	   	return allPossKeys;
	}
	
 	 public static List<List<String>> invert(List<String> chord) {
		
	
		List<List<String>> inversions = new ArrayList<List<String>>();
		List<String> cho = new ArrayList<String>();
		
		for(int note = 0; note < chord.size(); note++) {
		        	cho.add(chord.get(note));
		        }
		
	    	for(int i = 0; i < cho.size(); i++){
			
			inversions.add(new ArrayList<String>(cho));
			cho.add(cho.get(0));
			cho.remove(0);
			
		} 
		return inversions;
	}
	
	public static Map<String, ArrayList<ArrayList<String>>> allKeyChords(boolean print){ // this one works
		
	   	Map<String, ArrayList<ArrayList<String>>> x = new HashMap<String, ArrayList<ArrayList<String>>>();
		String[] chrom = Arrays.copyOfRange(Scale.chromatic, 0, 12);
		
		for(String note : chrom) {
			x.put(note, Chord.keyChords(Scale.major(note)));
		}
		  
		 if(print) {
			for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : x.entrySet()) {
		         	System.out.println("Key: " + entry.getKey() + entry.getValue());
			  } 
		  }  
		  return x;
		 
	}	
	

}
