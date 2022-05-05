import java.util.ArrayList;

import javax.sound.midi.MidiUnavailableException;

public class Experiment {

	public static Runnable x = ()-> {
		
			Compose.echoChamber(Chord.randomAugment(Chord.minor("D")), 600);          //wind("F", 1600);
		
	};
	
	public static void all(Runnable runnable, int ms){ 
		int i = 0;
		
		while(i < 3 ) {
			
			new Thread(runnable).start();
			i++;
			Beat.cut(ms);
		}
		
		
	}
	
	public static void loopy(ArrayList<String> scale, int ms) throws MidiUnavailableException {
		
		Player x = new Player();
		x.open();
		while(true) {
			for(ArrayList<String> mode : Scale.modes(scale)) {
				for(String note : mode) {
					x.mChannels[1].noteOn(Convert.midiNums(note)[Convert.random(2,5)], 100);
					Beat.cut(note.equals(scale.get(0)) || note.equals(scale.get(4)) ? ms : ms/4);
			
				}
			}
		}
		
	}
	public static void xxxx() {
		Playerexp p = new Playerexp();
		p.open();
		
		while(true) {
			p.mChannels[0].noteOn(100, 100);
			Beat.cut(220);	
		
		}

	}	
	

}
