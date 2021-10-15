import java.util.Arrays;
import java.util.Scanner;

import javax.sound.midi.MidiUnavailableException;






	
public class Main{
		   
		

		public static void main(String[] args){
			
	  	    
			System.out.println("What note?");
			Scanner input = new Scanner(System.in);
			String inputString = input.nextLine();
			String keynote = inputString.substring(0,1).toUpperCase() + inputString.substring(1); 
				
			while(!Arrays.asList(Scale.chromatic).contains(keynote) && 
			      Note.enharmonicEquivelent(keynote) == null) {
						System.out.println("That's not a note");
						keynote = input.nextLine();
						break;
						
				} 
			
			input.close();
			String s = keynote;
			
           // Compose.balla(Scale.minor(keynote), 300, 1,5,6,4);
			
            Runnable r = ()-> Compose.recurseShell(s, 220);
			
			new Thread(r).start();
	

		

			


			
			/*
			for(ArrayList<String> mode : Scale.modes(Scale.major(keynote))) {
		    	
		    
				Compose.progTest(Scale.major(Modulation.circleOfFifths(mode.get(Convert.random(0,mode.size()))).get(1).get(1)), 100);
			    Compose.progTest(mode, 100);
			
			}
			
		
			
			
			
			/*
			
			for(String note : Scale.chromatic) {
				
				Runnable x = ()-> Compose.go(Scale.pentatonic(note), 12, 8, 300);
				Runnable y = ()-> Compose.go(Scale.minor(note), 12, 8, 300);
				 
				  while(true) {
					new Thread(x).start();
					Beat.cut(200);
					new Thread(y).start();
					System.out.println(Thread.activeCount());
					x.run();
				 
					//Beat.cut(2500);
			
		          }
			  
			}	
			*/
}
}