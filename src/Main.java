import java.io.IOException;

/**
 * Created by nicol on 21.03.2017.
 */
public class Main extends Thread{
    public static void main(String[] args) throws IOException {
        ShotGunMain shotGunMain = new ShotGunMain();
        shotGunMain.connectToServer();

    }
}
