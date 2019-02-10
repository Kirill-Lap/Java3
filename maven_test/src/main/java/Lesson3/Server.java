package Lesson3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PipedInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

class ImportantInfoS implements Serializable {
    String theme;
    String info;
    String author;
    int importencyLevel;

    public ImportantInfoS(String theme, String info, String author, int importencyLevel) {
        this.theme = theme;
        this.info = info;
        this.author = author;
        this.importencyLevel = importencyLevel;
    }

    public void Info(){
        System.out.println(theme + " " + info + " "+ author + " importency levele=" + importencyLevel);
    }
}


public class Server {
    public static void main(String[] args) throws ClassNotFoundException {
        ServerSocket server = null;
        Socket socket = null;
//        PipedInputStream pipedIn;
//        pipedIn = new PipedInputStream()
        try{
            server = new ServerSocket(8189);
            System.out.println("Server started.");

            socket = server.accept();
            System.out.println("Client connected");

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ImportantInfo infoS = (ImportantInfo) ois.readObject();
            infoS.Info();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            try{
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
