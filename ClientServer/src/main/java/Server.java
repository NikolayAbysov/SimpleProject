import java.io.*;
import java.net.*;


class Server {

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(1201);
            Socket socket = serverSocket.accept();

            //Чтение входящего сообщения
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String msgIn = "", msgOut = "";

            while (!msgIn.equals("end")){
                msgIn = dataInputStream.readUTF();
                System.out.println(msgIn);//Печать клиентского сообщения

                msgOut = bufferedReader.readLine();
                dataOutputStream.writeUTF(msgOut);
                dataOutputStream.flush();
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
