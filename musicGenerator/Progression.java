package musicGenerator;

import java.util.ArrayList;

public class Progression {
	
	ArrayList<ChordNode> chords;
    ArrayList<String> scale;
    ArrayList<ArrayList<String>> modes;
    public Integer current;
	
    
    public class ChordNode{
		
		ArrayList<String> chord;
		ArrayList<Integer> adjacency;
		ArrayList<ArrayList<String>> substitutions;
		boolean visited;
		
		
		public ChordNode(ArrayList<String> chord, int...adjacency) {
			
			this.chord = chord;
			this.adjacency = new ArrayList<>();
			this.substitutions = new ArrayList<>();
			
			for(int a : adjacency) {
				this.adjacency.add(a);
			}
		}
	}
	
	
    
    
	public Progression(ArrayList<String> scale){
		
		this.scale = scale;
		this.chords = new ArrayList<>();
		if(Scale.isMajor(scale)) {
			major();
		} 
		else {
			minor();
		}
	}
	
	public void major() {
		/*
		chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    chords.add(new ChordNode(Chord.major(scale.get()), )));
	    */
	   
    }
	
	public void minor() {
		
		chords.add(new ChordNode(Chord.minor(scale.get(0)),  3,4));
		chords.add(new ChordNode(Chord.diminished(scale.get(1)), 1,2));
	    chords.add(new ChordNode(Chord.major(scale.get(2)), 0,3,4,5));
	    chords.add(new ChordNode(Chord.minor(scale.get(3)), 0,1,4));
	    chords.add(new ChordNode(Chord.minor(scale.get(4)), 0,2,5));
	    chords.add(new ChordNode(Chord.major(scale.get(5)), 1,3,6));
	    chords.add(new ChordNode(Chord.major(scale.get(6)), 0));





		
		


	}
	
}
