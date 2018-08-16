
import java.awt.*;

   public class Player  {
// Fields
	 private int x ;
	 private int y ;
	 private int r ;
	 private int dx ;
	 private int dy ;
	 private int Speed ;

	 private boolean LEFT;
	 private boolean RIGHT;
     private boolean UP;
     private boolean DOWN;	
	 
	 private boolean firing ;
	 private long firingTimer;
	 private long firingDelay ;

     private boolean recovering ;
     private long recoveringTimer;
      
	 
	 private Color color1;
	 private Color color2;
	 
	 private int Lives;  
     private int Score;
     
     private int powerLevel;
     private int power;
     private int [] requiredPower = {
            1, 2, 3 ,4 , 5,6,7,8,9
        };
     

// Constructor 
   public Player (){
	x = GamePanel.WIDTH / 2;
    y = GamePanel.HEIGHT / 2;
    r = 7;
	
	dx =0; 
	dy =0;
	Speed = 5;
	
	Lives = 3;
     color1=Color.WHITE ;
     color2=Color.RED ;	
	 firing = false ;
     firingTimer = System.nanoTime();
     firingDelay = 200 ;
 
     recovering = false;
     recoveringTimer = 0;
     Score = 0;
   }
   
// Functions
  
  public int getx() {return x;}
  public int gety() {return y ;}
  public int getr() {return r;}
   
  public int getScore (){return Score;}

  public int getLives() {return Lives ;}

  public boolean isDead(){return Lives <= 0;}
  
  public boolean isRecovering() {return recovering;}
  
  public void setLeft (boolean b ) {LEFT = b ;}
  public void setRight (boolean b ) {RIGHT = b ;}
  public void setUp (boolean b ) {UP = b ;}
  public void setDown (boolean b ) {DOWN = b ;}
  
  public void setFiring (boolean b) { firing = b;}

  public void addScore (int i ) {Score += i ;}

  public void gainLife() {Lives++;}


  public void loseLife (){
    Lives --;
   recovering = true;
   recoveringTimer = System.nanoTime();

}
    public void increasePower(int i){
     power += i;
	    if (powerLevel == 8){
			if (power > requiredPower[powerLevel] ){
				power = requiredPower[powerLevel];
			}
			return;
		}
     if ( power >= requiredPower [powerLevel]){
          power -= requiredPower[powerLevel];
          powerLevel++;
        }
    }

    public int getPowerLevel() {return powerLevel;}
    public int getPower() {return power;}
    public int getRequiredPower() {return requiredPower[powerLevel];}

    
  
  public void update (){
	   if (LEFT)
	   {
		dx = - Speed ;
	   }
	   if (RIGHT)
	   {
		dx =  Speed ;   
	   }
	   if (UP)
	   {
		dy = - Speed ;   
	   }
	   if (DOWN)
	   {
		dy = Speed ;  
	   }  
	   
	   x += dx;
	   y += dy;
	   if (x < r ) { x = r; }
	   if (y < r ) { y = r;}
       if (x > GamePanel.WIDTH - r )  { x =GamePanel.WIDTH - r; }
	   if (y > GamePanel.HEIGHT - r )  { y =GamePanel.HEIGHT - r; }
	   
	   dx = 0 ;
	   dy = 0 ;

       // firing
	   if (firing){
		   long elapsed = (System.nanoTime()-firingTimer ) / 1000000 ;
		   if (elapsed > firingDelay ){
			   firingTimer = System.nanoTime();

              if (powerLevel < 2 ){
                    GamePanel.bullets.add (new Bullet (270 , x , y));
                }
               else if (powerLevel < 3 ) {
                    GamePanel.bullets.add (new Bullet (270 , x + 5, y));
                    GamePanel.bullets.add (new Bullet (270 , x - 5, y));
                }
                else if (powerLevel < 4) {
                    GamePanel.bullets.add (new Bullet (270 , x ,   y));
                    GamePanel.bullets.add (new Bullet (275 , x + 5, y));
                    GamePanel.bullets.add (new Bullet (265 , x - 5, y));

                }
				else if (powerLevel < 5) {
                    GamePanel.bullets.add (new Bullet (270 , x ,   y));
                    GamePanel.bullets.add (new Bullet (275 , x + 5, y));
                    GamePanel.bullets.add (new Bullet (265 , x - 5, y));
                    GamePanel.bullets.add (new Bullet (260 , x - 7, y));
					
				}
				else if (powerLevel < 6) {
                    GamePanel.bullets.add (new Bullet (270 , x ,   y));
                    GamePanel.bullets.add (new Bullet (275 , x + 5, y));
                    GamePanel.bullets.add (new Bullet (265 , x - 5, y));
                    GamePanel.bullets.add (new Bullet (260 , x - 10, y));
					GamePanel.bullets.add (new Bullet (255 , x +10 , y));
				}
				else  {
                    GamePanel.bullets.add (new Bullet (270 , x ,   y));
                    GamePanel.bullets.add (new Bullet (275 , x + 5, y));
                    GamePanel.bullets.add (new Bullet (265 , x - 5, y));
                    GamePanel.bullets.add (new Bullet (260 , x - 10, y));
					GamePanel.bullets.add (new Bullet (255 , x + 10, y));
					GamePanel.bullets.add (new Bullet (250 , x - 14, y));
					GamePanel.bullets.add (new Bullet (250 , x + 14, y));
				}
               
			   
		   }
	   }
       
       if (recovering ){
	        long elapsed =(System.nanoTime()- recoveringTimer ) / 1000000;
            if (elapsed > 2000 ) {
                recovering = false;
                recoveringTimer =0 ;
            }
        }
	   
   }   
   
   public void draw(Graphics2D g){
     if (recovering) {
           g.setColor (color2);
	       g.fillOval (x - r , y - r , 2 * r , 2 * r );
	 
	       g.setStroke (new BasicStroke(3));
	       g.setColor (color2.darker ());
	       g.drawOval (x -r , y - r , 2 * r , 2 * r );
	       g.setStroke(new BasicStroke (1));
	
        }
        else {
      
	      g.setColor (color1);
	      g.fillOval (x - r , y - r , 2 * r , 2 * r );
	 
	      g.setStroke (new BasicStroke(3));
	      g.setColor (color1.darker ());
	      g.drawOval (x -r , y - r , 2 * r , 2 * r );
	      g.setStroke(new BasicStroke (1));
	    }

    }
	
	
	
	
	
}