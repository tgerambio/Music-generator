import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Playerexp {
	
	Synthesizer midiSynth;
    Instrument[] instr; 
    MidiChannel[] mChannels; 
    
    
    public Playerexp() {
    	
    }
    
    public void open() {
    	try {
			this.midiSynth = MidiSystem.getSynthesizer();
		} catch (MidiUnavailableException e) {
			
			e.printStackTrace();
		}
    	
    	this.instr = midiSynth.getDefaultSoundbank().getInstruments();
    	this.mChannels = midiSynth.getChannels();
    	try {
			midiSynth.open();
		} catch (MidiUnavailableException e) {
			
			e.printStackTrace();
		}
    	midiSynth.loadInstrument(instr[0]);
    	
    }

}
