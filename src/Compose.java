import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;


public class Compose {
	
  /*
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
   
    */
		public Compose() {
		
	}
	public static void recurseShell(String note, int ms) {
		
		Player p = new Player();
		p.mChannels[0].programChange(46);//46
		recurseSeed(p,Scale.minor(note), ms);
		
	}
	
	public static Object recurseSeed(Player p, ArrayList<String> scale,  int ms) {
		
	    ArrayList<ArrayList<String>> chords = Chord.keyChords(scale);
	    
		while(true) {
			
	    	int n = 0;
	    	
	    	List<Integer> x = Convert.merge(Chord.augment(chords.get(n), 2));
	        int s = x.indexOf(Convert.lowestNotes().get(scale.get(0)));

	        int cut = 0;
	          
	        //for(int note2 = s+7; note2 < 24; note2++) {
	        for(int note2 = s; note2 < s+16; note2++) {
	           
	        	Collections.rotate(x, cut%3 + 3); // changing leftshift cut%3+# changes composition drastically
	            int m = x.get(note2);
	            p.mChannels[0].noteOn(m < 70 ? m : m-36,  100 ); // shifts pitches back into tonal center
	          //p.mChannels[0].noteOn(m, 100);
	          //p.mChannels[0].noteOn(x.get(Convert.random(x.size()-24, x.size()-8)), 50);

	            cut++;
	            Beat.cut(cut%2 == 0 ? ms : 0);  // notice the 0!! will then play simultaneously
	    		
	    	}
	    	
			return recurseSeed(p, Scale.minor(scale.get(Convert.random(3, 5))), ms); // always return 4th or 5th
			
			
	    }
	}
	//*******************************************************************************
	
	public static void blues(Player p, ArrayList<String> scale, int measures, int ms, int...prog) {
	  // List<Integer> midiBlue = Convert.mergeList(Scale.blues(scale.get(0)), 4);
	   //for(int i = 0; i < 8; i++) midiBlue.add(midiBlue.get(i)+12);
	   ArrayList<String> minBlues = Scale.blues(scale.get(0));
	   ArrayList<String> majBlues = Scale.blues(scale.get(0));
	   Collections.rotate(majBlues, 1);
	   int solo = Convert.random(0, 4);
	  
	   while(true) {
		   for(int c : prog) {
			   
			  List<Integer>midiBlue = Convert.toMidi(Chord.isMajor(Chord.keyChords(scale).get(c-1)) ? majBlues : minBlues, 4);
			  if(Chord.isMajor(Chord.keyChords(scale).get(c-1))) {
				  
			  }
			  p.mChannels[1].allNotesOff();
	          ArrayList<Integer> midi = Convert.toMidi(Chord.bluesItUp(Chord.keyChords(scale).get(c-1)), 2);
	          int velocity = 100;
			  for(int i = 0; i < measures; i++) {
				   
				  if(Convert.random(0, 8) < 5) {
					   p.mChannels[1].noteOn(midiBlue.get(solo<0? midiBlue.size() + solo : solo % midiBlue.size()), velocity);
					   solo += Convert.random(-2, 3);
				   }
				   
				   
				   for(int note : midi) {
					   int r = Convert.random(0, 3);
					   if(r == 0) {
						   p.mChannels[0].noteOn(midiBlue.get(solo<0? midiBlue.size() + solo : solo % midiBlue.size()), 70);
						   solo += Convert.random(-2, 3);
						  
					   }
					  
					   p.mChannels[0].noteOn(note + (12* (r == 0 ? -1 : 1)), velocity);
					   Beat.swing(ms);
					   velocity -=5;
				   }
				   
				   if(Convert.random(0, 2) == 0) {
					   p.mChannels[1].noteOn(midiBlue.get(solo<0? midiBlue.size() + solo : solo % midiBlue.size())+(12*Convert.random(-2, 1)), velocity);
					   solo += Convert.random(-2, 3);
				   }
               }
		   }
	   }
   }
	public static void balla(ArrayList<String> scale,  int ms, int...progression) { // minor 1,5,6,4 !!
		
		
		Player p = new Player();
        p.open();
		p.mChannels[0].programChange(1);  // 32! 
		
		ArrayList<ArrayList<String>> prog = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> all = Chord.keyChords(scale);
		
		for(int x : progression) {
			prog.add(all.get(x-1));
		}
		
        while(true) {
        
	    for(ArrayList<String> chord : prog) {
			
	    	for(int i = 2; i >=0; i--) {
				
			    
				List<Integer> midi = Convert.merge(Chord.augment(chord, i));
			    int start = midi.indexOf(Convert.lowestNotes().get(chord.get(0)));
			    
				List<Integer> m = midi.subList(start+4, start + 20);
				
				p.mChannels[0].noteOn(midi.get(start), 100);
				Runnable r = ()->{
					for(int ind = start + 19; ind >= start + 15; ind--) {
						p.mChannels[0].noteOn(midi.get(ind), 100);
						Beat.cut(ms*2);
						
					}
				};
				new Thread(r).start();
				
				for(int x : m){
					//p.mChannels[0].allNotesOff();
					p.mChannels[0].noteOn(x, 100);
					Beat.cut(ms);
				
				   
				  
				
			/*	}if(i==0){
					for(int y : m){
						p.mChannels[0].noteOn(y, 100);
						Beat.cut(ms);
				} */
					
		}if(i==0){
			for(int y : m){
				
				p.mChannels[0].noteOn(y, 100);
				Beat.cut(ms);
		}
		}
	    }   
        }
        } 
	}
	//******************************************************************************************************
	
	public static void bigOne(ArrayList<String> scale, int cutMs, int latency, int ms, int...progression) { //   major  4,7,3,6,2,3,1
		   
		   Player p = new Player();
		   Runnable e =()-> Compose.genericProg(p, scale ,ms, progression); 

    	
	       new Thread(e).start();
		   Beat.cut(cutMs);
		   new Thread(e).start();
		   
	}
	public static void rc_mergeList(ArrayList<String> scale) {
		
		Player p = new Player();
		
		for(ArrayList<String> chord : Chord.keyChords(scale)) {
			recursiveDrop(p, Convert.mergeList(Chord.toSeventh(chord), 4), 8, 4, 4, 100);
		}
	}
	public static void genericProg(Player p, ArrayList<String> scale, int ms, int...progression) {
		
		
	 while(true) {
		 for(int j = 4; j >=3; j--){
			
			for(int i : progression) {
				
				recursiveDrop(p, Convert.mergeList(Chord.toSeventh(Chord.keyChords(scale).get(i-1)), j), 4, 4, 4, ms);
			    // recursiveDrop(p, Convert.mergeList(Chord.augment(Chord.keyChords(scale).get(i-1), 2), j), 4, 4, 4, ms);
			}
			}
		}
	}
	public static int[] intervals() {
		return new int[] {0,3,7,8};
	}
	public static ArrayList<Integer> counterpoint(ArrayList<Integer> cantusFirmis, int beats){
		ArrayList<Integer> counter = new ArrayList<Integer>();
		
		int index = 0;
		for(int note : cantusFirmis) {
			counter.add(index == 0 ? note + intervals()[Convert.random(0, intervals().length)] : note + intervals()[cantusFirmis.get(index) > 
			            cantusFirmis.get(index-1) ? 1 : 2]);
			index++;
		}
		return counter;
	}
	public static void cantusTest() {
		Player p = new Player();
		for(int note : Fractal.cantusFirmis(Scale.major("D"), 32)) {
			p.mChannels[0].noteOn(note, 100);
			Beat.cut(440);
		}
	}
	public static void vLMod(Player p, ArrayList<String> scale, int ms, int[] arpPattern, int...prog){
        
    	
    	int xx = 0;
    	int invert = Convert.random(0, 3);
    	int newKeyC = 0;
    	int[] dV = {1, 2, 6, 4};
    	while(true){

    		for(int c : prog){

    			ArrayList<String> chord = Chord.enrich(Chord.keyDeg(scale, c-1), 1);
    			ArrayList<String> dom = Modulation.secondaryDominant(chord);
    			ArrayList<String> dom2 = Chord.keyDeg(Scale.major(dom.get(0)), newKeyC++);
    			ArrayList<String> dom3 = Modulation.secondaryDominant(dom2);
    			ArrayList<String> dom4 = Chord.keyDeg(Scale.minor(dom2.get(0)), 1);

 
    			Chord.invert(chord, invert++ % 4);
    			Chord.invert(dom, invert%4 *-1);
    			Chord.invert(dom2, invert%4);
    			Chord.invert(dom3, invert%4 *-1);
               
    		
    			arpeggioDonut(p, dom4, 2, 4, dV[xx%4], ms, arpPattern);

    			p.play(Convert.midiNums(dom3.get(1))[3]);
    			arpeggioDonut(p, dom3, 2, 4, dV[xx %4], ms, arpPattern);
    			p.play(Convert.midiNums(dom2.get(1))[3]);
                
    			arpeggioDonut(p, dom2, 2, 4, dV[xx%4], ms, arpPattern);
    			p.play(Convert.midiNums(dom.get(1))[3]);

    			arpeggioDonut(p, dom, 2, 4, dV[xx%4], ms, arpPattern);
    			p.play(Convert.midiNums(chord.get(1))[3]);

    			arpeggioDonut(p, chord, 2, 4, dV[xx%4],  ms, arpPattern);
                
    			xx++;
    		}
    		
    		
    	}
    	
    	
    	
    }
    
