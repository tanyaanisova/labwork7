package Command;

import TCPServer.CollectionManager;

import Object.*;

import java.util.ArrayList;
import java.util.TreeSet;

public class AddIfMax extends Command {
    private ArrayList<String> loginAndPassword;
    public AddIfMax(CollectionManager manager, ArrayList<String> loginAndPassword) {
        super(manager);
        this.loginAndPassword = loginAndPassword;
        setDescription("добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.");
    }

    @Override
    public String execute(Object args) {
        return getManager().addIfMax((StudyGroup) args, loginAndPassword.get(0));
    }
}
