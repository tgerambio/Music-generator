package musicGenerator;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
public class Player {

	Synthesizer midiSynth;
    Instrument[] instr; 
    MidiChannel[] mChannels; 
    
    
    public  Player() {
        this.open();
    }
    
    public void open() {
    	try {
		this.midiSynth = MidiSystem.getSynthesizer();
	} 
	catch (MidiUnavailableException e) {
			e.printStackTrace();
	}
    	
    	this.instr = midiSynth.getDefaultSoundbank().getInstruments();
    	this.mChannels = midiSynth.getChannels(); // previously this.mChannels
    	try {
		midiSynth.open();
	} 
	catch (MidiUnavailableException e) {
		e.printStackTrace();
	}
    	midiSynth.loadInstrument(instr[0]);
    	
    }
	
    public void play(int note) {
       this.mChannels[0].noteOn(note, 100);
    }
}
