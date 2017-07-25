import com.sun.org.apache.bcel.internal.classfile.Unknown;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main extends JFrame implements Runnable {

    static private Socket connection;
    static private ObjectInputStream objectInputStream;
    static private ObjectOutputStream objectOutputStream;

    public static void main (String[] args) {
        new Main("Test");
    }

    public Main (String name) {
        super(name);
        setLayout(new FlowLayout());
        setSize(300, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void run() {

        try {
            connection = new Socket(InetAddress.getByName("127.0.0.2"), 333);

            while (true) {
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

    private static void sendData (Object object) {
        try {
            objectOutputStream.flush();
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
