
public class Note {

	public static final String[] chromatic = {"C","C#","D","Eb","E","F","F#","G","G#","A","Bb","B","C","C#","D","Eb","E","F","F#","G","G#","A","Bb"};

	public static String toFlat(String note){
		
		if(note.equals("C")) return "B";
		
		for(int i = 0; i < 12; i++) {
			if(chromatic[i].equals(note)) return chromatic[i-1];
		} return "C";
		
	}
	
	public static String toSharp(String note){
		
		for(int i = 0; i < 13; i++) {
			if(chromatic[i].equals(note)) return chromatic[i+1];
		} return null;
		
	}
	
	public static String getDegree(String note, int degree) {
		return Scale.major(note).get(degree-1);
	}
	
	public static String enharmonicEquivelent(String note) {
		String[] stragglers = {"Cb", "Db","E#","Fb","Ab","A#","B#"};
		String[] equivInIndex= {"B", "C#","F","E","G#","Bb","C"};
		
		for(int i = 0; i < stragglers.length; i++){
			if(stragglers[i].equals(note)) {
				return equivInIndex[i];
			}
		} return null;
		 
		
	}
		
	
}


