package cousework;

public class GraphicsExample {

void print(){
	    
		 
	
		
		System.out.println("Let's draw something on the screen!");
	}
		
	public static void main(String[] args) {
		GraphicsExample obj = new GraphicsExample();
	    
		System.out.println("My name is ...");
		
		obj.print();
		
		GraphicsScreen GP = new GraphicsScreen();
		
		GP.moveTo(200.200);
		GP.circle(150);
		GP.lineTo(200, 100);
		
	}
	
}
