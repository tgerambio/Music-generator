
public class Beat{

	
	public static void cut(int ms) {
		try { Thread.sleep(ms); 
	    } catch( InterruptedException e ) {
	        e.printStackTrace();
	}
  }
	
	
}
