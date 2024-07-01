
package radius_area_fx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField tfRadius = new TextField();
        tfRadius.setPromptText("Enter radius");
        Button btnSend = new Button("Send");
        TextArea ta = new TextArea();
        ta.setEditable(false);
        
        VBox vBox = new VBox(10, tfRadius, btnSend, new ScrollPane(ta));
        Scene scene = new Scene(vBox, 450, 200);
        
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        btnSend.setOnAction(e -> {
            try (Socket socket = new Socket("localhost", 8000)) {
                DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
                DataInputStream fromServer = new DataInputStream(socket.getInputStream());

                double radius = Double.parseDouble(tfRadius.getText().trim());
                toServer.writeDouble(radius);

                double area = fromServer.readDouble();
                
                Platform.runLater(() -> {
                    ta.appendText("Radius sent to server: " + radius + '\n');
                    ta.appendText("Area received from server: " + area + '\n');
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}