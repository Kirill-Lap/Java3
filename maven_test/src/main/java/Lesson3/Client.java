package Lesson3;

import java.io.*;
import java.net.Socket;

class ImportantInfo implements Serializable {
    String theme;
    String info;
    String author;
    int importencyLevel;

    public ImportantInfo(String theme, String info, String author, int importencyLevel) {
        this.theme = theme;
        this.info = info;
        this.author = author;
        this.importencyLevel = importencyLevel;
    }

    public void Info(){
        System.out.println("Тема: " + theme + " Текст: " + info + "\n Автор: "+ author + " важность=" + importencyLevel);
    }
}


public class Client {


    public static void main(String[] args) {

        ImportantInfo info = new ImportantInfo("Срочно в номер", "Что-то произошло", "редактор", 5);

        Socket socket;
        final String IP_ADRESS = "localhost";
        final int PORT = 8189;
        DataOutputStream out;

        try {
            socket = new Socket(IP_ADRESS, PORT);
            out = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(info);
            oos.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
