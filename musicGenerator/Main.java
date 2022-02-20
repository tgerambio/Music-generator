package musicGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import javax.sound.midi.MidiUnavailableException;






	
public class Main{
		   
		

		public static void main(String[] args){
			
			
			System.out.println("What note?");
			Scanner input = new Scanner(System.in);
			String keyNote = input.nextLine();
	
				
			while(!Arrays.asList(Scale.chromatic).contains(keyNote.substring(0,1).toUpperCase() + keyNote.substring(1))){
				  System.out.println("Try again.");
                  keyNote = input.nextLine();
            }
	    	
			keyNote = keyNote.substring(0,1).toUpperCase() + keyNote.substring(1);
			
			input.close();
			
			Player p = new Player();
	 
	        String s =  keyNote;
	       
	        int[] prog = {1,5,6,4};
	        ArrayList<String> scale = Scale.minor(keyNote);
	        p.mChannels[0].programChange(0);
	        Compose.progClassTester(p, scale, 150);
	        Compose.moveUpOrOver(p, Scale.harmonicMin(keyNote), 8, 400, 2,5,1);
	        //Compose.rTJ(p, Chord.minor(keyNote));
	       // Compose.bluesPhrases(p, scale, 7, 180, prog);
	       // Compose.aprProg(p, scale, 300, prog);
	        //Compose.balla(Scale.minor(keyNote), 180, prog);
	        //Compose.mapper(p, scale, 4, 8, prog, 200);
	        //Compose.toolProg(p, scale, 180, 7, 5,1,6,4);
	        //Compose.variantProg(p, Scale.major(keyNote), 4, 120, 1,5,6,4);
	       // Compose.phraseProg(p, Scale.minor(keyNote), 16, 140, 1,5,6,4);
	       // Compose.phraseProg(p, Scale.major(keyNote), 8, 140, 1,5,6,4);
	     // Compose.blues(p, Scale.minor(keyNote), 4, 250,  2,5,1);
	       // Compose.bigOne(Scale.major(keyNote), 1000, 3, 1500, 1,5,1,4);	      //  Compose.progClassTest1(p, keyNote, 120);
	      // Compose.ammendPhrase(p, Scale.minor(keyNote), 8, 200, 4,1,3,7);
	      // Compose.monet(p,keyNote);
	       //Compose.arpeggiProg(p, Scale.minor(keyNote), 110, new int[] {0,1,2,1,3,2,1,4}, 1,5,6,4);
	        
	        //Compose.codeMusic(p, Scale.harmonicMin(s), 180, 1,5,6,3,4,1,4,5);
	       
	        
	        
	        
	        //Compose.zoo(p, Scale.major(s), 180, 3, Convert.random(2, 4), 1,5,6,4,5,4,6,5);
	        
	        
	        
		   
			
			
			//Compose.testGraph(p,keyNote, 200);
		    
		 
		    
			
		    
		 
			
			
}
}
