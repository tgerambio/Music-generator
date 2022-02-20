package musicGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MidiMap {
	
	public Map<Integer, ArrayList<Integer>> map;
	
	public MidiMap() {
		
		this.map = new HashMap<>();
	}
	
	public void putNoOverwrite(Integer beat, int note) {
		
		if(map.containsKey(beat)) {
			map.get(beat).add(note);
		}
		else {
			map.put(beat, new ArrayList<Integer>());
			map.get(beat).add(note);
		}
	}
}
