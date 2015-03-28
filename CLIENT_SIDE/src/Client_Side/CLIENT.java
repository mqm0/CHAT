/*The MIT License (MIT)

Copyright (c) 2015 Muhammad Alhadi B.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/

package Client_Side;
import static Client_Side.CLIENT_GUI.conversation;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author muhammad
 */
public class CLIENT implements Runnable{
    
   Socket SOCK;
   Scanner INPUT;
   //Scanner SEND= new Scanner(System.in);
   PrintWriter OUT;
   
   public CLIENT(Socket x){
       this.SOCK= x;
   }

   
    public void run() {
        try {
            try {
                INPUT= new Scanner(SOCK.getInputStream());
                OUT= new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();
                
            } finally {
                SOCK.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void DISCONNECT() throws IOException{
        OUT.println(CLIENT_GUI.UserName+ " has disconnected");
        OUT.flush();
        SOCK.close();
        JOptionPane.showMessageDialog(null, "You disconnected");
        System.exit(0);
    }
    
    public void RECEIVE(){
        if(INPUT.hasNext()){
            String MESSAGE= INPUT.nextLine();
            
            if(MESSAGE.contains("#?!")){
                String TMP1= MESSAGE.substring(3);
                TMP1= TMP1.replace("[", "").replace("]", "");
                
                String[] CurrentUsers= TMP1.split(", ");
                for(int i= 0;i< CurrentUsers.length;i++){
                    if(CurrentUsers[i].equals(CLIENT_GUI.UserName))
                        CurrentUsers[i]= "<"+CurrentUsers[i]+">";
                    
                }
                CLIENT_GUI.online.setListData(CurrentUsers);
                
            
            }else
                
                CLIENT_GUI.conversation.append(MESSAGE.
                        replace(":)", "☺").
                        replace("><", "ᕙ(⇀‸↼‶)ᕗ").
                        replace("<3", "❤").
                        replace("3_3", "(♥‿♥)").
                        replace(":*", "ƪ(♥ﻬ♥)ʃ").
                        replace(":'(", "(╥_╥)").
                        replace("-_-", "(-̮̮̃-̃)")+"\n");
                conversation.setCaretPosition(conversation.getText().length());
                playSound();
                
        }
    }
    
    public void SERV(String Service){
        OUT.println(CLIENT_GUI.UserName+ ": "+ Service);
        OUT.flush();
    }
    
    public void SEND_PRIVATE(String X){
        //OUT.println(CLIENT_GUI.UserName+ ": "+ X);
        OUT.println(X);
        OUT.flush();
        PRIVATE_MESSAGE.msg.setText("");
        
    }
    
    public void SEND(String X){
        OUT.println(CLIENT_GUI.UserName+ ": "+ X);
        //OUT.println(X);
        OUT.flush();
        CLIENT_GUI.message.setText("");
        
    }
    

    
    public void CheckStream(){
        while (true) {            
            RECEIVE();
        }
    }
    
    public void playSound() {
    try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("/home/muhammad/NetBeansProjects/IRC_CLIENT_INTERFACE/src/bip.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        System.out.println("Error with playing sound.");
    }
}
}
