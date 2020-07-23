package TCPClient;

import Object.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Класс для отправки данных на сервер.
 */

public class TCPSender {

    private NewStudyGroup new_study_group = new NewStudyGroup();
    private String hostname;
    private boolean access;
    private ArrayList<String> loginAndPassword;
    private int port;
    private ArrayList<File> scripts = new ArrayList<>();

    public TCPSender(String hostname, int port, boolean access, ArrayList<String> loginAndPassword) {
        this.hostname = hostname;
        this.port = port;
        this.access = access;
        this.loginAndPassword = loginAndPassword;
    }

    public boolean isAccess() {
        return access;
    }

    public void checker(String[] command) {
        StudyGroup studyGroup;
        int id;
        if (command[0].equals("update_id")) {
            if (command.length == 2) {
                try {
                    id = Integer.parseInt(command[1]);
                    studyGroup = new_study_group.newGroup();
                    sender(command[0] + " " + command[1], studyGroup);
                } catch (NumberFormatException ex) {
                    System.out.println("Аргумент не является значением типа Integer");
                }
            } else System.out.println("Комманда некорректна.");
        } else if (command[0].equals("add") || command[0].equals("add_if_max") || command[0].equals("add_if_min")) {
            studyGroup = new_study_group.newGroup();
            sender(command[0], studyGroup);
        } else if (command[0].equals("remove_by_id")) {
            if (command.length == 2) {
                try {
                    id = Integer.parseInt(command[1]);
                    sender(command[0], id);
                } catch (NumberFormatException ex) {
                    System.out.println("Аргумент не является значением типа Integer");
                }
            } else System.out.println("Комманда некорректна.");
        } else if (command[0].equals("count_by_group_admin") || command[0].equals("count_greater_than_group_admin")) {
            Person groupAdmin = new_study_group.newPerson();
            sender(command[0], groupAdmin);
        } else if (command[0].equals("execute_script")) {
            if (command.length == 2) {
                if (!command[1].equals("")) {
                    File file1 = new File(command[1]);
                    if (!file1.exists())
                        System.out.println("Файла с таким названием не существует.");
                    else if (!file1.canRead())
                        System.out.println("Файл защищён от чтения. Невозможно выполнить скрипт.");
                    else if (scripts.contains(file1)) {
                        System.out.println("Могло произойти зацикливание при исполнении скрипта: " + command[1] + "\nКоманда не будет выполнена. Переход к следующей команде");
                    } else {
                        sender(command[0], "mew");
                        scripts.add(file1);
                        try (BufferedReader commandReader = new BufferedReader(new FileReader(file1))) {
                            String line = commandReader.readLine();
                            while (line != null) {
                                checker(line.split(" "));
                                line = commandReader.readLine();
                            }
                        } catch (IOException ex) {
                            System.out.println("Невозможно считать скрипт");
                        }
                        scripts.remove(scripts.size() - 1);
                    }
                }
            } else System.out.println("Команда введена некорректно.");
        }
        else {
            sender(command[0], "mew");
        }
    }
    private void sender(Object object, Object argument){
        try {
            ArrayList <Object> listObject = new ArrayList<>();
            listObject.add(access);
            listObject.add(loginAndPassword);
            listObject.add(object);
            listObject.add(argument);
            Socket socket = new Socket(hostname, port);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(listObject);
            byte[] outcoming = baos.toByteArray();
            socket.getOutputStream().write(outcoming);
            oos.close();
            baos.close();
            TCPReceiver receiver = new TCPReceiver(socket);
            access = receiver.receiver();
            socket.close();
        }catch (IOException e){
            System.out.println("Проблемы с передачей на сервер...");
        }
    }
}
