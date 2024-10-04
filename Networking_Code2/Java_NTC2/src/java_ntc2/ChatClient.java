/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_ntc2;
import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private BufferedReader consoleInput;

    public ChatClient(String serverIP) {
        try {
            // Connect to the server on port 5000
            socket = new Socket(serverIP, 5000);
            System.out.println("Connected to the server.");

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Start a thread to listen for incoming messages from the server
            new Thread(new IncomingReader()).start();

            // Sending messages from the console
            String userMessage;
            while (true) {
                userMessage = consoleInput.readLine();
                if (userMessage != null) {
                    output.println("Client: " + userMessage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread for listening to incoming messages
    private class IncomingReader implements Runnable {
        public void run() {
            String serverMessage;
            try {
                while ((serverMessage = input.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String serverIP = "Enter Server IP Address: ";
        new ChatClient(serverIP);
    }
}