    public static void arpeggioDonut(Player p, ArrayList<String> chord, int octave, int count, int playOff, int ms, int...pattern) {
 	   
 	   List<Integer> midi = Convert.toMidi(chord);
 	   int velocity = 100;
 	   int c = octave;
 	   
 	   while(c < count) {
 		  
 		   for(int i : pattern) {
 			   
 			   p.mChannels[0].noteOn(midi.get(i)+(12*c), Beat.counter% playOff == 1 ? 0 : velocity--);
 			   Beat.cut(ms);
 		   }
 		   c++;
 	   }
 	   return;
    }
	public static void windModes(ArrayList<String> scale, int beats, int ms, int...progression) {
		
		Player p = new Player();
		ArrayList<ArrayList<String>> modes = Scale.modes(scale);
		
		while(true) {
		for(int degree : progression) {
		
			for(int b = 0; b < beats; b++) {
				int rand = Convert.random(0, 3);
				if(b==0 || b==beats/2) {
			    p.mChannels[0].noteOn(Convert.midiNums(modes.get(degree-1).get(0))[1], 100);
				}
			    //p.mChannels[0].noteOn(Convert.midiNums(modes.get(degree-1).get(4))[2], b%2==1? 0 : 100);
	            if(rand % 2==0) {
	               int rNote = Convert.random(3,6);
	               int rOctave = Convert.random(2,4);
	               
	               p.mChannels[0].noteOn(Convert.midiNums(modes.get(degree-1).get(rNote))[rOctave], 100);
	               Beat.cut(ms);
	            }
	            }
			}
		}
	}
	   public static List<Integer> drop(List<Integer> chord, int index){ // index not 0-base!! ex.. C maj (1) is note C 
                if(index < 1 || index > chord.size()) {
                	return chord;
                }
		        chord.add(0, chord.get(index -1) -12);
		        chord.remove(index);
		        chord.add(index, chord.get(chord.size()-1)-12);
                chord.remove(chord.size()-1);
                return chord;
		}

		public static List<Integer> recursiveDrop(Player p, List<Integer> chord, int count, int drop, int latency, int ms){
		        p.mChannels[0].noteOff(ms);
		       
		         
		         if(count == 0){
		           return null;
		        }
		     
		        for(int note : chord){
		            p.mChannels[0].noteOn(note < 24? note + 60 : note, 75);
		            Beat.cut(drop%2==0? ms/latency : 0);  //Beat.cut(drop%2==0? ms/3 : 0) !!
		           
		        }
		        Beat.cut(drop%1==1? ms : 0);
		       
		        return recursiveDrop(p, drop(chord, drop), count - 1, drop-1, latency, ms);
		}
    //**************************************************************************************************************
	public static void dropper(ArrayList<String> chord) {
		Player p = new Player();
		
		List<Integer> c = Convert.mergeList(chord, 5);
		
		int i = 0;
		while(i < 5) {
		   for(int note : c) {
			   p.mChannels[0].noteOn(note, 100);
			   Beat.cut(200);
			   i++;
		   } 
		   c = drop(c, 2);
		}
		
	}
	public static Map<Integer, Integer> mapper(Player p, ArrayList<String> scale, int measures, int beats, int[] prog, int ms) {
		int[] one45 = {0,3,4};
		Map<Integer, Integer> map = new HashMap<>();
		while(map.size() < beats/2) {
			for(int x = 0; x < beats; x++) {
				if(map.containsKey(x)) {
					continue;
				}else {
					if(Convert.random(0,2) == 0) {
		               map.put(x, one45[Convert.random(0, 3)]);
					}
				}
			}
		}
		for(int i : prog) {
			for(int m = 0; m < measures; m++) {
				for(int b = 0; b < beats; b++) {
					if(map.containsKey(b)){
						p.mChannels[0].noteOn(Convert.mergeList(Scale.modes(scale).get(i-1), 1).get(map.get(b)), 100);
					}
					if(Convert.random(0, 2)== 1) {
						p.mChannels[0].noteOn(Convert.mergeList(scale, 3).get(Convert.random(0, scale.size())), 100);
					}
				    Beat.cut(ms);
				}
				
			}
		}
		
		return mapper(p, scale, measures, beats, prog, ms);
		
	}
	public static void bounce(Player p, int midiNote){
		int ms = 1000;
		while(ms > 20) {
			p.mChannels[0].noteOn(midiNote, 100);
			Beat.cut(ms);
			ms -= ms/6;
		}
		
	}
	public static void phraseProg(Player p, ArrayList<String> scale, int beats, int ms, int...prog) {
		Map<Integer, Integer> phrase = new HashMap<>();
		ArrayList<Integer> midi = Convert.mergeList(scale, 3);
		int[] movement = {-2, -1, 0, 1, 2 };
		int start = Convert.random(0, scale.size());
		
		for(int i = 0; i < beats; i++) {  // creates/maps random melodic line
			if(Convert.random(0, 2) == 0) {
				phrase.put(i, midi.get(start < 0 ? midi.size()+start : start%midi.size())); //so it never goes out of bounds
				start += movement[Convert.random(0, movement.length)];                      //maybe make them just indexes?
			}
		}
		
		Runnable repeat = ()-> {  for(int b = 0; b < beats; b++) {
			                          if(phrase.containsKey(b)) {
				                          p.mChannels[0].noteOn(phrase.get(b), 100);
				                      }
			                          Beat.cut(ms);
		                          }
		                       };
		
	    while(true) {
			for(int c : prog) {
				ArrayList<Integer> m = Convert.mergeList(Chord.keyChords(scale).get(c-1), 3);
				new Thread(repeat).start();
				for(int note : m) {      
					p.mChannels[0].noteOn(note, 100);
				}
				Beat.cut(ms*beats);
			}
		}
		
	}
	public static void variantProg(Player p, ArrayList<String> scale, int beats, int ms, int...prog) {
		int index = 0;
		int[] changes = { -2, -1, 0, 1, 2 };
		while(true) {
			for(int chord : prog) {
				for(int i = 0; i < beats; i++){
					for(int note : Convert.mergeList(Chord.keyChords(scale).get(chord-1), 3)) {
						p.mChannels[0].noteOn(note + (12*changes[(index++)%changes.length]), 100);
						Beat.cut(ms);
					} 
				}
			}
		}
	}
	public static void fifths4thsandOctaves(Player p, int note, int ms) {
		int[] changes = { -5, 7, 12, 5, -7 };
		while(true) {
			p.mChannels[0].noteOn(note, 100);
			note += changes[Convert.random(0, 5)];
			Beat.cut(ms);
			note += note < 30? 12 : note > 72 ? -12 : 0;
		}
		
	}
	public static void octaveChanges(Player p, ArrayList<String> scale, int beats, int ms, int...prog) {
		
		int[] octaveChange = { 0, -12, 12 };
		Map<Integer, Integer> beatMap = new HashMap<>();
		Map<Integer, Integer> beatMap2 = new HashMap<>();
		
		for(int i = 0; i < beats; i++) {
			if(Convert.random(0, 2)==0) {
				beatMap.put(i, Convert.random(0, scale.size()));
			}
			if(Convert.random(0,2)==0) {
				beatMap2.put(i, Convert.random(0, scale.size()));
			}
		}
		while(true) {
			boolean oChange = Convert.random(0, 2)==0? true : false;
			for(int c : prog) {
				ArrayList<Integer> midi = Convert.mergeList(Scale.modes(scale).get(c-1), 3);
			
				for(int b = 0; b < beats; b++) {
					if(beatMap.containsKey(b)) {
						p.mChannels[0].noteOn(midi.get(beatMap.get(b)) + octaveChange[oChange? 0 :Convert.random(0,3)], 100);
					}
					
					if(beatMap2.containsKey(b)) {
						p.mChannels[0].noteOn(midi.get(beatMap2.get(b)) + octaveChange[oChange? 0 :Convert.random(0,3)], 100);
					}
					Beat.cut(ms);
			}
		}
	}
		
	}
	//**************************************************************************************************************
	public static void windProghelper(Player p, ArrayList<String> scale, int beats, int octave, int ms, int...prog) {	
		
		Map<Integer, Integer> beatMap = new HashMap<>();
		for(int i = 0; i < beats; i++) {
			if(Convert.random(0, 2)==0) {
				beatMap.put(i, Convert.random(0, scale.size()));
			}
		}
		for(int c : prog) {
			ArrayList<Integer> midi = Convert.mergeList(Scale.modes(scale).get(c-1), octave);
			for(int b = 0; b < beats; b++) {
				if(beatMap.containsKey(b)) {
					p.mChannels[0].noteOn(midi.get(beatMap.get(b)), 100);
				}
				Beat.cut(ms);
			}
		}
		
	}
	public static void windProg(Player p, ArrayList<String> scale, int beats, int ms, int...prog) {
		
		Runnable r = ()-> Compose.windProghelper(p, scale, beats, 2, ms, prog);
		Runnable r2 = ()-> Compose.windProghelper(p, new ArrayList<>(scale), beats, 3, ms, prog);
        ArrayList<String> avoidConcurrent = new ArrayList<>(scale);
        while(true) {
        	new Thread(r).start();
        	new Thread(r2).start();
        	Compose.windProghelper(p, avoidConcurrent, beats, 3, ms, prog);
        }
       
	}
	//****************************************************************************************************************
	public static void toolProg(Player p, ArrayList<String> scale, int ms, int beats, int...prog) {
		p.mChannels[0].programChange(32); //32
		p.mChannels[1].programChange(46); //46(at 1/2 vol), 8
		Map<Integer, Integer> bassLine = new HashMap<>();
		int[] oneFour5 = {0, 3, 4};
		int count = 4;
		while(true) {
			if(beats == 0) return;
			for(int i = 0; i < beats; i++) {
				if(Convert.random(0, 2) == 0) {
					bassLine.put(i, oneFour5[Convert.random(0, 3)]);
				}
			for(int c : prog) {
				p.mChannels[0].allNotesOff();
			
				for(int b = 0; b < beats; b++) {
					if(bassLine.containsKey(b)) {
						p.mChannels[0].noteOn(Convert.mergeList(Scale.modes(scale).get(c-1), 1).get(bassLine.get(b)), 100);
						if(Convert.random(0,3)== 1) {
								
							p.mChannels[1].noteOn(Convert.midiNums(scale.get(b%scale.size()))[Convert.random(2, 4)], 100);
							p.mChannels[1].noteOn(Convert.midiNums(Scale.modes(scale).get(c-1).get(oneFour5[Convert.random(0, 3)]))[Convert.random(2, 4)], 75);
							
							//p.mChannels[1].noteOn(Convert.midiNums(Scale.modes(scale).get(c-1).get(oneFour5[Convert.random(0, 3)]))[Convert.random(2, 4)], 75);

							}
						}
						Beat.cut(ms);
					}
			}
		}
        if(bassLine.size()==beats) {
        	count--;
        }
        if(count < 1) {
        	bassLine.clear();
        	beats--;
        	System.out.println(beats);
        	count = 4;
        }
		
		
	}
		
	}
	
