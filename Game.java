
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Game {
      public static void main (String []args ){
		  JFrame window = new JFrame ("First Game ");
		  window.setUndecorated(true);
		  window.setLayout (null);
		  window.setResizable(false);
		  window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  window.setContentPane (new GamePanel () );
		  window.pack();
		  window.setVisible(true);
		  window.setLocationRelativeTo (null);
		  BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); 
		  Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor"); 
		  window.getContentPane().setCursor(blankCursor); 
		}
	
	
}