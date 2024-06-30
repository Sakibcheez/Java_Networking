/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatApp;


import java.util.Scanner;

public class Writer implements Runnable{
    public NetworkConnection netConnection;
    
    public Writer(NetworkConnection nc){
        netConnection=nc;
    }
    
//    public void WriteMessage(String msg){
//        
//    }
    
    @Override
    public void run() {
        
        Data data=new Data();
        
        while(true){
            
            Scanner in=new Scanner(System.in);
            
            data.message=in.nextLine();            
            try{
                netConnection.write(data.clone());
            }
            catch(Exception ex){
                System.out.println("sending failed");
            }
        }
    }
    
}