	public static void secondaryDomProg(Player p, ArrayList<String> scale, int ms, int...prog) {
			
		while(true) {
			int octave = Convert.random(1,5);
		        for(int c : prog) {
					ArrayList<String> chord = Chord.keyChords(scale).get(c-1);
					ArrayList<String> secDom = Modulation.secondaryDominant(chord);
					chord.add(chord.get(0));
					Collections.rotate(chord,Convert.random(0, 4));
					Collections.rotate(secDom, octave);
					for(int n : Convert.mergeList(secDom, octave)) {
						p.mChannels[0].noteOn(n, 100);
						Beat.cut(ms);
					}
					//Beat.cut(ms);
					for(int n2 : Convert.mergeList(chord, octave)) {
						p.mChannels[0].noteOn(n2, 100);
						Beat.cut(ms);
					}
			
					
				}
		}
			
	}
	public static void randomAugtst(ArrayList<String> scale, int loops, int ms) {
		
		Player p = new Player();
	
		
		ArrayList<ArrayList<ArrayList<String>>> list = Modulation.progList(scale);
		
		int loop = 0;
		
		while(loop < loops) {
			    
			    
			    int x = Convert.random(0, list.size());
			    
			    
			    for(int c = x; c >= 0; c--) {
				    List<Integer> l = Convert.merge(Chord.randomAugment(list.get(c).get(Convert.random(0, list.get(c).size()))));
				    List<Integer> midiN = l.subList(l.size()-21, l.size()-5);
				    /*
				    for(int i = 2; i < midiN.size(); i+=2) {
				    	Collections.swap(midiN, i-2, i);
				    }
			        */
				    
				    for(int note : midiN){
					    p.mChannels[0].noteOn(note, 100);
					    Beat.cut(ms);
				}
				    
				    for(int i = midiN.size()-1; i >=0; i--){
					    p.mChannels[0].noteOn(midiN.get(i), 100);
					    Beat.cut(ms);
				
				}
			}
			      loop++;
			      
		}
		
	}
	static class Dude{
		Player p;
		Beat beat;
		Compose c;
		int beats;
		int ms;
		ArrayList<String> scale;
		public Dude() {
			
		}
		public Dude(Player p, ArrayList<String> scale, int ms){
			super();
			this.p = p;
			this.scale = scale;
			while(true) {
				for(String note : scale) {
				p.mChannels[0].noteOn(Convert.midiNums(note)[3], 100);
				Beat.cut(ms);
				}
			}
	
		}
		
		
	}
	public static void progTest(ArrayList<String> scale, int ms) {
		Beat beat = new Beat();
		Player p = new Player();
		p.open();
		p.mChannels[0].programChange(46);
		p.mChannels[1].programChange(0);
		
		ArrayList<ArrayList<ArrayList<String>>> list = Modulation.progList(scale);
		int i = 0;
		
		while(i < 3) {
			
			int x = Convert.random(0, list.size());
		
			p.mChannels[1].allNotesOff();
			for(int outer = x; outer >= 0; outer--) {
				//p.mChannels[1].allNotesOff();			
				int y = Convert.random(0, list.get(outer).size());
				
				int j = 0;
				
				while(j<2) { 
				    
					List<Integer> midiN = Convert.merge(list.get(outer).get(y));
	                 
					Runnable r = ()->{
						Beat b = new Beat();
						for(int h : midiN.subList(9, 17)) {
							p.mChannels[1].noteOn(h, 100);
							//Beat.cut(ms*2);
							Beat.swing(ms);
							}
					};
					p.mChannels[1].allNotesOff();
				    new Thread(r).start();
					for(int m : midiN.subList(6,20)){
						
						p.mChannels[0].noteOn(m, 100);
						//Beat.cut(ms);
						Beat.swing(ms);
						
					}
					p.mChannels[1].noteOn(midiN.get(11), 100);
					p.mChannels[1].noteOn(midiN.get(9), 100);
					
					for(int d = 20; d >= 6; d--){
						
						p.mChannels[0].noteOn(midiN.get(d), 100);
						//Beat.cut(ms);
						Beat.swing(ms);
					}
					
					j++;
					}
				} i++;
		}
	}
	
