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

public class ChatClient extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter output;
    private BufferedReader input;
    private Socket socket;

    public ChatClient(String serverIP) {
        // Create UI
        setTitle("Chat Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(e.getActionCommand());
                inputField.setText("");
            }
        });
        
        setVisible(true);
        
        // Networking setup
        try {
            socket = new Socket(serverIP, 5000);  // Connect to server on port 5000
            chatArea.append("Connected to server\n");
            
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            
            while (true) {
                String message = input.readLine();
                if (message != null) {
                    chatArea.append("Server: " + message + "\n");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        chatArea.append("Client: " + message + "\n");
        output.println(message);  // Send message to server
    }

    public static void main(String[] args) {
        String serverIP = JOptionPane.showInputDialog("Enter Server IP Address: ");
        new ChatClient(serverIP);
    }
}
