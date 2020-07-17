/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author Asus
 */
public class Audio {
    public static String soundPoint = "./audio/point.wav";    
    public static String soundWing="./audio/wing.wav";
    public static AudioInputStream audioInputStream=null ;
    public static Clip POINT=null;
    public static Clip WING=null;
    public static void loadPointAudio(){
        try{
            audioInputStream=AudioSystem.getAudioInputStream(new File(soundPoint).getAbsoluteFile());
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            POINT = (Clip) AudioSystem.getLine(info);
            POINT.open(audioInputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public static void loadSwingAudio(){
        try{
            audioInputStream=AudioSystem.getAudioInputStream(new File(soundWing).getAbsoluteFile());
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            WING = (Clip) AudioSystem.getLine(info);
            WING.open(audioInputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