	public static void go(List<String> scale, int measures, int beats, int ms){
		
		Player player = new Player();
		player.open();
		
		Map<Integer, Integer> beatMap = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < beats; i++) {
			
			if(Convert.random(0, 3) %2==0) {
				
				beatMap.put(i, Convert.random(0, scale.size()));
			}
		}
		int m = 0;
		while(m < measures) {
            int x = Convert.random(2, 5);
			for(int i = 0; i < beats; i++) {
				if(beatMap.containsKey(i)) {
					player.mChannels[0].noteOn(Convert.midiNums(scale.get(beatMap.get(i)))[x], 100);
				   
				}
				m++;
				Beat.cut(ms);
			}
		  }
	}
	public static void jump(Player p, String note, int ms){
		 
        for(int x : Convert.midiNums(note)) {
            p.mChannels[0].noteOn(x, 100);
            Beat.cut(ms);
       
      }
      
}
	public static void grow(Player p, ArrayList<String> scale, int ms) {
		
		int baseD = ms/2, baseH = ms*2;
		scale.add(scale.get(0));
		
		
		while(true) {
			
			for(ArrayList<String>  mode : Scale.modes(scale)) {
				Collections.shuffle(mode);
			    jump(p, mode.get(Convert.random(0, mode.size()-1)), ms); 
			    Collections.shuffle(scale);
			   
				for(int i = 0; i < mode.size(); i++) {
					p.mChannels[0].noteOn(Convert.random(0, mode.size()-1), ms); 
					for(int x : Convert.midiNums(mode.get(i))) {
						p.mChannels[0].noteOn(x, 100);
						Beat.cut(i%2==0? ms : i%3==0? baseD : baseH);  
			        } 
			   Collections.rotate(scale, Convert.random(1, scale.size()));
		  }
	    }
	  }
	}
	public static void dream(String keyNote) {
		
		for(String x : Scale.minor(keyNote)) {
			
			Compose.fractalThreads(Scale.pentatonic(x), 1, 1050, 150);
		}
	}
	public static void fractalThreads(ArrayList<String> scale, int cycles, int compounder, int ms){
		                                                                                  /* compounder must be > ms
		                                                                                  or it will slowly create a 
		                                                                                  fork bomb */
		List<Runnable> corners = new ArrayList<Runnable>();
		
		for(ArrayList<String> chord : Chord.keyChords(scale)) {
			corners.add( ()-> Compose.arpeggiatorUp(chord, ms) );
		    corners.add( ()-> Compose.arpeggiatorUp(scale, ms) );
			}
 
		//Collections.shuffle(corners);
		int c = 0;
		while(c < cycles) {

			for(Runnable runnable : corners) {
				new Thread(runnable).start();
			    c++;
				Beat.cut(compounder);
			
		}
		}
	}
	
	public static void phrase(List<String> scale, Map<Integer, Integer> beatMap,int beats, int octave, int ms) {
		try {
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
	        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
	        MidiChannel[] mChannels = midiSynth.getChannels();
	        midiSynth.loadInstrument(instr[0]);
			
	        for(int b = 0; b < beats; b++) {
	        	if(beatMap.containsKey(b)) {
	        		
	        		mChannels[0].noteOn(Convert.midiNums(scale.get(beatMap.get(b)))[octave], 100);
	        	} Beat.cut(ms);
	        }
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}

	
		
	}
	
	
	public static void playNote(String note, int range){
		Player p = new Player();
		p.open();
        
        int mNote = Convert.midiNums(note)[range];
        p.mChannels[0].noteOn(mNote, 100);
        //midiSynth.close();
	}
	
	public static void x (List<String> scale, int loops, int measures, int beats, int ms) 
	throws MidiUnavailableException{
		
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
        
        
		
        for(int l = 0; l < loops; l++) {
        	int left = Convert.random(0, 4);
        	int right = Convert.random(0, 4);
        	if(right == left) {
               leftShift(scale, Convert.random(1, scale.size()));
        	}
			for(int m = 0; m < measures; m++) {
				
				mChannels[0].noteOn(Convert.midiNums(scale.get(0))[Convert.random(2, 5)], 80);
				
			}   for(int b = 0; b < beats; b++) {
				  
				    int note = Convert.random(0, scale.size());
				  
				    mChannels[0].noteOn(Convert.midiNums(scale.get(note))[Convert.random(2,5)],100);
				    Beat.cut(left < right? ms : ms/2);
			}
		}
		
		
		
	}
	
		public static void circScaleTest(String note) throws MidiUnavailableException {
			List<ArrayList<ArrayList<String>>> cir = Modulation.circ5Scales(note);
			System.out.println(cir);
			
			while(true) {
			for(int i = 0; i < cir.size() ; i++){
				if(i==6) i = 0;
				Beat.cut(300);
			    
			    x(cir.get(i).get(1), 2, 8, 16, 300);
			
			}
		}
	}
	
	
	public static void superWind(ArrayList<String> scale, int ms){
		
		List<ArrayList<String>> modes = Scale.modes(scale);
	
		
		Thread bass = new Thread() {
			public void run() {
				modes.forEach( (n) -> { 
			    try {
						Compose.skip(n, 2, 8, 2, ms);
						return;
					} catch (MidiUnavailableException e) {
						     e.printStackTrace();
					}
				});
			}
		};
		
		
		 bass.start();  
	     modes.forEach( (n) -> { 
	    	 
	    	 try {
				Compose.skip(n, 4, 8, 4, ms);
			} catch (MidiUnavailableException e) {
				
				e.printStackTrace();
			}
		
		 });

	
	}
	
	public static int[] varargs(int...whatev) {
		return whatev;
	}
	public static void universalFractal(Player p, int ms, int...prog){
		
	}
	public static void simone(Player p, ArrayList<String> scale, int[] arpeggio, int ms, int...progression){
		 // program 26 midi
        int[] oneThreeFour = {0,2,3};
		//int[] oneThreeFour = {0,2,4,5};
         int count = 0;
		 while(true) {
			 if(count == 4) {
				 Collections.rotate(scale, 2);
				 count = 0;
			 }
	         for(int c : progression) {
	        	
	        	 ArrayList<Integer> mode = Convert.mergeList(Scale.modes(scale).get(c -1), 2);
	        	
	             p.mChannels[0].noteOn(mode.get(oneThreeFour[Convert.random(0,3)]) + 12*(Convert.random(1,3)), 100);
	      
	             for(int i : arpeggio) {
	        		 if (count%2==1 && Convert.random(0, 4) == 2) {
	        			 p.mChannels[0].noteOn(mode.get(oneThreeFour[Convert.random(0, 3)])+ 12*(Convert.random(1, 3)), 100);
	        		 }
	        		 p.mChannels[0].noteOn(mode.get(i), 100);
	        		 //Beat.swing(ms, 3);
	        		 Beat.cut(ms);
	        		 
	        	 }
	         }
	         count++;
		 }
	}
	
	public static void skip(List<String> scale, int measures, int beats, int range, int ms) throws MidiUnavailableException {
		
		
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
	        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
	        MidiChannel[] mChannels = midiSynth.getChannels();
	        midiSynth.loadInstrument(instr[0]);
	        
	   
		    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		
		for(int m = 0; m < measures; m++) {
			
			for(int b = 0; b < beats; b++) {
				Beat.cut(ms);
				if(m==0) {
				   int up = Convert.random(0, 5);
				   int down = Convert.random(0, 5);
				   
				   if(up==down) {
					   mChannels[0].noteOn(Convert.midiNums(scale.get(0))[range], 100);
					   map.put(b, 0);
		               
				}  if(up < down) {
		        	   int index = Convert.random(0, scale.size());
		        	   mChannels[0].noteOn(Convert.midiNums(scale.get(index))[range], 100);
		        	   map.put(b, index);
		              
		       }
				
			}     else {
	                if(map.containsKey(b)) {
	                   mChannels[0].noteOn(Convert.midiNums(scale.get(map.get(b)))[range], 100);
		
	        	   	   	   
		}
		}
		} leftShift(scale, 1); 
		}
		
}  
	
	public static void wind(Player p, String keynote, int ms){
		
		int infinity = (int)Double.POSITIVE_INFINITY;
		
		
		Thread bassLine = new Thread() {
		       public void run(){
		    	  
					Compose.randomPhrase(p, Scale.pentatonic(keynote), infinity, 16, 2, ms);
			        return;
		     }
		   };
		   
		bassLine.start();
		Compose.randomPhrase(p, Scale.minor(keynote), infinity, infinity, 4, ms);
	   
	}
	
	public static void sh(Player p, ArrayList<String> scale, int ms) {
		ArrayList<Integer> s = Convert.mergeList(scale, 3);
		while(true) {
			for(int i : s) {
				p.mChannels[0].noteOn(i, 100);
				int c = Beat.counter;
				Beat.cut(ms);
			}
			Collections.rotate(s, 8);
			
		}
	}
	public static void echoChamber(List<String> scale, int ms){
		
		    Player p = new Player();
	        Map<Integer, String> repeat = new HashMap<Integer, String>();
            
            int b = 0;
            int counter = 1;
            
            while(true){
                   
            	if(counter%4==0) {
                	 leftShift(scale, 2);
            		//Collections.shuffle(scale);
                  }
                  counter++;
                  Beat.cut(ms);
                   
                   int up = Convert.random(0,4);
                   int down = Convert.random(0,4);
                  
                   if(up == 0 && down == 0) { 
                	   b = 0;
                   
                 } if(up==down){
                       p.mChannels[0].noteOn(Convert.midiNums(scale.get(0))[b%2==0? 3 : 2], 100);
                       repeat.put(b, scale.get(0));
                       b++;
                 } if(up > down){
                       repeat.put(b, scale.get(Convert.random(0, scale.size())));
                       p.mChannels[0].noteOn(Convert.midiNums(scale.get(Convert.random(3,5)))[3], 100); // [3]
                       p.mChannels[0].noteOn(Convert.midiNums(repeat.get(b))[3], 100);
                    
                
                 } if(repeat.containsKey(b)){
                      p.mChannels[0].noteOn(Convert.midiNums(repeat.get(b))[3], 100);  //[3]
              }
         } 
    }
	

	
	
	public static <T> void arpeggiatorUp(ArrayList<String> chord,  int ms) {
		  
		 Player p = new Player();
		 p.open();
	        List<Integer> nums = Convert.merge(chord);
	        
	        for(int i = 8; i < nums.size()-6; i++) { 
	            //Beat.cut(ms);
	        	p.mChannels[0].noteOn(nums.get(i), 100);
	            Beat.cut(ms);
	        }
		 
		  return;
	}
	
	public static void arpeggiatorDown(ArrayList<String> chord,  int ms){
		
		try {
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
	        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
	        MidiChannel[] mChannels = midiSynth.getChannels();
	        midiSynth.loadInstrument(instr[0]);
	    
	        List<Integer> lst = Convert.merge(chord);
	        
	        for(int i = lst.size()-8; i >= 8; i--) {
	        	Beat.cut(ms);
	        	mChannels[0].noteOn(lst.get(i),  100);
	        	
	        }
	        
		} catch (MidiUnavailableException e) {
			
			e.printStackTrace();
		}
		        
	    return;  
	}
        
public static void randomPhrase(Player p, List<String> scale, int measures, int beats, int range, int ms){
	
	    
	    Map<Integer, String> repeat = new HashMap<Integer, String>();
	
		for(int m = 0; m < measures; m++){
	        
			for(int b = 0; b < beats; b++){
				Beat.cut(ms);
			   
                if(m == 0) {
				  int up = Convert.random(0, 4);
				  int down = Convert.random(0, 4);
				
				if(up==down) {
				   p.mChannels[0].noteOn(Convert.midiNums(scale.get(0))[range], 100);
				   repeat.put(b, scale.get(0));
	          
				}  
				
				
				if(up < down) {
	        	   int index = Convert.random(0, scale.size());
	        	   p.mChannels[0].noteOn(Convert.midiNums(scale.get(index))[range], 100);
	        	   repeat.put(b, scale.get(index));
	           }
				
	   } else {
		   if(repeat.containsKey(b)) {
			   p.mChannels[0].noteOn(Convert.midiNums(repeat.get(b))[range], 100);
		}
	    }
	    }
	    }    
		
	  
}

public static void fork(List<String> scale, int measures, int beats, int shift, int ms)	{
	
	
	new Thread() {
		public void run() {
			for(List<String> mode : Scale.modes((ArrayList<String>) scale)) {
		    	
		    	randomPhraseEnhanced(mode, measures, beats, shift, ms);
		}
	}
	}.start();
	new Thread() {
		public void run() {
			for(List<String> mode : Scale.modes((ArrayList<String>) scale)) {
		    	
		    	randomPhraseEnhanced(mode, measures, beats, shift, ms);
		}
	}
	}.start();
	
}

