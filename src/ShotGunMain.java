import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nicol on 21.03.2017.
 */
public class ShotGunMain {
    private BufferedReader in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        ShotGunMain shotGunMain = new ShotGunMain();
        shotGunMain.connectToServer();
    }

    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = "virt14.ethz.ch";

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 1500);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            System.out.println(in.readLine());
        }
    }

    public void authenticateToServer(){
        
    }

}
