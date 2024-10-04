/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_ntc2;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatServer extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter output;
    private BufferedReader input;
    private ServerSocket server;
    private Socket socket;

    public ChatServer() {
        // Create UI
        setTitle("Chat Server");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(e.getActionCommand());
                inputField.setText("");
            }
        });
        
        setVisible(true);
        
        // Networking setup
        try {
            server = new ServerSocket(5000);  // Port number 5000
            chatArea.append("Waiting for connection...\n");
            socket = server.accept();  // Accept connection
            chatArea.append("Client connected\n");
            
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            
            while (true) {
                String message = input.readLine();
                if (message != null) {
                    chatArea.append("Client: " + message + "\n");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        chatArea.append("Server: " + message + "\n");
        output.println(message);  // Send message to client
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}