public static void rpeEvolved(List<String> scale, int measures, int beats, int shift, int ms){ // try 2-3 concurrent threads, all running this!!!
	
	
    
    for(String note : scale) {
    	
    	randomPhraseEnhanced(Scale.minor(note), measures, beats, shift, ms);
    	
    }
	
}

public static void randomPhraseEnhanced(List<String> scale, int measures, int beats, int shift, int ms) {
	
	Player p = new Player();
	p.open();
	
	   
	    
	
	
	Map<Integer, String> repeat = new HashMap<Integer, String>();
	
	for(int m = 0; m < measures; m++){
       
		for(int b = 0; b < beats; b++){
			Beat.cut(ms);
		    
			int range = Convert.random(2, 5);
			
			if(m==0) {
			int up = Convert.random(0, 4);
			int down = Convert.random(0, 4);
			
			if(up==down) {
			   p.mChannels[0].noteOn(Convert.midiNums(scale.get(0))[range], 100);
			   repeat.put(b, scale.get(0));
          
			}  
			
			
			if(up < down) {
        	   int index = Convert.random(0, scale.size());
        	   p.mChannels[0].noteOn(Convert.midiNums(scale.get(index))[range], 100);
        	   repeat.put(b, scale.get(index));
           }
			
   } else {
	   if(repeat.containsKey(b)) {
		   p.mChannels[0].noteOn(Convert.midiNums(repeat.get(b))[range], 100);
	}
    } leftShift(scale, shift);
    }
    }    
	
	 
	
}



public static void cir5tst2(String note) throws MidiUnavailableException {

   
		
		
        int[] prog = { 0, 3, 4};
        List<String> scale = Scale.major(note);
        List<ArrayList<String>> key = Chord.keyChords(Scale.minor(note));
        
        
        for(int n = 0; n < scale.size(); n++ ) {
        	scale = n < 6? Scale.minor(note) : Scale.major(note);
        	
        		rightShift(scale, 1);
        		
        	
             key = n==0||n==3||n==4 ? Chord.keyChords(Scale.major(scale.get(n))) :
                   Chord.keyChords(Scale.minor(scale.get(n)));
        	for(int j = 0; j < prog.length; j++) {
        	    
        		
        	
        	    arpeggiatorDown(Modulation.secondaryDominant(key.get(prog[j])), 120);
        	   
        	    
        	    arpeggiatorUp(key.get(prog[j]), 100);
            
        	
        		
        }
	        
        } 
        
	
}
public static void playChord(ArrayList<String> chord, int howManyTimes, int ms) {
	try {
   
		
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
        
        mChannels[0].programChange(0);
        
        for(int i = 0; i < howManyTimes; i++) {
        	
            for(String x : chord) {
               
        	    mChannels[0].noteOn(Convert.midiNums(x)[3], 100);
        	    
                
        } Beat.cut(ms);   
            
        }
      } catch(MidiUnavailableException e) {
         e.printStackTrace();
      }   
}
   public static void somethingNew(Player p, ArrayList<String> scale, int ms, int...progression){
	   ArrayList<ArrayList<String>> chords = Chord.keyChords(scale);
	   
	   while(true){
	      for(int i : progression){
	    	  new Thread(()-> randomPhrase(p, chords.get(i-1), 1, 12, 1, ms)).start();
		      new Thread(()-> randomPhrase(p, chords.get(i-1), 1, 12, Convert.random(2,5), ms)).start();
		      randomPhrase(p, Chord.augment(chords.get(i-1), 1), 1, 12, Convert.random(2, 5), ms);
	      }
       }
	   
   }
   public static void rPhrase(Player p, ArrayList<String> scale, int beats, int ms) {
	   
   }
   public static void russian(Player p, ArrayList<String> scale, int ms, int...progression){
	   int low = -1;
	   int high = 2;
	   int ind = 0;
	   while(true) {
		  
		   for(int i : progression) {
			   int outBeat = 1;
			   List<Integer> l = Convert.mergeList(Scale.modes(scale).get(i-1), 2);
			   while(outBeat <= 8) {
				   if(4%outBeat==0) {
				     p.mChannels[0].noteOn(l.get(0)+(12*high), 100);
				   }
				   if(outBeat ==1 || outBeat == 5) {
					   p.mChannels[0].noteOn(l.get(0) + (12*low), 100);
				   }
				   if(outBeat == 3 || outBeat == 7) {
					   p.mChannels[0].noteOn(l.get(4) + (12*low), 100);
				   }else {
					   p.mChannels[0].noteOn(l.get(ind++%7), 75);
				   }
				   
				  
				   Beat.swing(ms,Beat.counter%2==1? 2 : ms+1);
				   outBeat++;
				   
			   }
			   
			   
		   }
		   
	   }
   }

   public static void circ5Test(Player p, String note) {
	
        ArrayList<String> chord;
        List<ArrayList<String>> cir = Modulation.circleOfFifths(note);
        p.mChannels[0].programChange(88);
        
        int changer =0;
        for(int j = 5; j < 144; j++) {
        	  
        	for(int i = 0; i < cir.size(); i++) {
        	        changer++;
        	        
        		if(i== 6) i = 0;
        		   
        		   for(int t = 0; t < 2; t++) {
        		   
        		   chord = changer <12 || changer > 18? Chord.major(cir.get(i).get(0)):
        			                                    Chord.minor(cir.get(i).get(1));
        		   
        		   octaveJump(p, chord.get(t+1), 100);
        		   p.mChannels[0].allNotesOff();
        		   p.mChannels[0].noteOn(Convert.midiNums(chord.get(0))[i%2==1? 2: 3], 75);
        		   p.mChannels[0].noteOn(Convert.midiNums(chord.get(1))[i%2==1? 3: 2], 75);
        		   p.mChannels[0].noteOn(Convert.midiNums(chord.get(2))[i%2==1? 3: 2], 75);
        		   Beat.cut(600);
        		   p.mChannels[0].noteOn(Convert.midiNums(chord.get(0))[1], 200);
        		   
        		   Beat.cut(i%2==0? 150: 600);
        		  
        		}	
        
            
        
      } 
      } 

}

    public static void c(String note) {
	
        try {
   
		
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
        
        List<String> chord = Scale.major(note);
        List<ArrayList<String>> cir = Modulation.circleOfFifths(note);
        
        for(int j = 0; j < 144; j++) {
           
        	leftShiftNest(cir, j%4==0? 10 : 1);
        	for(int i = 0; i < chord.size(); i++) {
        	   Beat.cut(360);
        	   
        	   
        	   
        	   chord = j%4==0? Chord.min7(cir.get(11).get(1)) : Chord.maj7(cir.get(0).get(0));
        	   chord = Modulation.invert(chord).get(i);
        	  
        	  // mChannels[0].allNotesOff();	
        	  
        	   mChannels[0].noteOn(Convert.midiNums(chord.get(0))[i==1? 1 : 2], 75);
        	   Beat.cut(j%2==0? 120:0);
     		   mChannels[0].noteOn(Convert.midiNums(chord.get(1))[i==2? 2 : 3], 75);
     		   Beat.cut(120); 
     		   mChannels[0].noteOn(Convert.midiNums(chord.get(2))[i==3? 3 : 4], 75);
     		   Beat.cut(j%2==0? 120:0);
     		   mChannels[0].noteOn(Convert.midiNums(chord.get(3))[Convert.random(0, 6)], 75);
        	  /*
        	   mChannels[0].noteOn(Convert.midiNums(chord.get(0))[Convert.random(3, 5)], 75);
        	   Beat.cut(j%2==0? 120:0);
     		   mChannels[0].noteOn(Convert.midiNums(chord.get(1))[Convert.random(3, 5)], 75);
     		   Beat.cut(120); 
     		   mChannels[0].noteOn(Convert.midiNums(chord.get(2))[Convert.random(2, 5)], 75);
     		   Beat.cut(j%2==0? 120:0);
     		   mChannels[0].noteOn(Convert.midiNums(chord.get(3))[Convert.random(2, 5)], 75);
     		   
     		   */
     		   
        	}
        	}
        		
      } catch(MidiUnavailableException e) {
         e.printStackTrace();
      }   

}
	
    	
  

	
    public static void prog(String note) throws MidiUnavailableException {
             
	
		
		
        int[] prog = {0, 5, 3, 4};
        ArrayList<String> min = Scale.major(note);
        List<ArrayList<String>> cho = Chord.keyChords(min);
        while(true) {
        for(int whole = 1; whole < 144; whole++) {
        	
        	for(int quarter = 1; quarter <=4; quarter++) {
        		
        		for(int i = 0; i < 4; i++) {
        		    playChord(Chord.randomAugment(cho.get(i)), 3, 220);
        		   Beat.cut(400);
        		}
        		}
            }
        }
    }
	
	   
	

	
	
	public static void monet(Player p, String startNote) {
		
		
	    
	   ArrayList<ArrayList<String>> x =  Chord.keyChords(Scale.minor(startNote));
	  
	   for(int j = 0; j < 36; j++) {
		
	    for(List<String> chord : x) {
	    	
	    	for(int i = 0; i < 3; i++) {
	    		
	    	    p.mChannels[0].noteOn(Convert.midiNums(Modulation.invert(chord).get(2).get(i))[j%5==0? 5: i%2==1? 4 : j%3==0? 2 : j%4==0? 5 : i+1], 50);
		         
		       // [j%2==0? i+3: i%2==1? i+4 : j%3==0? 6 : j%4==0? 5 
		      Beat.cut(j%3==0? 150 : j%2==0 ? 300 :  j*15); 
	        
	    	}
	        } 
	   }
	     
	}
		
		
		  
	public static void weird(Player p, String note) {  // best in f# or g#
		
	    
	   p.mChannels[0].programChange(46);
	  
	   for(int j = 0; j < 360; j++) {
		  
		 p.mChannels[0].noteOn(Convert.midiNums(Scale.pentatonic(note).get(j%Scale.pentatonic(note).size()))[1], 100);
	     for(List<String> chord : Chord.keyChords(Scale.pentatonic(note))) {
	    	 
	    	for(List<String> inverse : Modulation.invert(chord)) {
	    	  int x = j < 23? 3 : 1;
	    	  for(int i = 0; i < x; i++) {
	    		 // mChannels[0].allNotesOff();
	    		  if(i==2) {
	    		   p.mChannels[0].noteOn(Convert.midiNums(note)[j%2==0? 2 : j%3==0? 4 : 3], 100);
	    		  }
	    	       p.mChannels[0].noteOn(Convert.midiNums(inverse.get(i))[j%2==0? 2 : i==2? 4 : 6], 100);
		         
		           Beat.cut(j%2==0 || i==1? 250 : j%3==0 || i==3? 50 : 0); 
	        
	    	}
	        } 
	        }
	        }
	 }
	 static void experiment() {
		 
		 Player p = new Player();
		 p.open();
		 
	     while(true) {
	    	 for(List<String> modes : Scale.modes(Scale.harmonicMin("D"))) {
	    		 modes.add(modes.get(0));
	    		 new Thread() {
	    	     public void run() {
	    		 for(String note1 : modes) {
	    			  p.mChannels[0].noteOn(Convert.midiNums(note1)[3], 100);
	    			  
	    		      Beat.cut(800);
	    		      p.mChannels[0].noteOn(Convert.midiNums(note1)[Convert.random(1,6)], 100);
	    		      Beat.cut(400);
	    		      
	    		 }
	    		 }
	    		 }.start();
	    		 Beat.cut(3200);
	    	 }
	     }
	      
		 
	 }
	public static void dandy(Player p, String key, boolean ringOut, int degree, int ms) {
	
		ArrayList<String> scale = Scale.major(key);
		
		while(true) {

			ArrayList<Integer> nums = Chord.selective(scale, 2, 1,3,5,8);
			if(ringOut)p.mChannels[0].allNotesOff();
			for(int i = 0; i < nums.size(); i++) {
				p.mChannels[0].noteOn(nums.get(Convert.random(0, nums.size()))+12, 70);
				
				Collections.rotate(nums, 2);
				Beat.cut(ms);
				p.mChannels[0].noteOn(nums.get(i), 100);
				
				Beat.cut(ms);
				Collections.rotate(nums, 2);
				p.mChannels[0].noteOn(nums.get(Convert.random(0, nums.size()))+12, 70);
			
				Beat.cut(ms);
				p.mChannels[0].noteOn(nums.get(i), 100);
				
			}
			scale = Scale.major(scale.get(degree-1));
	
		}
	}
    public static void ok(String startNote) {
		
        try {
		
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        
        
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        
        midiSynth.loadInstrument(instr[0]);
    
        
  
       for(int j = 0; j < 250; j++) {
        for(List<String> chord : Chord.keyChords(Scale.pentatonic(startNote))) {
    	  for(int i = 0; i < chord.size(); i++) {
    		 
    		if(j%2==0 || j%5==0) {
    			 
    			mChannels[0].programChange(9);
    			mChannels[0].noteOn(Convert.midiNums(Scale.pentatonic(startNote).get(i+5%3))[j%2==0? 4: 6], 50);
    		}
    	    
    		mChannels[0].noteOn(Convert.midiNums(Modulation.invert(chord).get(2).get(i))[j%5==0? 5: i%2==1? 4 : j%3==0? 2 : j%4==0? 5 : i+1], 50);
	        if(j%2==1) {
    		  mChannels[0].noteOn(Convert.midiNums(Modulation.invert(chord).get(0).get(2))[6],50);
	        }
       //j%3==0 && j%5==0? 150 : j%3==0 ? 300 :  j*15 ** save this
	      
	      Beat.cut(j%3==0 && j%5==0? 150 : j%3==0 ? 300 : i==3? 500 : j > 20? j : j*15); 
       
    	}
        }
        }
   
      } catch(MidiUnavailableException e) {
         e.printStackTrace();
      }   
   
	}
