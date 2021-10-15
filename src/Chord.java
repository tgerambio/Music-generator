import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




public class Chord {
	
	
	public static ArrayList<String> keyId(ArrayList<String> chord){ // returns a List of all keys the given chord is in
		
	    ArrayList<String> keys = new ArrayList<String>();
		Map<String, ArrayList<ArrayList<String>>> x = Modulation.allKeyChords(false);
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : x.entrySet()) {
			 if(entry.getValue().contains(chord)) {
				 keys.add(entry.getKey());
	    } 
    } return  keys;
		
}
	
	
	public static ArrayList<String> enrich(ArrayList<String> chord, int...dups){
		for(int x : dups) {
			chord.add(chord.get(x-1));
		}
		
		return chord;
	}
	public static ArrayList<ArrayList<String>> keyChords(ArrayList<String> scale){
		    
            // generates each degree chord in a given scale
		
		    ArrayList<String> degree = new ArrayList<String>();
		    degree.add("I Maj  :");
		    degree.add("ii min :");
		    degree.add("iii min:");
		    degree.add("Iv Maj :");
		    degree.add("V Maj  :"); 
		    degree.add("vi min :"); 
		    degree.add("vii dim:");
		   
		    
		    ArrayList<ArrayList<String>> chords = new ArrayList<ArrayList<String>>();
			ArrayList<String> triad = new ArrayList<String>();
			ArrayList<ArrayList<String>> modes = Scale.modes(scale);
			
			
			
			for(int i = 0; i < modes.size(); i++) {
			//	triad.add(degree.get(i));
			
				triad.add(modes.get(i).get(0));
				triad.add(modes.get(i).get(2));
				triad.add(modes.get(i).get(4));
				
				chords.add(new ArrayList<String>(triad));
				triad.removeAll(triad);
				
				
			} return chords;
			
			
    }	
	public static ArrayList<String> toFlat(ArrayList<String> chord){
		
		return chord.stream()
				    .map((x)-> Note.toFlat(x))
				    .collect(Collectors.toCollection(ArrayList::new));
	}
	public static ArrayList<String> randomAugment(ArrayList<String> chord){ 
    
		int x = Convert.random(0, 3);
		
		
		ArrayList<String> scale = chordId(chord).equals("major")? Scale.major(chord.get(0)) : Scale.minor(chord.get(0));
		scale.add(scale.get(0));
		
		int y = scale.indexOf(chord.get(x));
		
		chord.add(x, scale.get(y+1));
		
		
			
		
		
		return chord;
		
	}
	
	public static ArrayList<String> augment(ArrayList<String> chord, int index){ 
	    
		
		ArrayList<String> c = new ArrayList<String>(chord);
		ArrayList<String> scale = chordId(chord).equals("major")? Scale.major(chord.get(0)) : Scale.minor(chord.get(0));
		scale.add(scale.get(0));
	    
		int y = scale.indexOf(chord.get(index));
		
		c.add(index+1, scale.get(y+1));
		return c;
		
	}
	
	public static String chordId(ArrayList<String> chord) { // this needs more work
	      
		return Scale.major(chord.get(0)).get(2).equals(chord.get(1)) ? "major" : "minor";
		
	}
	
	public static ArrayList<ArrayList<String>> keyChords(String note, String type){
		// generates a list of each chord for each scale degree, major or minor (String type)
		ArrayList<String> scale = null;
		
		if(type.equals("major")) {
		   scale = Scale.major(note);
		}
		if(type.equals("minor")) {
		   scale = Scale.major(note);
		}
	    
		
		ArrayList<ArrayList<String>> chords = new ArrayList<ArrayList<String>>();
	    ArrayList<String> triad = new ArrayList<String>();
		ArrayList<ArrayList<String>> modes = Scale.modes(scale);
		
		for(int i = 0; i < modes.size(); i++) {
			triad.add(modes.get(i).get(0));
			triad.add(modes.get(i).get(2));
			triad.add(modes.get(i).get(4));
			//triad.add("(" + modes.get(i).get(6) + ")");
			chords.add(new ArrayList<String>(triad));
			triad.removeAll(triad);
			
			
		} return chords;
		
		
		
	}
 public static ArrayList<String> major(String rootNote){
	 ArrayList<String> chord = new ArrayList<String>();
	 chord.add(Scale.major(rootNote).get(0));
	 chord.add(Scale.major(rootNote).get(2));
	 chord.add(Scale.major(rootNote).get(4));
	
	 return chord;
	 
 }
 
 public static ArrayList<String> minor(String rootNote){
	 ArrayList<String> chord = new ArrayList<String>();
	 chord.add(Scale.minor(rootNote).get(0));
	 chord.add(Scale.minor(rootNote).get(2));
	 chord.add(Scale.minor(rootNote).get(4));
	 return chord;
	 
 }
 
 public static ArrayList<String> dom7 (String rootNote){
	 
	 ArrayList<String> chord = new ArrayList<String>();
	 ArrayList<String> scale = Scale.major(rootNote);
	 chord.add(rootNote);
	 chord.add(scale.get(2));
	 chord.add(scale.get(4));
	 chord.add(Note.toFlat(scale.get(6)));
	 return chord;
 }
 
 public static ArrayList<String> seventh(String rootNote){
	 ArrayList<String> chord = new ArrayList<String>();
	 List<String> scale = Scale.major(rootNote);
	 chord.add(rootNote);
	 chord.add(scale.get(2));
	 chord.add(Note.toFlat(scale.get(4)));
	 chord.add(scale.get(6));
	 return chord;
	 
 }

 public static ArrayList<String> maj7(String rootNote){
	
	 ArrayList<String> chord = new ArrayList<String>();
	 List<String> scale = Scale.major(rootNote);
	 
	 chord.add(rootNote);
	 chord.add(scale.get(2));
	 chord.add(scale.get(4));
	 chord.add(scale.get(6));
	 
	 return chord;
	
 }
 
 public static ArrayList<String> dim(String rootNote){
	 
	 ArrayList<String> chord = new ArrayList<String>();
	 ArrayList<String> scale = Scale.major(rootNote);
	 
	 chord.add(rootNote);
	 chord.add(Note.toFlat(scale.get(2)));
	 chord.add(Note.toFlat(scale.get(4)));
		 
	 return chord;
 }
 public static ArrayList<String> min7(String rootNote){
	 
	 ArrayList<String> chord = new ArrayList<String>();
	 ArrayList<String> scale = Scale.major(rootNote);
	 
	 chord.add(rootNote);
	 chord.add(Note.toFlat(scale.get(2)));
	 chord.add(scale.get(4));
	 chord.add(Note.toFlat(scale.get(6)));
	 
	 return chord;
 }

public static ArrayList<String> inversion(int invertNum, ArrayList<String> chord){// returns 1st, 2nd inversions
	if(invertNum == 1) {
		chord.add(chord.get(0));
		chord.remove(0);
	} if(invertNum == 2) {
		 chord.add(chord.get(0));
		 chord.add(chord.get(1));
		 chord.remove(0);
		 chord.remove(1);
	} return chord;
  
}	
}