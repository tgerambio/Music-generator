import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;



public class Scale {
	
	String I;
	String ii;
	String iii;
	String IV;
	String V;
	String vi;
	String vii;
	List<String> scale;
	
	public Scale(List<String> scale) {
		this.scale = scale;
	}
   
	
	
   final static String[] chromatic = {"C","C#","D","Eb","E","F","F#","G","G#","A","Bb","B","C","C#","D","Eb","E","F","F#","G","G#","A","Bb"};
   String[] chromsplit = {"C","C#/Db","D","D#/Eb","E","F","F#/Gb","G","G#/Ab","A","A#/Bb", "B"};
   
   
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
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1)); // accounts for b and #, but the key letter can be upper or lower
	  int modlength = o + 12;                                                                    // *notice it's not 7! 12 notes in an octave, this ensures we iterate all the way to the Vii degree 
	 	     
	  for (int k = o; k < modlength; k++) {                                                       // setting the iterator int k to variable o, (index of key note, we want the iteration to start there)
	 		   if ((k==(o+1)) || (k==(o+3)) || (k==(o+6)) || (k==(o+8)) || (k==(o+10))) continue; //skipping over notes not in the scale
			       majScale.add(chromatic[k]);                                                         
	  } return majScale;
  } 
   
  public static ArrayList<String> minor(String keynote){
	  ArrayList<String> minScale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1));
      int modlength = o + 12;
	  
      for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+4)) || (k==(o+6)) || (k==(o+9)) || (k==(o+11))) continue; 
		       minScale.add(chromatic[k]);
		    
	  } return minScale;
		    
  }
  public static ArrayList<String> harmonicMaj(String keyNote){
	  ArrayList<String> scale = major(keyNote);
	  scale.set(5, Note.toFlat(scale.get(5)));
	  return scale;
  }
  public static ArrayList<String> harmonicMin(String keyNote){
	  
	  ArrayList<String> minScale = new ArrayList<String>(Scale.minor(keyNote));
	  minScale.set(6, Note.toSharp(minScale.get(6)));
	  /*
	  int o = Arrays.asList(chromatic).indexOf(keyNote);
      int modlength = o + 12;
	  
      for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+4)) || (k==(o+6)) || (k==(o+9)) || (k==(o+10))) continue; 
		       minScale.add(chromatic[k]);
		    
	  } */
  
  return minScale;
	  
  }
  
  public static ArrayList<String> enigmaticMin(String keynote){
	  ArrayList<String> scale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote);
	  int modLength = o+12;
	 
	  for (int k = o; k < modLength; k++) {                                                      
		   if ((k==(o+2)) || (k==(o+4)) || (k==(o+5)) || (k==(o+8)) || (k==(o+9))) continue; 
		       scale.add(chromatic[k]);
	  } return scale;
  }
  
  public static ArrayList<String> pentatonic(String keynote){
	  ArrayList<String> pentScale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1));
      int modlength = o + 12;
	  
      for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+2)) || (k==(o+4)) || (k==(o+6)) || (k==(o+8)) ||  (k==(o+9)) || (k==(o+11))) continue; 
		       pentScale.add(chromatic[k]);
		    
	  } return pentScale;
  }
  
  
  public static ArrayList<String> blues(String keynote){
	  ArrayList<String> bluesScale = new ArrayList<String>();
	  int o = Arrays.asList(chromatic).indexOf(keynote.substring(0,1).toUpperCase()+keynote.substring(1));
      int modlength = o + 12;
	  
      for (int k = o; k < modlength; k++) {                                                      
		   if ((k==(o+1)) || (k==(o+2)) || (k==(o+4)) || (k==(o+8)) ||  (k==(o+9)) || (k==(o+11))) continue; 
		       bluesScale.add(chromatic[k]);
		    
	  } return bluesScale;
  }
  
  
  
  public static ArrayList<ArrayList<String>> modes(ArrayList<String> scale){
	  
	  ArrayList<ArrayList<String>> modal = new ArrayList<ArrayList<String>>();
	  
	 
      
      for(int i = 0; i < scale.size(); i++) {	 
    	  modal.add(new ArrayList<String>(scale));
    	  scale.add(scale.size(), scale.get(0));
    	  scale.remove(0);
    	  
      } return modal;
	
  }
  
  
public String getI() {
	return I;
}

public void setI(String i) {
	I = i;
}

public String getIi() {
	return ii;
}

public void setIi(String ii) {
	this.ii = ii;
}

public String getIii() {
	return iii;
}

public void setIii(String iii) {
	this.iii = iii;
}

public String getIV() {
	return IV;
}

public void setIV(String iV) {
	IV = iV;
}

public String getV() {
	return V;
}

public void setV(String v) {
	V = v;
}

public String getVi() {
	return vi;
}

public void setVi(String vi) {
	this.vi = vi;
}

public String getVii() {
	return vii;
}

public void setVii(String vii) {
	this.vii = vii;
}

@Override
public String toString() {
	return "Scale [I=" + I + ", ii=" + ii + ", iii=" + iii + ", IV=" + IV + ", V=" + V + ", vi=" + vi + ", vii=" + vii
			+ "]";
}
  

}






