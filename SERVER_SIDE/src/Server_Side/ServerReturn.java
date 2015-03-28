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

package Server_Side;

import static Server_Side.channels.RefreshListUsers;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author muhammad
 */
public class ServerReturn implements Runnable{
    
    final Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE= "";
    final int room;
    
           
    public ServerReturn(Socket sock, int room){
        this.room= room;
        this.SOCK= sock;
    }
    
    //Checking of non connected sockets and removing them, then refresh
    public void CheckConnection() throws IOException{
        
        switch (room){
            case SERVER_CLASS.ROOM_ONE:{
                if(!SOCK.isConnected()){  
                    
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomOne.get(i-1) == SOCK){
                        SERVER_CLASS.ConnectionRoomOne.remove(i-1);
                        SERVER_CLASS.CurrentUsersOne.remove(i-1);
                        Refresh();
                    }
                }
        
            }else
                Refresh();
            }break;
            case SERVER_CLASS.ROOM_TWO:{
                if(!SOCK.isConnected()){  
                    
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomTwo.get(i-1) == SOCK){
                        SERVER_CLASS.ConnectionRoomTwo.remove(i-1);
                        SERVER_CLASS.CurrentUsersTwo.remove(i-1);
                        Refresh();
                    }
                }
        
            }else
                Refresh();
            }break;
            case SERVER_CLASS.ROOM_THREE:{
                if(!SOCK.isConnected()){  
                    
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomThree.get(i-1) == SOCK){
                        SERVER_CLASS.ConnectionRoomThree.remove(i-1);
                        SERVER_CLASS.CurrentUsersThree.remove(i-1);
                        Refresh();
                    }
                }
        
            }else
                Refresh();
            }break;
        }
    }

    //Refresh the lists of curent usernames
    public void Refresh() throws IOException{
        
        switch(room){
            case SERVER_CLASS.ROOM_ONE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomOne.get(i-1).isClosed()){
                        SERVER_CLASS.ConnectionRoomOne.remove(i-1);
                        SERVER_CLASS.CurrentUsersOne.remove(i-1);
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1), SERVER_CLASS.ROOM_ONE);
                    }else
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1), SERVER_CLASS.ROOM_ONE); 
          
                }
            }
            case SERVER_CLASS.ROOM_TWO:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomTwo.get(i-1).isClosed()){
                        SERVER_CLASS.ConnectionRoomTwo.remove(i-1);
                        SERVER_CLASS.CurrentUsersTwo.remove(i-1);
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1), SERVER_CLASS.ROOM_TWO);
                    }else
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1), SERVER_CLASS.ROOM_TWO); 
            }
            }
            case SERVER_CLASS.ROOM_THREE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomThree.get(i-1).isClosed()){
                        SERVER_CLASS.ConnectionRoomThree.remove(i-1);
                        SERVER_CLASS.CurrentUsersThree.remove(i-1);
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1), SERVER_CLASS.ROOM_THREE);
                    }else
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1), SERVER_CLASS.ROOM_THREE); 
                }
        }
        }
        
    }
    
    //Defusing of messages in chat rooms to all connected users
    public void Difusion(String MSG) throws IOException{
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date= new Date();
        
        switch(room){
            case SERVER_CLASS.ROOM_ONE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println("["+dateFormat.format(date)+"]"+MSG);
                    TEMP_OUT.flush();

                    System.out.println("Sent to : "+TEMP_SOCK.getLocalAddress().getHostName()); 
                }
            }break;
            case SERVER_CLASS.ROOM_TWO:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println("["+dateFormat.format(date)+"]"+MSG);
                    TEMP_OUT.flush();

                    System.out.println("Sent to : "+TEMP_SOCK.getLocalAddress().getHostName()); 
                }
            }break;
            case SERVER_CLASS.ROOM_THREE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println("["+dateFormat.format(date)+"]"+MSG);
                    TEMP_OUT.flush();

                    System.out.println("Sent to : "+TEMP_SOCK.getLocalAddress().getHostName()); 
                }
            }break;
        }
        
    }
    
    //Sending of private messages
    public void SendPriv(String MSG) throws IOException{
        int index= 3;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date= new Date();
        
        String[] message= MSG.split(" ");
        String To= message[2];
        MSG= "["+dateFormat.format(date)+"]"+message[1];
        while(index< message.length){
           
                MSG+= message[index]+" ";
            
            index++;
        }
        
        switch(room){
            case SERVER_CLASS.ROOM_ONE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                if(SERVER_CLASS.CurrentUsersOne.get(i-1).equals(To)){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(MSG);
                    TEMP_OUT.flush();
                }
            
                }
            }break;
            case SERVER_CLASS.ROOM_TWO:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                if(SERVER_CLASS.CurrentUsersTwo.get(i-1).equals(To)){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(MSG);
                    TEMP_OUT.flush();
                }
            
                }
            }break;
            case SERVER_CLASS.ROOM_THREE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                if(SERVER_CLASS.CurrentUsersThree.get(i-1).equals(To)){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(MSG);
                    TEMP_OUT.flush();
                }
            
                }
            }break;
        }
        
    }
    
    //Sending actual time to ones who request
    public void Service(String MSG) throws IOException{
        
        String[] message= MSG.split(":");
        String dest= message[0];
        
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date= new Date();
       
        switch(room){
            case SERVER_CLASS.ROOM_ONE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                if(SERVER_CLASS.CurrentUsersOne.get(i-1).equals(dest)){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(dateFormat.format(date));
                    TEMP_OUT.flush();
                }
            
                }
            }break;
            case SERVER_CLASS.ROOM_TWO:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                if(SERVER_CLASS.CurrentUsersTwo.get(i-1).equals(dest)){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(dateFormat.format(date));
                    TEMP_OUT.flush();
                }
            
                }
            }break;
            case SERVER_CLASS.ROOM_THREE:{
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                if(SERVER_CLASS.CurrentUsersThree.get(i-1).equals(dest)){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1);
                    PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                    TEMP_OUT.println(dateFormat.format(date));
                    TEMP_OUT.flush();
                }
            
                }
            }break;
        }
        
        
    }
   
    @Override
    public void run() {

    try {
        try {
            INPUT= new Scanner(SOCK.getInputStream());
            OUT= new PrintWriter(SOCK.getOutputStream());

            while(true){
                
                CheckConnection();
                
                if(!INPUT.hasNext()) return;

                MESSAGE= INPUT.nextLine();
                
                
                System.out.println("Client said "+ MESSAGE);

                switch (room){
                    case 1:{
                        Refresh();
                        if(MESSAGE.contains("/priv"))
                            SendPriv(MESSAGE);
                        else if(MESSAGE.contains("GET_TIME"))
                            Service(MESSAGE);
                        else
                            Difusion(MESSAGE);
                    }break;
                    case 2:{
                        Refresh();
                        if(MESSAGE.contains("/priv"))
                            SendPriv(MESSAGE);
                        else
                            Difusion(MESSAGE);
                    }break;
                    case 3:{
                        Refresh();
                        if(MESSAGE.contains("/priv"))
                            SendPriv(MESSAGE);
                        else
                            Difusion(MESSAGE);
                    }break;
                }

                }

          
        } finally {
            SOCK.close();
        }
        
    } catch (Exception e) {
        System.err.println(e);
    }
    
    
}    
}
