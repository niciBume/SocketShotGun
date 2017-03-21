import java.io.*;
import java.net.Socket;

/**
 * Created by nicol on 21.03.2017.
 */
public class ShotGunMain {

    BufferedReader input;
    BufferedWriter output;
    int GameId;
    int roundCounter;
    String enemyMove = " ";
    boolean goOn = true, enemyIsLoaded = false;

    public ShotGunMain(){
        try {
            // Get the server address from a dialog box.
            String serverAddress = "virt14.ethz.ch";
            // Make connection and initialize streams
            Socket socket = new Socket(serverAddress, 1500);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connectToServer() throws IOException {
        String response = " ";
        response = read();
        if (response.startsWith("Hello")) {
            System.out.println("starting authentication");
            this.write("Auth bee2fl3");
            response = this.read();
            if (response.startsWith("Ack")){
                response = read();
                if (response.startsWith("Game")){
                    String[] GameIDString = response.split("\\s");
                    GameId = Integer.parseInt(GameIDString[1]);
                    System.out.println("GameID: " + GameId);
                    fancyStuff();
                }
            }
        }
    }

    public String read() throws IOException {
        String response = this.input.readLine();
        System.out.println(response);
        return response;
    }

    public void write(String message) throws IOException {
        this.output.write(message);
        this.output.newLine();
        this.output.flush();
        System.out.println(message);
    }

    public boolean myTurn(){
        if (roundCounter % 2 == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void isEnemyLoaded(String enemyMove){
        if (enemyMove.equals("Reload")) enemyIsLoaded = true;
        if (enemyMove.equals("Shoot")) enemyIsLoaded = false;
    }

    public void fancyStuff() throws IOException {
        System.out.println("starting fancy shit");
        write("DoMove " + GameId + " Reload");
        roundCounter++;

        while (goOn){
            if (myTurn()){
                if (enemyIsLoaded) write("DoMove " + GameId + " Protect");
                else {
                    if (!enemyMove.equals("Protect")) write("DoMove " + GameId + " Shoot");
                    else write("DoMove " + GameId + " Reload");
                }
                roundCounter++;
            }

            if (!myTurn()){
                enemyMove = read();
                String[] enemyStuff = enemyMove.split("\\s");
                if (enemyStuff[5].startsWith("Continue")) goOn = true;
                else goOn = false;
                enemyMove = enemyStuff[4];
                isEnemyLoaded(enemyMove);
                roundCounter++;
            }
        }
    }

}
