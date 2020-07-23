package Command;

import TCPServer.CollectionManager;
import Object.*;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * добавить новый элемент.
 */
public class AddIfMin extends Command {
    private ArrayList<String> loginAndPassword;
    public AddIfMin(CollectionManager manager, ArrayList<String> loginAndPassword) {
        super(manager);
        this.loginAndPassword = loginAndPassword;
        setDescription("добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.");
    }

    @Override
    public String execute(Object args) {
        return getManager().addIfMin((StudyGroup) args, loginAndPassword.get(0));
    }
}