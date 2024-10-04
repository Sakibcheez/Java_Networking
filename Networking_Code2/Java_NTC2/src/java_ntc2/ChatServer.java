/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_ntc2;
import java.io.*;
import java.net.*;

public class ChatServer {
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private BufferedReader consoleInput;

    public ChatServer() {
        try {
            // Create server socket on port 5000
            server = new ServerSocket(5000);
            System.out.println("Waiting for a client to connect...");

            socket = server.accept();
            System.out.println("Client connected.");

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Start a thread to listen for incoming messages from the client
            new Thread(new IncomingReader()).start();

            // Sending messages from the console
            String userMessage;
            while (true) {
                userMessage = consoleInput.readLine();
                if (userMessage != null) {
                    output.println("Server: " + userMessage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread for listening to incoming messages
    private class IncomingReader implements Runnable {
        public void run() {
            String clientMessage;
            try {
                while ((clientMessage = input.readLine()) != null) {
                    System.out.println(clientMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}
