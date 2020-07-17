/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Audio;
import Entity.Bird;
import Entity.Pipe;
import Entity.Pipe_Double;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class BluePanel extends javax.swing.JPanel {

    /**
     * Creates new form BluePanel
     */
    static int WIDTH;
    static int HEIGHT;
    static final int NUM_REC=4;
    
    List<Pipe_Double> pipes;
    Graphics g;
    public static boolean isPlaying=false;
    Bird bird;
    Thread t1;
    Thread tCheck;
    Thread tBird;
    Random r= new Random(System.currentTimeMillis());
    ControlPanel controlPanel;
    
    
    
    public BluePanel(ControlPanel controlPanel) {
        this.controlPanel=controlPanel;
        this.setSize(1500,1500);
        WIDTH=this.getWidth();
        HEIGHT=this.getHeight();
        init();
        createBird();
        initComponents();
        startGame(this.isPlaying);
    }
    public void startGame(boolean isPlaying){
        if(isPlaying==true){
            
            init();
            controlPanel.setPoint(0);
            createBird();
            thread();
            t1.start();
            tBird.start();
            tCheck.start();
        }
    }
   
    public void init(){
        
        try {
            pipes=new LinkedList<>();
            Pipe_Double pipe_Double= new Pipe_Double(Color.GREEN,70,HEIGHT,WIDTH,r);
            pipes.add(pipe_Double);
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    public void createBird(){
        
       //bird= new Bird("bluebird-midflap.png",100,300,20,20);
       //bird= new Bird("bluebird-downflap.png",100,300,20,20);
        bird= new Bird("./img/yellowbird-downflap.png","./img/yellowbird-midflap.png","./img/yellowbird-upflap.png",150,300,50,50);
    }
    @Override
    protected void paintComponent(Graphics g) {
        WIDTH=this.getWidth();
        HEIGHT=this.getHeight();
        super.paintComponent(g);
        this.setBackground(new Color(51,204,255));//To change body of generated methods, choose Tools | Templates.
        drawPipes(g);//To change body of generated methods, choose Tools | Templates.
        drawBird(g);
    }
    public void drawBird(Graphics g){
        bird.DrawBird(g);
    }
   
    public void drawPipes(Graphics g){
        
        Iterator<Pipe_Double> iter=pipes.iterator();
        
        while(iter.hasNext()){
            Pipe_Double p=iter.next();
            p.drawPipes(g);
        }

    }
    // tao thread de dich chuyen cac pipe ve ben trai
    
    private void thread(){
        t1= new Thread(new Runnable() {
        @Override
        public void run() {
            while(isPlaying){
                
                ListIterator<Pipe_Double> iter=pipes.listIterator();
                while(iter.hasNext()){
                    Pipe_Double p=iter.next();
                    p.getUp().x=p.getUp().x-1;
                    p.getDown().x=p.getDown().x-1;
                }

                // ham nay goi ham paint trong ham paint co ham ve lai==> ve lai theo x moi
                repaint();
                List<Integer> listIndexRemove=new Vector<>();
                List<Pipe_Double> listPipesAdd= new Vector<>();
                iter=pipes.listIterator();
                int i=0;
                while(iter.hasNext()){
                    
                    Pipe_Double p=iter.next();
                    if(p.getUp().x+p.getUp().width==0){
                        //Pipe_Double pNew= new Pipe_Double(Color.GREEN,50, HEIGHT,WIDTH, r);
                        listIndexRemove.add(i);
                    }
                    if(WIDTH-p.getUp().x==400){
                        Pipe_Double pNew= new Pipe_Double(Color.GREEN,70, HEIGHT,WIDTH, r);
                        listPipesAdd.add(pNew);
                    }
                    if(p.getUp().intersects(bird)||p.getDown().intersects(bird)){
                        isPlaying=false;  
                    }
                    if(bird.x==p.getUp().x+p.getUp().width){
                        Audio.loadPointAudio();
                        Audio.POINT.start();
                        controlPanel.setPoint(controlPanel.getPoint()+1);
                       
                    }
                    i++;
                }
                for (Integer k : listIndexRemove) {
                    pipes.remove(k);
                }
                pipes.addAll(listPipesAdd);

                try{
                    Thread.sleep(5);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(bird.y<0||bird.y>HEIGHT){
                    isPlaying=false;
                }
                
            }
        }
    });
    tCheck= new Thread(new Runnable() {
        boolean check=true;
        @Override
        
        public void run() {
            while(check){
                if(isPlaying==false){
                    check=false;
                    JOptionPane.showMessageDialog(null,"Lose");
                    break;
                }
                
                try{
                    Thread.sleep(1);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            }
        }
    });
    tBird= new Thread(new Runnable() {
        @Override
        public void run() {
            while(isPlaying){
                bird.y=bird.y+2;
                try{
                    Thread.sleep(9);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 755, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        
        if(evt.getKeyCode()==KeyEvent.VK_UP&&isPlaying==true){
            bird.y=bird.y-60;
            Audio.loadSwingAudio();
            Audio.WING.start();
        }
    }//GEN-LAST:event_formKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
