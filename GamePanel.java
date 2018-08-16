
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
 public class GamePanel extends JPanel  implements Runnable , KeyListener {
// Fields 
	
	 public static int WIDTH = 700;
	 public static int HEIGHT = 600;
	 private Thread thread;
	 private Thread thread1;
	 private boolean Running;
     private boolean Esc ;
	 private BufferedImage image ;
	 private Graphics2D g;
    private boolean rerun ;
	 private int FBS = 45;
	 private double averageFBS ;
	 public static Player player ;
	 
	 private long waveStartTimer;
	 private long waveStartTimerDiff;
	 private int waveNumber;
	 private boolean waveStart;
	 private  long waveDelay = 2000;
     private boolean stop;
	 private long slowDownTimer;
	 private long slowDownTimerDiff;
	 private int slowDownLength = 6000 ;
	 
	 public static ArrayList <Bullet> bullets ;
	 public static ArrayList <Enemy> enemies;
     public static ArrayList <PowerUp> powerups;
     public static ArrayList <Explosion> explosions;
	 public static ArrayList <Text> texts;
     
     private Sound sound ;
// Constructor 
	 public GamePanel (){
	 super();
	 setPreferredSize (new Dimension (WIDTH , HEIGHT));
	 setFocusable (true );
	 requestFocus ();
	 Esc = false;
	 rerun = false ;
	 }
// Functions
     public void addNotify (){
	 super.addNotify ();
     if (thread == null ){
		thread = new Thread (this);	
	    thread.start();
	 addKeyListener (this);
	}
	}	
     public void run (){
	 Running = true ; 
	 image = new BufferedImage (WIDTH , HEIGHT , BufferedImage.TYPE_INT_RGB );
	 g =(Graphics2D) image.getGraphics();
	 g.setRenderingHint (
	                      RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
	 g.setRenderingHint (
	                      RenderingHints.KEY_TEXT_ANTIALIASING,
	                      RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	 
	  player = new Player ();
	  bullets = new ArrayList <Bullet>();
	  enemies = new ArrayList<Enemy>();
	  powerups = new ArrayList <PowerUp>();
      explosions = new ArrayList <Explosion>();
	  texts = new ArrayList<Text>();
	  sound = new Sound ();
	  waveStartTimer = 0;
	  waveStartTimerDiff = 0;
	  waveStart = true;
	  waveNumber = 0;
	  
	 
	  long startTime;
	  long URDTimeMillis;
	  long waitTime;
	  long totalTime = 0 ;
	  int frameCount = 0;
	  int maxFrameCount = 30;
	  long targetTime = 1000 / FBS ;
	  
	while (Running ){
	 startTime = System.nanoTime();
	 
	 gameUpdate();
     gameRender();
     gameDraw ();	  
	 URDTimeMillis = (System.nanoTime() - startTime ) /1000000;
	 waitTime = targetTime - URDTimeMillis;
	 System.out.println ( "   ------------------------------------------    "  ) ;
	 System.out.println ( " ---- URDTimeMillis ----- "  + URDTimeMillis ) ;
	 System.out.println ( "                                                     "  ) ;
     System.out.println (" ---- Wait Time ----- "   + waitTime ) ;
    
	  try {
		Thread.sleep (waitTime);
	     }
	    catch(Exception e) {
		
	    }
	    totalTime += System.nanoTime() - startTime ;	
	    frameCount++;
	    if (frameCount == maxFrameCount )
	    {
		
		System.out.println ("                                           " ) ;
		System.out.println (" ----- totalTime ------ "     + totalTime / 1000000   ) ;

		
		averageFBS = 1000.0 / ((totalTime/frameCount)/ 1000000) ;
		frameCount =0;
		totalTime = 0;
	    } 
	  }
	 // Game Reload 
	  
		
		
		  
	   
		// End Game Reload
	  
	  if (!Running && waveNumber == 16){
		  g.setColor(new Color(50, 155 , 0));
	       g.fillRect(0 ,0, WIDTH, HEIGHT);
	       g.setColor(new Color (100 , 255, 180));
	       g.setFont(new Font ("Century Gothic" , Font.PLAIN , 25));
	       String s = "C O N G R A T U L A T I O N";
	       int length = (int) (g.getFontMetrics().getStringBounds( s , g).getWidth());
	       g.drawString (s , (WIDTH - length ) / 2 , (HEIGHT / 2 ));
	     
		    g.setColor(Color.RED.darker());
	        g.setFont(new Font ("French Script MT Rrgular" , Font.PLAIN , 25));
	        s = "Final Score :  " + player.getScore();
	        length = (int) (g.getFontMetrics().getStringBounds( s , g).getWidth());
	        g.drawString (s , (WIDTH - length ) / 2 , HEIGHT / 2 + 40 );
		 gameDraw();
   		 
	  }
	else {
	       sound.getGameOverSound().play();
    	   g.setColor(new Color(50, 155 , 0));
	       g.fillRect(0 ,0, WIDTH, HEIGHT);
	       g.setColor(new Color (100 , 255, 180));
	       g.setFont(new Font ("Century Gothic" , Font.PLAIN , 25));
	       String s = "G A M E  O V E R";
	       int length = (int) (g.getFontMetrics().getStringBounds( s , g).getWidth());
	       g.drawString (s , (WIDTH - length ) / 2 , (HEIGHT / 2 ));
	 
    	    g.setColor(Color.RED.darker());
	        g.setFont(new Font ("French Script MT Rrgular" , Font.PLAIN , 25));
	        s = "Final Score :  " + player.getScore();
	        length = (int) (g.getFontMetrics().getStringBounds( s , g).getWidth());
	        g.drawString (s , (WIDTH - length ) / 2 , HEIGHT / 2 + 40 );
	    }
	    gameDraw();
		if ( Esc == true ) {
		System.exit(0);
		}
		
} 
       
     private void gameUpdate() 
		{
	     
	     
		// new wave 
        if (waveStartTimer == 0 && enemies.size()== 0 ){
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
		}	
		if (waveNumber == 16){
			Running = false;
		}
        else {
			waveStartTimerDiff = (System.nanoTime() - waveStartTimer ) / 1000000;
			if (waveStartTimerDiff > waveDelay) {
				waveStart = true ;
				waveStartTimer = 0;
				waveStartTimerDiff = 0;
			}
		}		
		
		// Create Enemies
		if (waveStart && enemies.size()== 0) {
			createNewEnemies ();
		}
		 
		 // Player update
       player.update();
	   
	    // bullet update
	   for (int i = 0 ; i < bullets.size(); i++ ){
		   boolean remove = bullets.get(i).update();
		   if (remove){
			   bullets.remove(i);
			   i-- ;
		   }
	   }
	   // enemy update
	   
	   for (int i = 0 ; i < enemies.size(); i++){
		   enemies.get(i).update();
	   }
        // powerup update 
        for (int i = 0; i < powerups.size(); i++ ){
          boolean remove =  powerups.get(i).update();
          if (remove) {
                powerups.remove(i);
                i--;
            }
			
			
        
        }
    
        // explosion update
        for (int i = 0 ; i < explosions.size(); i++){
            boolean remove = explosions.get(i).update();
            if (remove) {
               explosions.remove(i);
                i--;
            }
        }
	   
	   // text update
	   for (int i = 0 ; i < texts.size(); i++){
		   boolean remove = texts.get(i).update();
		   if (remove){
			   texts.remove(i);
			   i--;
		   }
	   }
	   
	   // bullet-enemy collisoin
	   for ( int i = 0; i < bullets.size(); i++){
		   Bullet b = bullets.get(i);
		   double bx = b.getx();
		   double by = b.gety();
		   double br = b.getr();
		   
		   for(int j = 0; j< enemies.size(); j++){
			   Enemy e = enemies.get(j);
		       double ex = e.getx();
			   double ey = e.gety();
			   double er = e.getr();
			   
			   double dx = bx - ex ;
			   double dy = by - ey ;
			   
			   double dist = Math.sqrt (dx * dx + dy * dy);
			   if (dist < br +er){
				   e.hit();
				   bullets.remove(i);
				   i--;
				   sound.getExplosionSound().play();
				   sound.getBulletSound().play();
				   break ;
			   }
		   }
	    }
	   
	   // check dead enemies 
	   for (int i = 0 ; i < enemies.size(); i++){
		   if (enemies.get(i).isDead()){

			   Enemy e = enemies.get(i);
               // chance for powerup
                
               double rand = Math.random();
			   if ( player.getPower  ( ) < 7) {
			  
			    if (rand < 0.001 ) powerups.add(new PowerUp( 2, e.getx(), e.gety()));
               else if (rand < 0.020)  powerups.add(new PowerUp( 3 , e.getx(), e.gety()));
               else if (rand < 0.120)  powerups.add (new PowerUp(2 ,e.getx(), e.gety()));
               else if (rand < 0.130)   powerups.add (new PowerUp(4 ,e.getx(), e.gety()));
			    }
				 if (rand < 0.00444)   powerups.add(new PowerUp(1, e.getx(), e.gety()));
     			player.addScore(e.getType() + e.getRank());
               enemies.remove(i); 
			   i--;
			   
                
               e.explode();
               explosions.add (new Explosion ( e.getx() , e.gety(),e.getr(), e.getr()+ 20)); 
		   }
	   }
	   
      // player -enemy collision
        if (!player .isRecovering()) {
            int px = player.getx();
            int py = player.gety();
            int pr = player.getr();
            for (int i = 0 ; i < enemies.size(); i++){
                Enemy e = enemies.get(i);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();
                
                double dx = px - ex;
                double dy = py -ey ;
                double dist = Math.sqrt (dx * dx + dy * dy );
                if (dist < pr + er ){
                    player.loseLife();
                }

            }
        }
		// check dead player
		if (player.isDead()){
			sound.getGameOverSound().play();
			Running = false;
		}
		
        // player - powerup collision
        int px = player.getx();
        int py = player.gety();
        int pr = player.getr();
        for (int i = 0 ; i < powerups.size(); i++){
         PowerUp p = powerups.get(i);
         double x = p.getx();
         double y = p.gety();
         double r = p.getr();
         double dx = px - x;
         double dy = py - y ;
         double dist = Math.sqrt (dx * dx + dy * dy );
         // Collected powerup
         if (dist < pr+ r ){
             int Type = p.getType();
             if (Type == 1 ) {
                player.gainLife();
				texts.add (new Text(player.getx(), player.gety(), 1000, "Exra Life" ));
				sound.getPowerSound().play();
            }
            if (Type == 2 && player.getPower( ) < 7  ) {
               player.increasePower(1);
			   	texts.add (new Text(player.getx(), player.gety(), 450, "Power" ));
                sound.getPowerSound().play();
            }
            if (Type == 3  && player.getPower( ) <  7   ) {
                player.increasePower(2);
				texts.add (new Text(player.getx(), player.gety(), 500, "Double Power" ));
				sound.getPowerSound().play();
            }
			if (Type == 4 && player.getPower( ) < 7  ) {
				slowDownTimer = System.nanoTime();
				for (int j = 0 ; j < enemies.size(); j++ ){
				    enemies.get(j).setSlow(true);
				}
				texts.add (new Text(player.getx(), player.gety(), 600, "Slow Down" ));
				sound.getPowerSound().play();
			}

            powerups.remove(i);
            i --;
            }
        }
		// slowdown update
		if (slowDownTimer != 0){
			slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000000;
			if (slowDownTimerDiff > slowDownLength){
				slowDownTimer = 0;
				for (int j = 0 ; j < enemies.size(); j++ ){
				    enemies.get(j).setSlow(false);
					
				}
			}
		}
    }
     private void gameRender ()
	 {
		 // draw background
		
		    g.setColor (new Color (130, 130, 207));
		   g.fillRect (0 , 0 , WIDTH, HEIGHT );
		            
		// draw stop
		if ( stop  ) {
		   if ( stop ) {
	
             }
		  while ( Running) {
		  try {
		    thread.sleep(	1000);
			}
			catch ( Exception e ) 
			{
			
			}
				
			
			if ( rerun ) { stop = false ;  rerun = false ; break; }
			
			}
		    
		}
		// drew slowdown screen
		if (slowDownTimer != 0){
		    g.setColor (new Color (255 , 255 , 255 ,100));
		    g.fillRect (0 , 0 , WIDTH , HEIGHT);
		}
		
		// draw Player 
		player.draw (g);
		
       // draw bullet
		for (int i = 0 ; i < bullets.size() ; i++ ) {
			bullets.get(i).draw(g);
		}
		// draw enemies
		for (int i =0 ; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
         
        // draw powerups
		for (int i =0; i < powerups.size(); i++){
            powerups.get(i).draw(g);
         
        }  
        // draw explosions 
        for ( int i = 0; i < explosions.size(); i++){
            explosions.get(i).draw(g);
        }
		
		// draw text 
		for (int i= 0 ; i < texts.size(); i++){
			texts.get(i).draw(g);
		}
		
		// draw wave number 
		if (waveStartTimer != 0){
			g.setFont (new Font ("Century Gothic", Font.PLAIN, 32));   
			String s = "- W A V E   " + waveNumber + "  -";
			int length = (int) g.getFontMetrics().getStringBounds(s ,g).getWidth();
			int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff / waveDelay));
			if (alpha > 255) alpha =255;
			g.setColor (new Color (100 ,255 ,130, alpha));
			g.drawString (s, WIDTH / 2 - length / 2, HEIGHT / 2);
		}
		// draw player lives
		for (int i = 0 ; i < player.getLives() ; i++){
			g.setColor(Color.GREEN);
			g.setStroke (new BasicStroke (3) );
            g.fillOval (20 + ( 20 * i ) , 20 , player.getr() * 2 , player.getr() *2 );
            
            g.setColor (Color.GREEN.darker());
            g.drawOval (20 + ( 20 * i ) , 20 , player.getr() * 2 , player.getr() *2 );
            g.setStroke (new BasicStroke (1) );
		}

        // draw player power
        g.setColor (Color.YELLOW);
        g.fillRect (20 , 45 , player.getPower() * 12 ,12);
        g.setColor (Color.YELLOW.darker());
        g.setStroke (new BasicStroke(2));
        for ( int i = 0 ; i < player.getRequiredPower(); i ++){
            g.drawRect ( 20 + 12 * i , 45 ,12 , 12 );
        }
        g.setStroke(new BasicStroke(1));


        // draw player score
        g.setColor(new Color (53 ,70,250 ));
        g.setFont (new Font ("Colonna MT Regular" , Font.PLAIN , 17 ));
        g.drawString("Score :  " + player.getScore(), WIDTH - 115 , 40);
		
		// draw slowDown meter
		if (slowDownTimer != 0){
			g.setColor (Color.WHITE);
			g.drawRect (20 , 70 ,100 , 8);
			g.fillRect (20 , 70 ,
			(int) (100 - 100.0 * slowDownTimerDiff / slowDownLength) , 8);
		}
		
	 } 
     private void gameDraw ()
	 {

		 Graphics g2 = this.getGraphics();
		   g2.drawImage (image , 0 , 0 , null);
		   g2.dispose();
		 
		 
	 }
   
    private void createNewEnemies ( ){
		enemies.clear();
		Enemy e ;
		
		if (waveNumber == 1) {
			for (int i = 0 ; i < 4 ; i++){
				enemies.add (new Enemy (1, 1));
			}
		}
		if (waveNumber == 2) {
			for (int i = 0 ; i < 8; i++){
				enemies.add (new Enemy (1, 1));
			}
            enemies.add (new Enemy (1, 2 ));
            enemies.add (new Enemy (1, 2 ));
		}
		if (waveNumber == 3 ){
           for (int i = 0 ; i < 4; i++){
				enemies.add (new Enemy (1, 2));
			    enemies.add (new Enemy (1, 2));
			}
        }
		if (waveNumber == 4) {
			enemies.add (new Enemy (1, 3));
			enemies.add (new Enemy (1, 4));
			for (int i = 0 ; i < 4 ; i++){
				enemies.add (new Enemy (2, 1));
			}
		}
		if (waveNumber == 5) {
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (1, 3));
			enemies.add (new Enemy (2, 3));
		}
		if (waveNumber == 6){
			enemies.add (new Enemy (1, 3));
			for (int i = 0 ; i < 4 ; i++){
				enemies.add (new Enemy (2, 1));
				enemies.add (new Enemy (3, 1));
			}
		}
		if (waveNumber == 7){
			enemies.add (new Enemy (1, 3));
			enemies.add (new Enemy (2, 3));
			enemies.add (new Enemy (3, 3));
		}
		if (waveNumber == 8){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
		}
		if (waveNumber == 9){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			
		}
		if (waveNumber == 10){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
		}
		if (waveNumber == 11){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
		    enemies.add (new Enemy (3, 4));
		}
		if (waveNumber == 12){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			
		}if (waveNumber == 13){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
		}
		if (waveNumber == 14){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
		}
		if (waveNumber == 15){
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
			enemies.add (new Enemy (1, 4));
			enemies.add (new Enemy (2, 4));
			enemies.add (new Enemy (3, 4));
		}
		
	}

   public void keyTyped (KeyEvent key) 
	{
		
	}
	public void keyPressed (KeyEvent key) 
	{
		 int keyCode = key.getKeyCode();
		if ( Running || stop )
		{
		   
		
     		if (keyCode == KeyEvent.VK_LEFT )
	    	{
			player.setLeft (true );
		   }
		  if (keyCode == KeyEvent.VK_RIGHT )
		   {
			player.setRight (true );
		   }
		   if (keyCode == KeyEvent.VK_UP )
		  {
			player.setUp (true );
		   }
		   if (keyCode == KeyEvent.VK_DOWN )
		  {
			player.setDown (true );
		   }
		  if (keyCode == KeyEvent.VK_SPACE)
		  {
			player.setFiring (true);
			sound.getBulletSound().play();
			
		  }
		    
    		if ( keyCode == KeyEvent.VK_ESCAPE && Running == true ) {
		     Esc = true;
			 Running = false ;
			 
		    }
			if ( keyCode == KeyEvent.VK_S ) {
			stop = true;
			}
			if ( keyCode == KeyEvent.VK_R ) {
			rerun = true;
			}
		}
		
		
	}
    public void keyReleased (KeyEvent key) 
	{
	    
		if ( Running || stop )
		{
		    int  keyCode = key.getKeyCode ( ) ;
	    	if (keyCode == KeyEvent.VK_LEFT )
		    {
			player.setLeft (false );
		    }
		    if (keyCode == KeyEvent.VK_RIGHT )
		   {
			player.setRight (false );
		   }
		   if (keyCode == KeyEvent.VK_UP )
		   {
			player.setUp (false );
		    }
		   if (keyCode == KeyEvent.VK_DOWN )
		   {
			player.setDown (false );
	  	   }
	    	if (keyCode == KeyEvent.VK_SPACE)
		   {
			player.setFiring (false);
			
		}
		
		}
	}
	
 

 }