public static void form(Player p, String note) {

    p.mChannels[1].programChange(67);
  
    
    List<ArrayList<String>> c = Chord.keyChords(Scale.minor(note));
    
	for(int j = 0; j < 36; j++) {
		   
	    for(List<String> chord : c) {
	    	
		    for(List<String> inverse : Modulation.invert(chord)) {
		    	octaveJump(p,inverse.get(2), 50);
		        for(String x : inverse) {
		        	
		        	for(int beat = 0; beat < 8; beat++) {
		        
		        	    p.mChannels[0].noteOn(Convert.midiNums(x)[beat%2==0? 2 : beat%3==0? 5:  4], 50);
		                 for(String y : Scale.minor(note)) {
		                	 p.mChannels[0].noteOn(Convert.midiNums(y)[beat%2==0? 2 :4], 50);
		                	 
		                	 Beat.cut(beat%2== 1? 100 : 20); 
	                	   
	                 
		  
	       
         
    
      
      try { Thread.sleep(beat%2==1? 25: 200); 
    } catch( InterruptedException e ) {
        e.printStackTrace();
    }  
	}
    } 
    }
    }
    }
    }
 
}

    public static void exp(String note1) {
        try {
		
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
    
        mChannels[0].programChange(0);
   
   for(int j = 0; j < 144; j++) {
	   System.out.println(j);
       for(List<String> chord : Chord.keyChords(Scale.pentatonic(note1))) {
    	   for(int i = Chord.keyChords(Scale.pentatonic(note1)).size()-1; i >=0; i--) {
    		   if(i%3==0)
    		   mChannels[0].noteOn(Convert.midiNums(Scale.pentatonic(note1).get(1))[j%2==0? 3 : 1], j%2==0? 50 : j%3==0? 25 : 12);
    		  
    		   for(List<String> inverse : Modulation.invert(chord)) {
    			   for(String note : inverse) {
    				   mChannels[0].noteOn(Convert.midiNums(note)[i%4==0? 2 : 5], j%2==0? 50 : j%3==0? 75 : 25);
    			       
    		   }mChannels[0].noteOn(Convert.midiNums(chord.get(j%2==0? 0 : j%3==0? 1 : 2))[i%5==0? 6 : i%3==0? 4 : 3], i%2==0? 50: 12);
    	    
	      try { Thread.sleep(j%5==0? 400 : i%3==0 || j%2==0? i*75 : 300); 
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }
    	}
        }
        }
        }
   
      } catch(MidiUnavailableException e) {
         e.printStackTrace();
      }

}

 public static void evenRythm(Player p, String startNote) { 
	 
	         
	       
	       int x = 0;
	       int y = 360; //this is the base rhythm
	       for(int whole = 1; whole <7; whole++) {
	    	   if(whole==5) whole=1;
	    	   octaveJump(p,Scale.pentatonic(startNote).get(whole/2),  y/2+1);
		       for(int half = 1; half <=2; half++) {
	    		   p.mChannels[0].noteOn(Convert.midiNums(startNote)[5], 75);
		    	   for(int quarter = 1; quarter <=2; quarter++) {
		    		   try { Thread.sleep(quarter==1? y : y/3); 
		   	        } catch( InterruptedException e1 ) {
		   	            e1.printStackTrace();
		   	        }
		    		   p.mChannels[0].noteOn(Convert.midiNums(startNote)[quarter==1? 5: 4], 50);
		    		   for(int eighth = 1; eighth <=2; eighth ++) {
		    			   if(x >= 5) x = 0; // make it x>= a mult of 2 for evenness in phrase
		    			   p.mChannels[0].noteOn(Convert.midiNums(Scale.pentatonic(startNote).get(x))[eighth == 1? 3 : 2], eighth == 1? 50 : 75);
		    	           x++;
		                   Beat.cut(eighth ==1? y : y/3); 
	        
	        }
	        }
	        } 
	        }
	     
 }   
  
 public static void octaveJump(Player p, String note, int ms){
	 
		   
		    
		
	        p.mChannels[4].programChange(0);        // 46
	        for(int x : Convert.midiNums(note)) {
	        	//mChannels[4].allNotesOff();
	            p.mChannels[4].noteOn(x, 50);
	       
		        Beat.cut(ms);
	       
	      }
	      
 }
 
     public static void shuffle(Player p, List<String> x){
	 
	 
		 List<String> scale = new ArrayList<String>();
		 for(String note : x) {
			 scale.add(note);
		 }
		 
		 for(String note : scale) {
			   
			   for(int i = 0, j = scale.size()-1; i < scale.size(); i++, j--) {
				   
				 
				   octaveJump(p,scale.get(j), i*25);
				   try { Thread.sleep(450/(i+1)); 
			        } catch( InterruptedException e ) {
			            e.printStackTrace();
			        }
				   octaveJump(p, note,  i*50);
				   try { Thread.sleep(112/(i+1)); 
			        } catch( InterruptedException e ) {
			         // octaveJump(scale.get(Math.abs(j-i)), 5, i*75);
				
				   
			   }//octaveJump(scale.get(Math.abs(j-i)), 5, i*75);
			   } 		 
	}
	}
     
    public static void pivot(Player p, ArrayList<String> scale, int octave, int ms, int...prog) {
    	scale.add(scale.get(0));
    	int[] cuts = {1,2,4};
    	while(true) {
	    	for(int i : prog) {
	    	    octave = Convert.random(2,4);
	    		ArrayList<Integer> mode = Convert.mergeList(Scale.modes(scale).get(i-1), octave); 
	    		int pivot = 7;
	    		int move = 6;
	    		int x = ms / cuts[Convert.random(0,2)];
	    		for(int n = 0; n < 6; n++) {
	    			p.mChannels[0].noteOn(mode.get(pivot), 100);
	    			Beat.cut(x);
	    			p.mChannels[0].noteOn(mode.get(move), 100);
	    			Beat.cut(x);
	    			move--;
	    			
    		    }
    			
    	}
    	
      }
    	
    }
   //*************************************************************************************************************** 
   public static void phr(Player p, ArrayList<String> scale, int[] arp, int octave, int ms){
	   
	   ArrayList<Integer> midi = Convert.mergeList(scale, octave);
	   int size = midi.size();
	   
	   for(int i : arp){
		   if(i >= size) {
			   midi.add(midi.get(i%size)+12);
		   }
		   p.mChannels[0].noteOn(midi.get(i),100);
		   Beat.cut(ms);
	   }
   }
   public static void userIN(Player p, ArrayList<String> scale, int ms) {
	   scale.add(scale.get(0));
	   Scanner s = new Scanner(System.in);
	   while(true) {
		   if(s.hasNextInt()) {
			   int index = s.nextInt();
        	   scale = Scale.modes(scale).get(index);
        	   }
		   ArrayList<Integer> midi = Convert.mergeList(scale, 2);
           for(int i : midi) {
        	   p.mChannels[0].noteOn(i, 100);
        	   Beat.cut(ms);
        	
        	   
           }
	   }
	   
   }
   public static void phrProg(Player p, ArrayList<String> scale, int ms, int...prog){
	   ArrayList<ArrayList<String>> modes = Scale.modes(scale);
	   int [] arp =  new int[] {4,2,0,2,4,2} ;
	   
	   new Thread() {
		 @ Override
		 public void run() {
			 for(int i : prog) {
				   phr(p, modes.get(i-1), arp, 3, ms);
			   }
		 }
	   }.start();
	  
	   Beat.cut(ms * 3);
	  
	   for(int i : prog) {
		   phr(p, modes.get(i-1), arp, Convert.random(3, 5), ms/Convert.random(1, 3));
	   }
   }
   public static void allTogetherNow(Player p, ArrayList<String> scale, int ms, int...prog) {
	  
	   while(true) {
		  
		   phrProg(p, scale, ms, prog);
		   
	   }
   }
   
   //***************************************************************************************************************
   public static void testRs(String note){
	   
			Player p = new Player();
			p.open();
	        
	        List<String> scale = Scale.minor(note);
	        
	        for(int outer = 0; outer < 144; outer++) {
	        	
	        	
	        	p.mChannels[1].noteOn(Convert.midiNums(scale.get(outer%scale.size()))[3], 50);
	            if(outer > 24) leftShift(scale, 6);
	        	p.mChannels[0].noteOn(Convert.midiNums(note)[2], 50);
	        	
	            for(int mid = 0; mid < 4; mid++) {
	            	rightShift(scale, 6);
	                
	            	p.mChannels[0].noteOn(Convert.midiNums(scale.get(mid))[mid%2==0? 2:3], 50);
	            	for(int inner = 0; inner < 4; inner++) {
	            		
	            		p.mChannels[0].noteOn(Convert.midiNums(scale.get(inner))[inner%2==0? 4:5], 50);
	         Beat.cut(mid%2==0 ? 300 : 150);   		
	        }
	        }
	        }       
	     
 }
   
   
   public static void four4(int base) throws MidiUnavailableException {
	  
			Player p = new Player();
			p.open();
	        
	        for(int whole = 1; whole < 144; whole++) {
	        	p.mChannels[9].noteOn(36, 100);
	        	//Beat.cut(base);
	        	for(int half = 1; half <= 2; half++) {
	        		
	        		p.mChannels[9].noteOn(36, 100);
	        		p.mChannels[0].programChange(0);
	        		for(int quarter = 1; quarter <=2; quarter++) {
	        			p.mChannels[9].noteOn(38, 100);
	        			for(int eighth = 1; eighth <= 2; eighth++) {
	        				
	        				
	        				for(int sixteenth = 1; sixteenth <= 2; sixteenth++) {
	        					p.mChannels[9].noteOn(42, 100);
	        					
	        					
	        					Beat.cut(base);
	        					
	        				}
	        			}
	        		}
	        	}
	        }
	        
   }
  
   public static void eightBeetz(int base) throws MidiUnavailableException {
	   Player x = new Player();
	   x.open();
	   
      while(true) {
       
    	  for(int outer = 0; outer < 8; outer++) {
    		  x.mChannels[9].noteOn(45, 300);
    		  for(int mid = 0; mid < 8; mid++) {
    			  int r = Convert.random(0, 2);
    			  
    			  
    			  x.mChannels[9].noteOn(38, 100);
    			  for(int inner = 0; inner<8; inner++) {
    				  if(inner == r) {
        				  x.mChannels[9].noteOn(46, 100);
    				  }
    				  x.mChannels[9].noteOn(42, 100);
    				  Beat.cut(base/2);
    			  }
    		  }
    	  }
    	
       }
   }

   public static Map<Integer, Integer> beatMap(int beats, int range){
	   
	   Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	   for(int i = 0; i < beats; i++) {
		   int x = Convert.random(0,4);
		   int y = Convert.random(0, 4);
		   if(x%2==0) {
			   map.put(i, Convert.random(0, range+1));
		   }
		   if(x==y) {
			   map.put(i, 0);
		   }
		   
	   } return map;
   }
   public static List<String> rightShift(List<String> scale, int count ) {
	  int x = scale.size();
	  for(int i = 0; i < count; i++) {
		  scale.add(0, scale.get(x-1));
		  scale.remove(x);
	  }
	  return scale; 
   }
   public static List<String> leftShiftList(List<String> scale, int count) {
		  for(int i = 0; i < count; i++) {
			  scale.add(scale.get(0));
			  scale.remove(0);
		  } return scale;
	   } 
   
   public static <T> List<T> leftShift(List<T> scale, int count) {
	  for(int i = 0; i < count; i++) {
		  scale.add(scale.get(0));
		  scale.remove(0);
	  } return scale;
   } 
   public static ArrayList<Runnable> listChange(ArrayList<Integer> scale) {
	   
	   ArrayList<Runnable> list = new ArrayList<>();
	   list.add(()-> Collections.rotate(list, 1));
	   
	   return list;
	   
   }
   public static void intervalTest(Player p, List<Integer> scale, int ms) {
	   int interval = 1;
	   
	   for(int x = 0; x < scale.size(); x++) {
		   interval += x;
		   for(int i = 0; i < scale.size(); i++) {
			   p.mChannels[0].noteOn(scale.get(i%scale.size())-24, 100);
			   p.mChannels[0].noteOn(scale.get((i+interval)%scale.size()), 100);
			   Beat.cut(ms);
		   }
	   }
   }
   public Map<Integer, List<Integer>> Musikalisches_W??rfelspiel(ArrayList<String> scale, int beats, int tempo){
	   Map<Integer, List<Integer>> map = new HashMap<>();
	   for(ArrayList<String> mode : Scale.modes(scale)) {
		   
	   }
	   return map;
   }
   public static void zoo(Player p, ArrayList<String> scale, int ms, int compounder, int octave, int...prog) {//compounder keep it below 6

	   while(true) {
	       for(int c : prog) {
	    	   
	    	   ArrayList<Integer> chord = Convert.mergeList(Chord.augment(Chord.keyChords(scale).get(c-1), 2), octave);
	    	   ArrayList<Integer> copy = new ArrayList<>(chord);
	    	   ArrayList<Integer> copy2 = new ArrayList<>(chord);

	    	   
	    	   new Thread() {
	    		   
	    		   public void run() {
	    	    	   recursiveRandomDropPush(p, chord , 4, ms);
                   }
	    	   }.start();
	    	   
	    	   Beat.cut(ms*compounder);
	    	  
	    	   new Thread() {
	    		  
	    		   public void run() {
	    	    	   recursiveRandomDropPush(p, copy , 4, ms);
	    	       }
	    	   }.start();
	    	 
	    	   Beat.cut(ms*compounder);
	    	   recursiveRandomDropPush(p, copy2 , 3, ms);
		       
	       }
	       
	  }
   }
   public static void keepSkipping(Player p, ArrayList<String> chord, int ms) {
	  ArrayList<Integer> midi = new ArrayList<>(Convert.merge(chord));//.subList(4, 20));
	  
	  int compounder = 1;
	  while(true) {
		  int start = 0;
		  for(int i = 0; i < 16; i++) {
              p.mChannels[0].noteOn(midi.get(start % midi.size()), 100);
			  Beat.cut(ms);
			  start += compounder;
		  }
		  compounder++;
	  }
   }
   
   public static List<ArrayList<String>> leftShiftNest(List<ArrayList<String>> cir, int count){
	   for(int i = 0; i < count; i++) {
		   cir.add(cir.get(0));
		   cir.remove(0);
	   } return cir;
   }
   //********************************************************************
   public static ArrayList<Note>testNoteObj(ArrayList<String> scale) {
	   ArrayList<Note> scala = new ArrayList<>();
	   for(String note : scale) {
		   scala.add(new Note(note, 3, 250));
	   }
	   return scala;
   }
   public static void p(Player p, ArrayList<Note> scale) {
	   while(true) {
		   for(Note n : scale) {
			   p.mChannels[0].noteOn(n.midi, 100);
			   n.setOctave(4);
			   Beat.cut(n.duration);
			   
		   }
	   }
   }
   //*********************************************************************
   public static List<Integer> recursiveRandomDropPush(Player p, List<Integer> chord, int count, int ms){
        p.mChannels[0].noteOff(ms);
        
        if(count == 0){
          return null;
       }
       int drop = Convert.random(0, chord.size());
       if(Convert.random(0, 2) == 1) {
    	   chord.add(0, chord.get(drop)-12);
    	   chord.remove(drop+1);
       }
       else {
    	   chord.add(chord.get(drop)+12);
    	   chord.remove(drop);
       }
       for(int note : chord){
    	   
           p.mChannels[0].noteOn(note, 100);
           Beat.cut(ms);
          
       }
       
       return recursiveRandomDropPush(p, chord, count-1, ms);
       
}
   public static void testGraph(Player p, String key, int ms) {
	   
	   Progression pro = new Progression(key, "major");
	  
	   while(true) {
		   int r = Convert.random(0, pro.prog.size());
		   while(r >= 0) {
			   
			   ArrayList<String> ref = pro.prog.get(Convert.random(0, pro.prog.size())).chord;
			   ArrayList<Integer> cho = ref.size() ==  4 ? Convert.mergeList(ref, 4) :
				                                           Convert.mergeList(Chord.randomAugment(ref), 3);
					                                    // Convert.mergeList(Chord.enrich(ref, 1), 4);
			   
			   Runnable run = ()->  p.mChannels[0].noteOn(cho.get(Convert.random(0, 3))-24, 100);
			  
			   ArrayList<Integer> dup = new ArrayList<Integer>(cho);
			   new Thread(run).start();
			   Collections.shuffle(dup);
			   new Thread() {
			       public void run() {
				       Compose.recursiveDrop(p, dup, 4, 2, 3, ms*8);
			       }
		       }.start();
			   new Thread(run).start();
			   Compose.recursiveDrop(p, cho, 8, 2, 3, ms*4);;
			   p.mChannels[0].allNotesOff();
			   
			   r = pro.prog.get(r).paths.get(Convert.random(0, pro.prog.get(r).paths.size()));

		   }
		  
			   
		   }
		   
		   
   }
   
   public static void oneFour5(Player p, ArrayList<String> scale, int ms, int...prog) {
	   
	   while(true) {
		   for(int c : prog) {
			   p.mChannels[0].allNotesOff();
			   ArrayList<Integer> midi = Convert.mergeList(Scale.modes(scale).get(c-1), Convert.random(1, 3));
			   ArrayList<Integer> midi2 = new ArrayList<Integer>();
			   
			   midi2.add(midi.remove(6)+12);
			   midi2.add(midi.remove(5)+12);
			   midi2.add(midi.remove(2)+12);
			   midi2.add(midi.remove(1)+12);
			   midi2.add(midi2.get(0)+12);
			   
			   Runnable r = ()-> Fractal.spread(p, midi, 0, 4, ms);
			   new Thread(r).start();
			   Fractal.spread(p, midi2, 1, 8, ms/2);
			   			   
			   
		   }
		   Collections.rotate(scale, 2);
	   }
   }
   public static void intervalJump(Player p, ArrayList<String> scale, int ms, int...prog) {
	  
	   while(true) {
		    //int interval = Convert.random(0, 14);
	        for(int c : prog) {
	        	int interval = Convert.random(0, 14);
	        	
	        	ArrayList<Integer> midi = Convert.mergeList(Scale.modes(scale).get(c-1), Convert.random(1, 5));
	        	ArrayList<Integer> midi2 = Convert.mergeList(Scale.modes(scale).get(c-1), Convert.random(1, 5));
	           
	            midi.add(midi.get(0)+12);
	            midi2.add(midi2.get(0)+12);
	            
	           
	        	int val = interval;
	        	Runnable r = ()-> Fractal.spread(p, midi, val, 8, ms);
	            
	        	new Thread(r).start();
	        	
                Fractal.spread(p, midi2, interval/2, 12, ms/2);
	        }
	      
       }
   }
   public static void compoundProg(Player p, ArrayList<String> scale,  int ms, int...prog) {
	   for(int c : prog) {
		   for(ArrayList<String> chord : Chord.keyChords(Scale.modes(scale).get(c-1))){
			   ArrayList<Integer> midi = Convert.mergeList(chord, 3);
			   for(int note : midi) {
				   p.mChannels[0].noteOn(note, 75);
				   Beat.cut(ms);
			   }
		   }
	   }
	   return;
   }
   public static void rotateProg(Player p, ArrayList<String> scale, int ms, int...prog) {
	   ArrayList<Integer> midi = Convert.mergeList(scale, 3);
	   int index = 1;
	   while(true) {
		   for(int chord : prog) {
			   index = chord - index;
			   System.out.println(index);
			   Collections.rotate(midi, index);
			   for(int i : midi) {
				   p.mChannels[0].noteOn(i, 100);
				   Beat.cut(ms);
			   }
		   }
   	   }
   }
   
   public static void makeMusic(Player p, ArrayList<String> scale, int ms) {
	   Map<Integer, Integer> beatMap = new HashMap<>();
	   while(true) {   
	   	   for(int i = 0; i < 12; i++) {
			   if(Convert.random(0,2)==1) {
				   beatMap.put(i, Convert.midiNums(scale.get(i%scale.size()))[Convert.random(1, 4)]);
			   }
			   
		   }
	   	   int b = 4;
	   	   while(b > 0) {
	   		   for(int m = 0; m < 12; m++) {
	   			   if(beatMap.containsKey(m)) {
	   				 p.mChannels[0].noteOn(beatMap.get(m), 100);
	   			   }
	   			   Beat.cut(ms);
	   		   } b--;
	   	   }
	   }
   }
   //****************************************************************************************************************
   public static Map<Integer, ArrayList<ArrayList<String>>> modeSubs(ArrayList<String> scale){
			  Map<Integer, ArrayList<ArrayList<String>>> modeSubs = new HashMap<>();
			  int index = 0;
			 
			  for(ArrayList<String> mode : Scale.modes(scale)){
			       
			  modeSubs.put(index, new ArrayList<ArrayList<String>>());
			  modeSubs.get(index).add(mode);
			  modeSubs.get(index).add(index == 0 || index == 3 || index == 4 ? Scale.major(mode.get(0)) :
			                          index == 6 ? Scale.diminished(mode.get(0)) : Scale.minor(mode.get(0)));
			  index++;
			  }
			  return modeSubs;
   }
   public static void subTest(Player p, ArrayList<String> majScale, int ms, int...prog) {
	   
	   for(int c : prog) {
		   ArrayList<ArrayList<String>> subs = modeSubs(majScale).get(c-1);
		   subs.get(0).add(subs.get(0).get(0));
		   subs.get(1).add(subs.get(1).get(0));
			   for(int n : Convert.mergeList(subs.get(0), 3)) {
				   p.mChannels[0].noteOn(n, 100);
				   Beat.cut(ms);
			   }
			   
		   
	   }
   }
   
