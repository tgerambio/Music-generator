package musicGenerator;

public class Beat {
	
	public static int counter;
	
	public static void cut(int ms) {
		try {
			Thread.sleep(ms); 
	   	 } 
		catch( InterruptedException e ) {
	       	 	e.printStackTrace();
	   	 }
	}
	
	public static void swing(int ms) {
		try { 
			Thread.sleep(counter%2==0? ms : ms/2); 
	    	} 
		catch( InterruptedException e ) {
	        	e.printStackTrace();
	    	}
		counter++;
	}
	
	public static void swing(int ms, int latency) {
		try { 	
			Thread.sleep(counter%2==0? ms : ms/latency); 
	    	} 
		catch( InterruptedException e ) {
	        	e.printStackTrace();
	   	 }
	         counter++;
	}
}
