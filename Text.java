import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Text
{
  private double x;
  private double y;
  private long Time;
  private String s;
  private long Start;
  
  public Text() {}
  
  public Text(double paramDouble1, double paramDouble2, long paramLong, String paramString)
  {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.Time = paramLong;
    this.s = paramString;
    this.Start = System.nanoTime();
  }
  
  public boolean update()
  {
    long l = (System.nanoTime() - this.Start) / 1000000L;
    if (l > this.Time) {
      return true;
    }
    return false;
  }
  
  public void draw(Graphics2D paramGraphics2D)
  {
    paramGraphics2D.setFont(new Font("French Script MT Regular", 0, 15));
    long l = (System.nanoTime() - this.Start) / 1000000L;
    int i = (int)(255.0D * Math.sin(3.14D * l / this.Time));
    if (i > 255) {
      i = 255;
    } else {
      i = 160;
    }
    paramGraphics2D.setColor(new Color(255, 255, 255, i));
    int j = (int)paramGraphics2D.getFontMetrics().getStringBounds(this.s, paramGraphics2D).getWidth();
    paramGraphics2D.drawString(this.s, (int)(this.x - j / 2 + 20.0D), (int)(this.y + 20.0D));
  }
}