//*************************************************************************
   public static void codeMusic(Player p, ArrayList<String> scale, int ms, int...prog) {
	 
	   int r = 0;
	   while(true) {
		  
		   for(int c : prog) {
			   ArrayList<Integer> midi = Convert.mergeList(Scale.modes(scale).get(c-1), 3);
			   midi.add(midi.get(0)+12);
			   int i = 0;
               p.mChannels[0].noteOn(midi.get(0)-24, 100);
               Collections.rotate(midi, c);
			   for(int n : midi) {
				   Collections.rotate(midi, r);
				   p.mChannels[0].noteOn(n, 75);
				   if(i == midi.size()/2) p.mChannels[0].noteOn(midi.get(i+3)-24, 70);
				   Beat.cut(ms);
				   i++;
				  
			   }
		   }
		   r++;
	   }
   }
  
   public static void bFindProg(Player p, boolean mozartify, ArrayList<String> scale, int ms, int...prog) {
	   
	   int i = 24;
	   while(true) {
		
		   for(int chord : prog) {
			   if(mozartify) {
			      Collections.rotate(scale, 1);
			   }
			   ArrayList<Integer> l = Convert.merge(Chord.keyChords(scale).get(chord-1));
			   
			   Fractal.binaryFind(p, l, i % l.size(),  ms);
			   
		   }
		   i++;
		 
		   
	   }  
	   
   }
   
  

}












