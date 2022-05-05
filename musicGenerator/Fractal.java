package musicGenerator;

import java.util.ArrayList;
import java.util.Collections;


public class Fractal {
	
	public static void echoRandomModulation(Player p, ArrayList<String> chord, int measures, int ms) {
		 
		 
		 // List<Integer>midi1 = Convert.toMidi(chord, 4);
		  ArrayList<String> keysToChangeTo = Modulation.keyId(chord);
		  ArrayList<String> scale = Scale.major(keysToChangeTo.size() == 0? 
				                    chord.get(0) : keysToChangeTo.get(0));
		  if(!Chord.isMajor(chord)) {
			  Collections.rotate(scale, 5);
		  }
		  ArrayList<Integer> midi1 = Convert.toMidi(scale, 4);
		  p.mChannels[1].allNotesOff();
		  chord.add(chord.get(0));
          	  int solo = Convert.random(0, midi1.size());
         	  int velocity = 100;
		  for(int i = 0; i < measures; i++) {
			  ArrayList<Integer> midiC = Convert.toMidi(chord, 2);
			  
			  if(Convert.random(0, 8) < 5) {
				   p.mChannels[1].noteOn(midi1.get(solo < 0? midi1.size() + solo : solo % midi1.size()), velocity);
				   solo += Convert.random(-2, 3);
			   }
			   
			   //Collections.rotate(chord, 1);
			   for(int note : midiC) {
				   int r = Convert.random(0, 3);
				   if(r == 0) {
					   p.mChannels[0].noteOn(midi1.get(solo<0? midi1.size() + solo : solo % midi1.size()), 70);
					   solo += Convert.random(-2, 3);
					  
				   }
				  
				   p.mChannels[0].noteOn(note + (12* (r == 0 ? -1 : 1)), velocity);
				   Beat.cut(ms);
				   velocity -=3;
			   }
			   
			   if(Convert.random(0, 2) == 0) {
				   p.mChannels[1].noteOn(midi1.get(solo<0? midi1.size() + solo : solo % midi1.size())+(12*Convert.random(-2, 1)), velocity);
				   solo += Convert.random(-2, 3);
			   }
         
	   	}
	}
}
