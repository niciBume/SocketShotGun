import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by nicol on 21.03.2017.
 */
public class ShotGunMain {
    private BufferedReader in;
    private PrintWriter out;
    private BufferedWriter bufferedWriter;

    public static void main(String[] args) throws IOException {
        ShotGunMain shotGunMain = new ShotGunMain();
        shotGunMain.connectToServer();
    }

    public void connectToServer() throws IOException {
        // Get the server address from a dialog box.
        String serverAddress = "virt14.ethz.ch";
        String response = " ";
        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 1500);

        response = read(socket);
        if (response.startsWith("Hello")) {
            System.out.println("starting authentication");
            write(socket, "Auth bee2fl3");
            System.out.println(read(socket));
        }
    }


    public String read(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String response = in.readLine();
        System.out.println(response);
        return response;
    }

    public void write(Socket socket, String message) throws IOException {
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(message);
        System.out.println(message);
    }

}
