import java.io.*;
import java.net.*;

class Client {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("127.0.0.1", 1201);// server ip and port

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String msgIn = "", msgOut = "";

            while (!msgIn.equals("end")) {
                msgOut = bufferedReader.readLine();
                dataOutputStream.writeUTF(msgOut);

                msgIn = dataInputStream.readUTF();
                System.out.println(msgIn);//Печать серверного сообщения
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
