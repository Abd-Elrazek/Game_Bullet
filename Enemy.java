
import java.awt.*;

public class Enemy {
	// Fields
	private double x ;
	private double y ;
	private int r ;
	
	private double dx ;
	private double dy ;
	private double rad ;
	private double Speed ;
	
	private int health ;
	private int Type ;
	private int rank ;
	
	private Color color1;
	
	private boolean ready;
	private boolean dead;
	
    private boolean hit;
    private long hitTimer;
	
	private boolean slow;
	// Constructor 
	public Enemy (int Type , int rank){
		
		this.Type =Type ;
        this.rank = rank ;
        // Default enemy
      if (Type == 1){
		   // color1= Color.GREEN;
           color1 = new Color ( 100 , 255 , 0,255  );
		  if (rank == 1){
			  Speed =2;
			  r = 3;
			  health = 1;
		    }
	        if ( rank ==2 ) {
             Speed = 2;
             r = 10;
             health = 2;
            }
            if ( rank == 3 ) {
               Speed = 1.5;
               r = 20;
               health = 3;
            }
            if (rank == 4 ){
               Speed = 1.5;
               r = 30;
               health = 4 ;

            }
	    }
        // stronger , faster default
        if (Type == 2 ) {
            // color1 = Color.RED;
            color1 = new Color ( 255 , 0 , 0 , 120 );
            if (rank == 1){
               Speed= 3;
               r = 5 ;
               health = 2;
            }
            if ( rank == 2 ){
               Speed = 3;
               r = 5;
               health = 2;
            }
            if (rank == 3) {
               Speed = 2.5;
               r = 20 ;
               health = 3;
            }
            if ( rank == 4 ){

               Speed = 2.5;
               r = 30;
               health = 4;
            }

        }

        // slow , but hard to kill
        if (Type == 3){
           // color1 = Color.GREEN;
            color1 = new Color (100, 255 , 0 ,128);
            if (rank == 1 ) {
             Speed = 1.5;
             r = 5;
             health = 3;
            }
            if (rank == 2 ) {
               Speed = 1.2;
               r = 10;
               health = 4;
            }
            if ( rank == 3) {
                Speed = 1.5;
                r = 25;
                health = 5;
            }
            if ( rank == 4) {
                Speed = 1.5;
                r = 45;
                health = 5 ;
            }
        }
       
		
	  x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4 ;
	  y = -r ;
	  double angle = Math.random() *  140 + 20;
	  rad = Math.toRadians (angle);
	  
	  dx = Math.cos (rad) * Speed;
	  dy = Math.sin (rad) * Speed ;
	  
	  ready = false ;
	  dead = false ;
	  hit = false;
      hitTimer = 0;
	}
	// Functions
	
	public double getx() {return x ;}
	public double gety() {return y; }
	public int getr() {return r ;}
 
    public int getType() {return Type; }
    public int getRank() {return rank;}
	
	public boolean isDead () {return dead;}
	public void setSlow (boolean b) {slow = b;}
	public void hit (){
		health--;
		if (health <= 0){
			dead = true ;
		}
        hit = true;
	}

    public void explode (){
      if ( rank > 1 ){
          int amount = 0 ;
          if ( Type == 1 ){
             amount = 3;
            }
            if ( Type == 2 ) {
                amount = 3;
            }
            if ( Type == 3 ) {
                amount = 4;
            }
            

            for ( int i = 0; i < amount; i++ ){
                Enemy e = new Enemy (getType(), getRank() - 1 );
                e.setSlow (slow);
				e.x = this.x;
                e.y = this.y;
                double angle = 0;
                if  (!ready ) {
                    angle = Math.random() * 140 + 20 ;
                }
                else 
                {
                 angle = Math.random() * 360;

                }
                e.rad = Math.toRadians (angle );
                GamePanel.enemies.add(e);
            }
        }
    }
	
	public void update (){
		if (slow){
			x += dx * 0.3;
			y += dy * 0.3;
		}
		else {
			x += dx;
		    y +=dy;  
		}
		
		
		if (!ready){
			if (x > r && x < GamePanel.WIDTH - r 
			&&  y > r && y < GamePanel.HEIGHT - r ){
				ready = true ;
				
			}
			
		}
		if (x < r && dx < 0) dx = -dx;
        if (y < r && dy < 0)  dy = - dy;
		if (x > GamePanel.WIDTH - r && dx > 0 ) dx = - dx;
		if (y > GamePanel.HEIGHT - r && dy > 0 ) dy = -dy ;
        if (hit) {
            long elapsed = (System.nanoTime() - hitTimer ) / 1000000;
            if (elapsed > 50 ) {
            hit = false;
            hitTimer = 0 ;
            }
        }
	}  
	
	public void draw (Graphics2D g){
        if (hit) {
            g.setColor(Color.WHITE);
		    g.fillOval ((int) ( x - r ) , (int) (y - r ) , 3 * r , 3 * r ) ;
		
		    g.setStroke (new BasicStroke(3));
		    g.setColor(Color.WHITE.darker());
		    g.drawOval ((int) (x - r) , (int ) ( y - r ) , 3 * r , 3 * r);
            g.setStroke(new BasicStroke(1));	
        } 
        else {
		    g.setColor(color1);
		    g.fillOval ((int) ( x - r ) , (int) (y - r ) , 3 * r , 3 * r ) ;
		
		    g.setStroke (new BasicStroke(3));
		    g.setColor(color1.darker());
		    g.drawOval ((int) (x - r) , (int ) ( y - r ) , 3 * r , 3 * r);
            g.setStroke(new BasicStroke(1));		
		}
	}
	
	
	
	
}