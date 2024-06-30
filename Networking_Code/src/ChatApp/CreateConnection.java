/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatApp;


import java.util.HashMap;

/**
 *
 * @author user
 */
public class CreateConnection implements Runnable{
    
    HashMap<String, Information> clientList;
    NetworkConnection nc;
    public CreateConnection(HashMap<String,Information> cList, NetworkConnection nConnection){
        clientList=cList;
        nc=nConnection;    
    }
        
    
    @Override
    public void run() {
        Object userObj=nc.read();
        String username=(String)userObj;
        
        System.out.println("User : "+username+" connected");
        
        clientList.put(username,new Information(username,nc));
        System.out.println("HashMap updated"+clientList);
        new Thread(new ReaderWriterServer(username,nc,clientList)).start();
        
    }
    
}