/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import GUI.BluePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;
import javax.swing.ImageIcon;

/**
 *
 * @author Asus
 */
public class Bird extends Rectangle {
    static int i=0;
     Vector<String> imgeVector;
     String img;
    public Bird(String image1,String image2,String image3, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.imgeVector= new Vector<>();
        this.imgeVector.add(image1);
        this.imgeVector.add(image2);
        this.imgeVector.add(image3);
        img=this.imgeVector.get(0);
        t.start();
    }
    Thread t= new Thread(new Runnable() {
        @Override
        public void run() {
            while(BluePanel.isPlaying){
                img=imgeVector.get(i);
                i+=1;
                if(i>2){
                    i=0;
                }
                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    });
    public void DrawBird(Graphics g){
        if(imgeVector!=null||imgeVector.size()>=0){
           
            ImageIcon icon= new ImageIcon(img);
            Image image=icon.getImage();
            g.drawImage(image, x, y,null);
            
        }
    }       
}
