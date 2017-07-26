import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Server implements Runnable {

    static private Socket connection;
    static private ObjectInputStream objectInputStream;
    static private ObjectOutputStream objectOutputStream;

    public void run() {
        try {
            while (true) {
                connection = new Socket(InetAddress.getByName("127.0.0.2"), 333);
                objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
                objectInputStream = new ObjectInputStream(connection.getInputStream());
                JOptionPane.showMessageDialog(null, (String) objectInputStream.readObject());
            }
        } catch (IOException e ) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
