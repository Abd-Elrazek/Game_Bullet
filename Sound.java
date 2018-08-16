
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.applet.*;


 public class Sound {
	
     // Fields
	  private	URL Bullet_Sound = this.getClass().getResource("Sound//Bullet_Sound.wav");
	  private URL Explosion_Sound = this.getClass().getResource("Sound//Explosion_Sound.wav");
	  private  URL  GameOver_Sound = this.getClass().getResource("Sound//GameOver_Sound.wav");
	    private  URL  Power_Sound = this.getClass().getResource("Sound//Power_Sound.wav");
		
	   // private	URL Bullet_Sound = this.getClass().getClassLoader().getResource("Sound/Bullet_Sound.wav");
       // private URL Explosion_Sound = this.getClass().getClassLoader().getResource("Sound/Explosion_Sound.wav");
	    // private  URL  GameOver_Sound = this.getClass().getClassLoader().getResource("Sound/GameOver_Sound.wav");
	    // private  URL  Power_Sound = this.getClass().getClassLoader().getResource("Sound/Power_Sound.wav");
		
		private AudioClip Bullet_S ;
		private AudioClip Explosion_S;
		private AudioClip GameOver_S;
		private AudioClip Power_S;
	
	 // Constructor
	 public Sound (){
	   		Bullet_S = Applet.newAudioClip(Bullet_Sound);
			Explosion_S = Applet.newAudioClip(Explosion_Sound);
			GameOver_S = Applet.newAudioClip(GameOver_Sound);
	        Power_S = Applet.newAudioClip(Power_Sound);
		}
	 
	 // Functions
	    public AudioClip getBulletSound (){return Bullet_S;}
	    public AudioClip getExplosionSound(){return Explosion_S;}
	    public AudioClip getGameOverSound(){return GameOver_S;}	 
		public AudioClip getPowerSound() {return Power_S;} 
	 
	 
	 
	 
	 
	 
	 
    }