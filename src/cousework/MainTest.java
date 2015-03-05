package cousework;
import java.util.Scanner;
public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//GraphicsScreen g = new GraphicsScreen();
		 
Scanner input = new Scanner(System.in);	// used to read the keyboard
		
		String next;	// stores the next line input
		
		
			System.out.print("Enter a command (\"stop\") to finish : ");
			next = input.nextLine();
			String[] StrArray;
			
			String LineEntered, firstword, x, y;
			GraphicsScreen graphics = new GraphicsScreen();
			
			int NumberOfwords;
			float amount = 0;
			
			Scanner scan = new Scanner(System.in);
			
			do{
			        System.out.println("Welcome to GraphicsScreen:");
			        System.out.println("Create the graph (circle or line):");
			   
			        LineEntered = scan.nextLine();
			        StrArray = LineEntered.split(" ");
			        
			        firstword = "";
			        NumberOfwords = StrArray.length;
			        
			       if(NumberOfwords >=1){
			    	   firstword = StrArray[0];
			    	   firstword = firstword.toLowerCase();
			    	   
			    	   switch(firstword)
			    	   {
			    	   
			    	   case "moveto":
			    	   
			    	         if (NumberOfwords ==3)
			    	         {
			    	        	 x = StrArray[1];
			    	        	 y = StrArray[2];
			    	        	 graphics.moveTo(x, y);
			    	         }
			    	        
			    	         else 
			    	        	 System.out.println("Please Enter the code(moveTO) in format");
			    	    break;
			    	    
			    	   case "circle":
			    		   
			    		   if (NumberOfwords == 2)
			    		   {
			    			   x = StrArray[1];
			    			   
			    			   graphics.Circle(x);
			    			   
			    		   }
			    		   
			    		   else 
			    			   System.out.println("Please Enter the code(Circle) in format");
			    		   break;
			    		   
			    	        
	                   case "lineto":
			    		   
			    		   if (NumberOfwords == 3)
			    		   {
			    			   x = StrArray[1];
			    			   y = StrArray[2];
			    			   
			    			   graphics.LineTo(x, y);
			    			   
			    		   }
			    		   
			    		   else 
			    			   System.out.println("Please Enter the code(LineTo) in format");
			    		   break;   
			    		   
	                   case "clear":
	                	   
	                	   if (NumberOfwords ==0)
	                	   {
	                		   
	                		   graphics.clear();
	                	   }
	                       break;
			    		   
			    		   
			    		   
			    	   default:
			    		   if(!firstword.equals("stop"))
			    			   System.out.println("Invalid command");
			    		   else 
			    			   System.out.println("Bye & see you soon...");
			    	   }
			    	   
			       }//end of the statement
			   
			       else {
			    	   
			    	   System.out.println("No command or wrong number of parameters entered ");
			       }	
		
		
			
		
		
		while ( next.equalsIgnoreCase("stop") == false );
		
		System.out.println("You have decided to stop entering commands. Program terminated!");
			}
	}
}
		
	



