package musicGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Scale {
	
	ArrayList<String> notes;
	ArrayList<ArrayList<String>> modes;
	ArrayList<Integer> midi;
	Map<String, int[]> modeSubs;
	
	public Scale(ArrayList<String> scale) {
		this.notes = scale;
		this.modes = modes(scale);
		this.midi = midi(scale);
	}
   
	
	
   final static String[] chromatic = {"C","C#","D","Eb","E","F","F#","G","G#","A","Bb","B","C","C#","D","Eb","E","F","F#","G","G#","A","Bb"};
   final static List<String> chrom = Arrays.asList(chromatic);
   String[] chromsplit = {"C","C#/Db","D","D#/Eb","E","F","F#/Gb","G","G#/Ab","A","A#/Bb", "B"};
   
   public static ArrayList<Integer> midi(ArrayList<String> scale){
	   ArrayList<Integer> m = new ArrayList<>();
	   //needs work here.
	   return m;
   }
   public static ArrayList<String> hybrid(ArrayList<String> scale){
	   
	   ArrayList<String> x = new ArrayList<String>();
	   x.add(scale.get(0));
	   x.add(scale.get(1));
	   x.add(scale.get(2));
	   x.add(scale.get(4));
	   x.add(scale.get(0));
       	   x.add(scale.get(1));
       	   x.add(scale.get(2));

	   return x;
   }
   public static ArrayList<String> major(String keynote){
	  
	  ArrayList<String> majScale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1)); 
	  int modlength = o + 12;                                                                     
	 	     
	  for (int k = o; k < modlength; k++) {                                                     
	 	 if ((k==(o+1)) || (k==(o+3)) || (k==(o+6)) || (k==(o+8)) || (k==(o+10))){
	         	continue; 
		 }
		 majScale.add(chromatic[k]);                                                         
	  } 
	  return majScale;
  } 
   
  public static ArrayList<String> minor(String keynote){
	 
	  ArrayList<String> minScale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1));
      int modlength = o + 12;
	  
      for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+4)) || (k==(o+6)) || (k==(o+9)) || (k==(o+11))) continue; 
		   minScale.add(chromatic[k]);
		    
	} 
        return minScale;
		    
  }
	
  public static ArrayList<String> harmonicMaj(String keyNote){
	  
	  ArrayList<String> scale = major(keyNote);
	  scale.set(5, Note.toFlat(scale.get(5)));
	  return scale;
  }
  public static ArrayList<String> harmonicMin(String keyNote){
	  
	  ArrayList<String> minScale = new ArrayList<String>(Scale.minor(keyNote));
	  minScale.set(6, Note.toSharp(minScale.get(6)));
	 
      	  return minScale;
	  
  }
  
  public static ArrayList<String> enigmaticMin(String keynote){
	  
	  ArrayList<String> scale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote);
	  int modLength = o+12;
	 
	  for (int k = o; k < modLength; k++) {                                                      
		   if ((k==(o+2)) || (k==(o+4)) || (k==(o+5)) || (k==(o+8)) || (k==(o+9))) continue; 
		   scale.add(chromatic[k]);
	  } 
	  return scale;
  }
  
  public static ArrayList<String> pentatonic(String keynote){
	 
	  ArrayList<String> pentScale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1));
      	  int modlength = o + 12;
	  
      	  for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+2)) || (k==(o+4)) || (k==(o+6)) || (k==(o+8)) ||  (k==(o+9)) || (k==(o+11))) continue; 
		   pentScale.add(chromatic[k]);
		    
	  } 
      	  return pentScale;
  }
  
  public static ArrayList<String> blues(String keynote){
	 
ArrayList<String> bluesScale = new ArrayList<String>();
      int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1));
      int modlength = o + 12;
	  
      for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+2)) || (k==(o+4)) || (k==(o+8)) ||  (k==(o+9)) || (k==(o+11))) continue; 
		   bluesScale.add(chromatic[k]);
		    
	  } 
          return bluesScale;
  }
  
  public static ArrayList<ArrayList<String>> modes(ArrayList<String> scale){
	  
	  ArrayList<ArrayList<String>> modal = new ArrayList<ArrayList<String>>();
	  
	  for(int i = 0; i < scale.size(); i++) {	 
    	  modal.add(new ArrayList<String>(scale));
    	  scale.add(scale.size(), scale.get(0));
    	  scale.remove(0);
    	  
      } 
      return modal;
   }
	
   public static boolean isMajor(ArrayList<String> scale) {
        
	    ArrayList<Integer> m = Convert.toMidi(scale, 0);	 
	    
	    return m.get(2) - m.get(0) == 4;
   }
}  
  
