package cousework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * This is the graphics screen class.<br/><br/>
 * 
 * Use the methods provided by this class to draw to the screen. e.g.<br/><br/>
 * 
 * <code>
 * GraphicsScreen g = new GraphicsScreen();<br/><br/>
 * 
 * g.moveTo(250, 200);<br/>
 * g.lineTo(200, 100);<br/>
 * g.circle(100);<br/>
 * 
 * </code><br/><br/>
 * 
 * note: it would have been much easier to draw directly onto an image rather than using a command stack approach.
 * But this is much more interesting in terms of programming experience. If this application was been developed for
 * real then drawing onto an image in memory would have been the better approach, since it would have been more
 * efficient and the code would have been much simpler.
 * 
 * @author mdixon
 *
 */
@SuppressWarnings("serial")
public class GraphicsScreen {

	// Some private attributes for use in the class.

	/**
	 * The JFrame which represents the 'window' of the graphics screen.
	 */
	private final JFrame frame;

	/**
	 * The current X position of the pen.
	 */
	private int penX;

	/**
	 * The current Y position of the pen.
	 */
	private int penY;

	/**
	 * A list of commands which are used to paint the window when required.
	 * 
	 * This is synchronized so that multi-threaded access to the list can be safely performed.
	 */
	private final List<Command> commands = Collections.synchronizedList(new ArrayList<Command>());

	/**
	 * The available command types that are currently supported by the graphics screen.
	 */
	private enum CommandKind {
		MOVE,
		LINE,
		CIRCLE,
		PEN_COLOUR,
	}

	/**
	 * Instances of this class represent a command to be executed.  
	 */
	private class Command {

		/**
		 * The type of the command.
		 */
		final private CommandKind type;

		/**
		 * The arguments used by the command, e.g. x and y position for a move command.
		 */
		final private int [] args;

		/**
		 * Executes the command, using the given graphics object to draw when necessary.
		 * 
		 * @param g the graphics object to be used for drawing.
		 */
		void execute(Graphics g) {

			switch( type ) {

			case MOVE:
				// No drawing required for this command. Just set the current pen position.

				penX = args[0];
				penY = args[1];

				break;

			case LINE:

				g.drawLine(penX, penY, args[0], args[1]);

				// Move the pen to the end of the line just drawn
				penX = args[0];
				penY = args[1];

				break;

			case CIRCLE:
				// store the radius in a variable since we will use it several times.
				int radius = args[0];

				g.drawOval(penX - radius, penY - radius, radius<<1, radius<<1);	// radius<<1 multiples the radius by 2 (same as radius * 2 but more efficient).

				break;

			case PEN_COLOUR:

				int red = args[0];
				int grn = args[1];
				int blue = args[2];

				g.setColor(new Color(red, grn, blue));

				break;
			}
		}

		/**
		 * 
		 * @param type the type of the command.
		 * @param args the arguments used by the command, e.g. x and y position for a move command.
		 */
		Command(CommandKind type, int [] args) {

			this.type = type;
			this.args = args;
		}
	}

	/**
	 * This is the "panel" (inside the outer window frame) in which the graphics get painted.
	 */
	private class GraphicsPanel extends JPanel {

		public GraphicsPanel() {

			setBorder(BorderFactory.createLineBorder(Color.black));
		}

		public Dimension getPreferredSize() {

			return new Dimension(500,400);
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);       

			penX = 0;	// init the pen position before executing the commands
			penY = 0;

			// For each command available call its execute() method.
			synchronized(commands) {		// this ensures updates to the command list (by another thread) are blocked during iteration.
				
				for ( Command command : commands ) {

					command.execute(g);	// this will make the command do the appropriate action. e.g. paint a line
				}
			}
		}  
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// This is the public API

	/**
	 * Sets the current pen position to the given X and Y values.
	 * 
	 * @param x
	 * @param y
	 */
	public void moveTo(int x, int y) {

		int [] args = {x, y};

		// Add the appropriate command to the command list.
		commands.add( new Command(CommandKind.MOVE, args) );
	}

	/**
	 * Draws a line from the current pen position to the given X and Y position.<br/><br/>
	 * 
	 * The pen position is updated to be at the end of the line.
	 * 
	 * @param x line end X position
	 * @param y line end Y position
	 */
	public void lineTo(int x, int y) {

		int [] args = {x, y};

		// Add the appropriate command to the command list.
		commands.add( new Command(CommandKind.LINE, args) );

		frame.repaint();
	}

	/**
	 * Draws a circle at the current pen position using the specified radius.<br/><br/>
	 * 
	 * The current pen position remains unchanged.
	 * 
	 * @param radius
	 */
	public void circle(int radius) {

		int [] args = {radius};

		// Add the appropriate command to the command list.
		commands.add( new Command(CommandKind.CIRCLE, args) );

		frame.repaint();
	}

	/**
	 * Sets the pen colour to the specified red, green, blue values in the range (0 - 255).
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void penColour(int red, int green, int blue) {

		int [] args = {red, green, blue};

		// Add the appropriate command to the command list.
		commands.add( new Command(CommandKind.PEN_COLOUR, args) );

		frame.repaint();
	}

	/**
	 * Clears the graphics screen.
	 */
	public void clear() {

		// No need for a clear command, just remove all previous commands and ask for a repaint.
		commands.clear();

		frame.repaint();
	}

	/**
	 * Causes the graphics screen to close itself. This should be called when the application needs to terminate.
	 */
	public void close() {

		// Simulate the 'X' being pressed on the window.
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent( new WindowEvent(frame, WindowEvent.WINDOW_CLOSING) );
	}

	/**
	 * Constructor. Sets up the internal state of the graphics screen class, ready for use.
	 */
	public GraphicsScreen() {

		frame = new JFrame("Graphics Interpreter");	// create a window 'frame'.

		//Schedule a job for the event-dispatching thread creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				frame.setAlwaysOnTop(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new GraphicsPanel());
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	public void moveTo(String x, String y) {
		// TODO Auto-generated method stub
		
	}

	public void Circle(String x) {
		// TODO Auto-generated method stub
		
	}

	public void LineTo(String x, String y) {
		// TODO Auto-generated method stub
		
	}
}