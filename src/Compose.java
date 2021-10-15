import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	//****************************************************************************
	
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
	
	public static String findAKey(Object...notes) {
		
		return null;
	}
	public static void counterpoint(ArrayList<String> chord, int ms, int...prog) {
		
		Player p = new Player();
	    
		
		
		
		while(true) {
			
			
			
			
			
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
	public static void progTest(ArrayList<String> scale, int ms) {
		
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
				p.mChannels[1].allNotesOff();
				int y = Convert.random(0, list.get(outer).size());
				
				int j = 0;
				
				while(j<2) { 
				    
					List<Integer> midiN = Convert.merge(list.get(outer).get(y));
					

					Runnable r = ()->{
						for(int h : midiN.subList(9, 17)) {
							p.mChannels[1].noteOn(h, 100);
							Beat.cut(ms*2);
							
						}
					};
					p.mChannels[1].allNotesOff();
				    new Thread(r).start();
					for(int m : midiN.subList(6,20)){
						
						p.mChannels[0].noteOn(m, 100);
						Beat.cut(ms);
						
					}
					p.mChannels[1].noteOn(midiN.get(11), 100);
					p.mChannels[1].noteOn(midiN.get(9), 100);
					
					for(int d = 20; d >= 6; d--){
						
						p.mChannels[0].noteOn(midiN.get(d), 100);
						Beat.cut(ms);
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
	public static void grow(ArrayList<String> scale) {
		
		Player player =  new Player();
		player.open();
		scale.add(scale.get(0));
		
		
		while(true) {
			
			for(ArrayList<String>  mode : Scale.modes(scale)) {
				Collections.shuffle(mode);
			    octaveJump(mode.get(Convert.random(0, mode.size()-1)),100);
			    Collections.shuffle(scale);
			    new Thread() {
			    	public void run() {
			    		arpeggiatorDown(mode, 100);
			    	}
			    }.start();
				for(int i = 0; i < mode.size(); i++) {
					player.mChannels[2].noteOn(Convert.random(0, mode.size()-1), 100);
					for(int x : Convert.midiNums(mode.get(i))) {
						player.mChannels[0].noteOn(x, 100);
						Beat.cut(i%2==0? 100 : i%3==0? 50 : 200);
			} Collections.rotate(scale, Convert.random(1, scale.size()));
		  }
	    }
	  }
	}
	public static void dream(String keyNote) {
		
		for(String x : Scale.minor(keyNote)) {
			
			Compose.fractalThreads(Scale.pentatonic(x), 1, 1050, 150);
		}
	}
	public static void fractalThreads(ArrayList<String> scale, int cycles, int compounder, int ms){/* compounder must be > ms
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
	public static void jackWagon(List<String> scale, int beats, int ms) throws MidiUnavailableException{
		
		final class Corner implements Runnable {
			  private List<String> scale;
			  private int measures;
			  private int beats;
			  private int range;
			  private int ms;
			  
			  public Corner(List<String>scale, int measures, int beats, int range,  int ms) {
				this.scale = scale;
			    this.measures = measures;
			    this.beats = beats;
			    this.range = range;
			    this.ms = ms;
			    
			  }

			  @Override
			  public void run() {
			  
					randomPhrase(scale, measures, beats, range, ms);
				
			  }
			}
		        
		        Thread[] ok = new Thread[9];
		        for(int i = 0; i < 9; i++) {
		        	ok[i] = new Thread(new Corner(scale, 8, 8, Convert.random(2,5), ms));
		        }
		
		  
			    for(Thread thread : ok) {
			    	thread.start();
			    	Beat.cut(ms);
			    }
			    
			   
			    
		       	
		
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
	
	public static void wind(String keynote, int ms){
		
		int infinity = (int)Double.POSITIVE_INFINITY;
		
		
		Thread bassLine = new Thread() {
		       public void run(){
		    	  
					Compose.randomPhrase(Scale.pentatonic(keynote), infinity, 16, 2, ms);
			        return;
		     }
		   };
		   
		bassLine.start();
		Compose.randomPhrase(Scale.minor(keynote), infinity, infinity, 4, ms);
	   
	}
	
	public static void circ5Three(String note) {
		
		List<String> scale = Scale.major(note);
		int i = 0;
		for(String x : scale) {
			
			playChord(Chord.dom7(Modulation.circleOfFifths(x).get(1).get(0)), 1, 400);
			playChord(Chord.dim(Modulation.circleOfFifths(x).get(0).get(2)), 1, 400);
			switch(i) {
			case 0:
			    playChord(Chord.major(x), 1, 400);
			    break;
			case 1:
				playChord(Chord.minor(x), 1, 400);
				break;
			case 2:
				playChord(Chord.minor(x), 1, 400);
				break;
			case 3:
				playChord(Chord.major(x), 1, 400);
				break;
			case 4:
				playChord(Chord.major(x), 1, 400);
				break;
			case 5:
				playChord(Chord.minor(x), 1, 400);
				break;
			case 6:
				playChord(Chord.dim(x), 1, 400);
			    break;
			}Beat.cut(200);
			 i++;
		}
		return;
		
	}
	
	
	public static void echoChamber(List<String> scale, int ms){
		
		    Player p = new Player();
	        Map<Integer, String> repeat = new HashMap<Integer, String>();
            
            int b = 0;
            int counter = 1;
            
            
            while(true){
                   
            	  if(counter%40==0) {
                	 leftShift(scale, 2);
                	 
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
        
public static void randomPhrase(List<String> scale, int measures, int beats, int range, int ms){
	
	
		Player p = new Player();
		p.open();
	    
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

public static void rpeEvolved(List<String> scale, int measures, int beats, int shift, int ms){
	
	
    
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

public static void circ5Test(String note) {
	
try {
   
		
		Synthesizer midiSynth = MidiSystem.getSynthesizer();
		midiSynth.open();
        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
        MidiChannel[] mChannels = midiSynth.getChannels();
        midiSynth.loadInstrument(instr[0]);
        ArrayList<String> chord;
        List<ArrayList<String>> cir = Modulation.circleOfFifths(note);
        mChannels[0].programChange(88);
        
        int changer =0;
        for(int j = 5; j < 144; j++) {
        	  
        	for(int i = 0; i < cir.size(); i++) {
        	        changer++;
        	        
        		if(i== 6) i = 0;
        		   
        		   for(int t = 0; t < 2; t++) {
        		   
        		   chord = changer <12 || changer > 18? Chord.major(cir.get(i).get(0)):
        			                                    Chord.minor(cir.get(i).get(1));
        		   
        		   octaveJump(chord.get(t+1), 100);
        		   mChannels[0].allNotesOff();
        		   mChannels[0].noteOn(Convert.midiNums(chord.get(0))[i%2==1? 2: 3], 75);
        		   mChannels[0].noteOn(Convert.midiNums(chord.get(1))[i%2==1? 3: 2], 75);
        		   mChannels[0].noteOn(Convert.midiNums(chord.get(2))[i%2==1? 3: 2], 75);
        		   Beat.cut(600);
        		   mChannels[0].noteOn(Convert.midiNums(chord.get(0))[1], 200);
        		   
        		   Beat.cut(i%2==0? 150: 600);
        		  
        		}	
        
       }     
        
      } 
      } catch(MidiUnavailableException e) {
         e.printStackTrace();
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
	
	   
	

	
	
	public static void monet(String startNote) {
		
		try {
			
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
	        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
	        MidiChannel[] mChannels = midiSynth.getChannels();
	        midiSynth.loadInstrument(instr[0]);
	    
	   ArrayList<ArrayList<String>> x =  Chord.keyChords(Scale.minor(startNote));
	  
	   for(int j = 0; j < 36; j++) {
		
	    for(List<String> chord : x) {
	    	
	    	for(int i = 0; i < 3; i++) {
	    		
	    	    mChannels[0].noteOn(Convert.midiNums(Modulation.invert(chord).get(2).get(i))[j%5==0? 5: i%2==1? 4 : j%3==0? 2 : j%4==0? 5 : i+1], 50);
		         
		         
	        // [j%2==0? i+3: i%2==1? i+4 : j%3==0? 6 : j%4==0? 5 
		      
		      try { Thread.sleep(j%3==0? 150 : j%2==0 ? 300 :  j*15); 
	        } catch( InterruptedException e ) {
	            e.printStackTrace();
	        }
	    	}
	        } 
	   }
	      } catch(MidiUnavailableException e) {
	         e.printStackTrace();
	      } 
	}
		
		
		  
	public static void weird(String note) {  // best in f# or g#
		try {
			
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
	        
	        
	        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
	        MidiChannel[] mChannels = midiSynth.getChannels();
	        
	        midiSynth.loadInstrument(instr[0]);
	    
	        mChannels[0].programChange(46);
	  
	   for(int j = 0; j < 360; j++) {
		  
		 mChannels[0].noteOn(Convert.midiNums(Scale.pentatonic(note).get(j%Scale.pentatonic(note).size()))[1], 100);
	     for(List<String> chord : Chord.keyChords(Scale.pentatonic(note))) {
	    	 
	    	for(List<String> inverse : Modulation.invert(chord)) {
	    	  int x = j < 23? 3 : 1;
	    	  for(int i = 0; i < x; i++) {
	    		 // mChannels[0].allNotesOff();
	    		  if(i==2) {
	    		   mChannels[0].noteOn(Convert.midiNums(note)[j%2==0? 2 : j%3==0? 4 : 3], 100);
	    		  }
	    	       mChannels[0].noteOn(Convert.midiNums(inverse.get(i))[j%2==0? 2 : i==2? 4 : 6], 100);
		         
		         
	        
		      
		      try { Thread.sleep(j%2==0 || i==1? 250 : j%3==0 || i==3? 50 : 0); 
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
public static void form(String note) {
try {
	
	Synthesizer midiSynth = MidiSystem.getSynthesizer();
	midiSynth.open();
    
    
    Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
    MidiChannel[] mChannels = midiSynth.getChannels();
    
    midiSynth.loadInstrument(instr[0]);

    mChannels[1].programChange(67);
  
    
    List<ArrayList<String>> c = Chord.keyChords(Scale.minor(note));
    
	for(int j = 0; j < 36; j++) {
		   
	    for(List<String> chord : c) {
	    	
		    for(List<String> inverse : Modulation.invert(chord)) {
		    	octaveJump(inverse.get(2), 50);
		        for(String x : inverse) {
		        	
		        	for(int beat = 0; beat < 8; beat++) {
		        
		        	    mChannels[0].noteOn(Convert.midiNums(x)[beat%2==0? 2 : beat%3==0? 5:  4], 50);
		                 for(String y : Scale.minor(note)) {
		                	 mChannels[0].noteOn(Convert.midiNums(y)[beat%2==0? 2 :4], 50);
		                	 
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
  } catch(MidiUnavailableException e) {
     e.printStackTrace();
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
    		   //2  lines above can be deleted. added on whim
    		   for(List<String> inverse : Modulation.invert(chord)) {
    			   for(String note : inverse) {
    				   mChannels[0].noteOn(Convert.midiNums(note)[i%4==0? 2 : 5], j%2==0? 50 : j%3==0? 75 : 25);
    			       
    		   }mChannels[0].noteOn(Convert.midiNums(chord.get(j%2==0? 0 : j%3==0? 1 : 2))[i%5==0? 6 : i%3==0? 4 : 3], i%2==0? 50: 12);
    	       //System.out.println(Thread.activeCount());
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

 public static void evenRythm(String startNote) { //Eb
	 try {
			
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			midiSynth.open();
	        Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
	        MidiChannel[] mChannels = midiSynth.getChannels();
	        midiSynth.loadInstrument(instr[0]);
	         
	       mChannels[0].programChange(0);
	       int x = 0;
	       int y = 360; //this is the base rythm
	       for(int whole = 1; whole <7; whole++) {
	    	   if(whole==5) whole=1;
	    	   octaveJump(Scale.pentatonic(startNote).get(whole/2),  y/2+1);
		       for(int half = 1; half <=2; half++) {
	    		   mChannels[0].noteOn(Convert.midiNums(startNote)[5], 75);
		    	   for(int quarter = 1; quarter <=2; quarter++) {
		    		   try { Thread.sleep(quarter==1? y : y/3); 
		   	        } catch( InterruptedException e1 ) {
		   	            e1.printStackTrace();
		   	        }
		    		   mChannels[0].noteOn(Convert.midiNums(startNote)[quarter==1? 5: 4], 50);
		    		   for(int eighth = 1; eighth <=2; eighth ++) {
		    			   if(x >= 5) x = 0; // make it x>= a mult of 2 for evenness in phrase
		    			   mChannels[0].noteOn(Convert.midiNums(Scale.pentatonic(startNote).get(x))[eighth == 1? 3 : 2], eighth == 1? 50 : 75);
		    	           x++;
		      try { Thread.sleep(eighth ==1? y : y/3); 
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
  
 public static void octaveJump(String note, int ms){
	 
		   
			Player p = new Player();
			p.open();
	        p.mChannels[4].programChange(0);        // 46
	        for(int x : Convert.midiNums(note)) {
	        	//mChannels[4].allNotesOff();
	            p.mChannels[4].noteOn(x, 50);
	       
		        Beat.cut(ms);
	       
	      }
	      
 }
 public static void shuffle(List<String> x) throws MidiUnavailableException {
	 
	 
	 List<String> scale = new ArrayList<String>();
	 for(String note : x) {
		 scale.add(note);
	 }
	 
	 for(String note : scale) {
		   
		   for(int i = 0, j = scale.size()-1; i < scale.size(); i++, j--) {
			   
			 
			   octaveJump(scale.get(j), i*25);
			   try { Thread.sleep(450/(i+1)); 
		        } catch( InterruptedException e ) {
		            e.printStackTrace();
		        }
			   octaveJump(note,  i*50);
			   try { Thread.sleep(112/(i+1)); 
		        } catch( InterruptedException e ) {
		         // octaveJump(scale.get(Math.abs(j-i)), 5, i*75);
			
			   
		   }//octaveJump(scale.get(Math.abs(j-i)), 5, i*75);
		   } 		 
}
}
public static void triple(String x){
	
	    Player p = new Player();
	    p.open();
        
        //List<String> min = Scale.minor(x);
        List<Integer> pen = Scale.pentatonic(x).stream()
        		                               .map(z-> Convert.midiNums(z)[3])
        		                               .collect(Collectors.toList());
        
        List<Integer> min = Scale.minor(x).stream().map(z-> Convert.midiNums(z)[0]).collect(Collectors.toList());
        for(int whole = 1; whole < 144; whole++) {
        	
        	Collections.shuffle(pen);
        	for(int half = 1; half <=3; half++) {
        		//Collections.shuffle(pen);
        		p.mChannels[half].noteOn(pen.get(0), half==1? 100 :half==2? 50 : 25);
        		for(int quarter = 1; quarter <=3; quarter++) {
        			Collections.shuffle(min);
        	        p.mChannels[quarter].noteOn(min.get(3), quarter==1? 100 :quarter==2? 50 : 25);
	                        Beat.cut(50);
	    }
	    }  
	    }  
             
      
 
}

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
   
   public static List<ArrayList<String>> leftShiftNest(List<ArrayList<String>> cir, int count){
	   for(int i = 0; i < count; i++) {
		   cir.add(cir.get(0));
		   cir.remove(0);
	   } return cir;
   }
  

}