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


import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author muhammad
 */
public class SERVER_CLASS {
    
    //Lists where to stock clients by their sockets in different chat rooms 
    public static ArrayList<Socket> ConnectionRoomOne= new ArrayList<>();
    public static ArrayList<Socket> ConnectionRoomTwo= new ArrayList<>();
    public static ArrayList<Socket> ConnectionRoomThree= new ArrayList<>();
    
    //Lists where to stock clients by their usernames in different chat rooms 
    public static ArrayList<String> CurrentUsersOne= new ArrayList<>();
    public static ArrayList<String> CurrentUsersTwo= new ArrayList<>();
    public static ArrayList<String> CurrentUsersThree= new ArrayList<>();
    
    //Define room's numbers
    public static final int ROOM_ONE= 1;
    public static final int ROOM_TWO= 2;
    public static final int ROOM_THREE= 3;

    
    public static void main(String[] args) throws Exception {
       
        try {
            //Define chat rooms by different ports
            ServerSocket SERVER_1= new ServerSocket(1025);
            ServerSocket SERVER_2= new ServerSocket(1026);
            ServerSocket SERVER_3= new ServerSocket(1027);
            
            System.out.println("Waiting for clients...");
            
            //Starts 3 different threads, for 3 chat rooms
            new channels(SERVER_1, ROOM_ONE).start();
            new channels(SERVER_2, ROOM_TWO).start();
            new channels(SERVER_3, ROOM_THREE).start();
            
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }
    
    
    
}

//Class of channels "Chat Rooms" 
class channels extends Thread{
    
    
   
    ServerSocket SERVER= null;
    int room;
    
    //Constructor of a thread
    public channels(ServerSocket SERVER, int room){
        this.SERVER= SERVER;
        this.room= room;
    }
    
    @Override
    public void run(){
        
        while(true){
            try {
                
                //Create a socket for every client connects to the chat room
                Socket SOCK= SERVER.accept();
                
                
                //Stock of sockets and usernames for every connection in different chat rooms
                switch (room){
                    case SERVER_CLASS.ROOM_ONE:{
                        SERVER_CLASS.ConnectionRoomOne.add(SOCK);
                        
                        System.out.println("Client connected from :"+SOCK.getLocalAddress().getHostName());

                        AddUserName(SOCK, room);

                        Thread X= new Thread(new ServerReturn(SOCK, room));
                        X.start();
                    }break;
                    case SERVER_CLASS.ROOM_TWO:{
                        SERVER_CLASS.ConnectionRoomTwo.add(SOCK);
                
                        System.out.println("Client connected from :"+SOCK.getLocalAddress().getHostName());

                        AddUserName(SOCK, room);

                        Thread X= new Thread(new ServerReturn(SOCK, room));
                        X.start();
                    }break;
                    case SERVER_CLASS.ROOM_THREE:{
                        SERVER_CLASS.ConnectionRoomThree.add(SOCK);
                
                        System.out.println("Client connected from :"+SOCK.getLocalAddress().getHostName());

                        AddUserName(SOCK, room);

                        Thread X= new Thread(new ServerReturn(SOCK, room));
                        X.start();
                    }break;
                }
                
            } catch (IOException ex) {
                Logger.getLogger(channels.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
    }
    
    
    //Updating of list of online users by defusing the current online users
    public static void RefreshListUsers(Socket TEMP_SOCK, int room) throws IOException{
        switch(room){
            case SERVER_CLASS.ROOM_ONE:{
                PrintWriter OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                OUT.println("#?!"+ SERVER_CLASS.CurrentUsersOne);
                OUT.flush();
            }break;
            case SERVER_CLASS.ROOM_TWO:{
                PrintWriter OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                OUT.println("#?!"+ SERVER_CLASS.CurrentUsersTwo);
                OUT.flush();
            }break;
            case SERVER_CLASS.ROOM_THREE:{
                PrintWriter OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                OUT.println("#?!"+ SERVER_CLASS.CurrentUsersThree);
                OUT.flush();
            }break;
        }
                
        
    }
    
    /*  Capture of coming messages which contains usernames
        from every client who connect then stock the username in server.
        finally check for closed sockets and removing them then refreshing 
        the list of current usernames
    */
    public static void AddUserName(Socket Sock, int room)throws IOException{
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date= new Date();
        
        Scanner INPUT= new Scanner(Sock.getInputStream());
        
        String UserName= INPUT.nextLine();
        UserName= UserName.replace("/join ", "");
        switch (room){
            case SERVER_CLASS.ROOM_ONE:{
                SERVER_CLASS.CurrentUsersOne.add(UserName);
                
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomOne.get(i-1).isClosed()){
                        SERVER_CLASS.ConnectionRoomOne.remove(i-1);
                        SERVER_CLASS.CurrentUsersOne.remove(i-1);
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1), SERVER_CLASS.ROOM_ONE);
                    }
                        
                    else
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1), SERVER_CLASS.ROOM_ONE);
                }
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomOne.size(); i++){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomOne.get(i-1);
                    if(!SERVER_CLASS.CurrentUsersOne.get(i-1).equals(UserName)){
                        PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println("["+dateFormat.format(date)+"]"+UserName+" has joined the chat");
                        TEMP_OUT.flush();
                    }else{
                        PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println("["+dateFormat.format(date)+"]"+"Welcome to chat "+UserName);
                        TEMP_OUT.flush();
                    }
                }
                
            }break;
            case SERVER_CLASS.ROOM_TWO:{
                SERVER_CLASS.CurrentUsersTwo.add(UserName);
                
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomTwo.get(i-1).isClosed()){
                        SERVER_CLASS.ConnectionRoomTwo.remove(i-1);
                        SERVER_CLASS.CurrentUsersTwo.remove(i-1);
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1), SERVER_CLASS.ROOM_TWO);
                    }
                        
                    else
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1), SERVER_CLASS.ROOM_TWO);
                }
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomTwo.size(); i++){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomTwo.get(i-1);
                    if(!SERVER_CLASS.CurrentUsersTwo.get(i-1).equals(UserName)){
                        PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println("["+dateFormat.format(date)+"]"+UserName+" has joined the chat");
                        TEMP_OUT.flush();
                    }else{
                        PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println("["+dateFormat.format(date)+"]"+"Welcome to chat "+UserName);
                        TEMP_OUT.flush();
                    }
                }
            }break;
            case SERVER_CLASS.ROOM_THREE:{
                SERVER_CLASS.CurrentUsersThree.add(UserName);
                
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                    if(SERVER_CLASS.ConnectionRoomThree.get(i-1).isClosed()){
                        SERVER_CLASS.ConnectionRoomThree.remove(i-1);
                        SERVER_CLASS.CurrentUsersThree.remove(i-1);
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1), SERVER_CLASS.ROOM_THREE);
                    }
                        
                    else
                        RefreshListUsers((Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1), SERVER_CLASS.ROOM_THREE);
                }
                for(int i= 1; i<= SERVER_CLASS.ConnectionRoomThree.size(); i++){
                    Socket TEMP_SOCK= (Socket) SERVER_CLASS.ConnectionRoomThree.get(i-1);
                    if(!SERVER_CLASS.CurrentUsersThree.get(i-1).equals(UserName)){
                        PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println("["+dateFormat.format(date)+"]"+UserName+" has joined the chat");
                        TEMP_OUT.flush();
                    }else{
                        PrintWriter TEMP_OUT= new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println("["+dateFormat.format(date)+"]"+"Welcome to chat "+UserName);
                        TEMP_OUT.flush();
                    }
                }
            }break;
        }
        
    
    }
}
