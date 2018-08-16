
import java.awt.*;

public class PowerUp {
   // Fields
    private double x;
    private double y;
    private int r;
    private Color color1;
    private int Type ;
     
    // 1 -- +1  life
   // 2 -- +1 power
   // 3-- +2 power
   // 4 -- slow down time
   // Constructor
    public PowerUp (int Type , double x , double y ) {
        this.Type = Type;
        this.x = x;
        this.y = y; 
        
        if (Type == 1 ){
         color1 = Color.PINK;
         r = 4;
        }
        if (Type== 2 ){
            color1 = Color.YELLOW;
            r = 4;
        }
        if (Type == 3 ){
          color1 = Color.YELLOW;
          r = 5;
        }
         
        if ( Type == 4){
		   color1 = Color.WHITE;
		   r = 3;
			
		}
    }
    
    // Functions
    public double getx() {return x ;}
    public double gety() {return y ;}
    public double getr() {return r ;}

    public int getType() {return Type ;}

    public boolean  update() {
    
   	 y += 2 ;
       
       if ( y > GamePanel.HEIGHT + r ) 
	   {
            return true;
        }
		
     	return false;
    } 

    public void draw (Graphics2D g ){
        g.setColor (color1);
        g.fillRect ((int)(x - r ) , (int ) ( y - r) , 2 * r ,2 * r );
        g.setStroke(new BasicStroke(3));
        g.setColor (color1.darker());
        g.drawRect ((int)(x - r ) , (int ) ( y - r) , 2 * r ,2 * r );
        g.setStroke(new BasicStroke(1));
    }

}