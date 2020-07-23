package Command;

import TCPServer.CollectionManager;
import Object.*;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * добавить новый элемент с заданным ключом.
 */
public class Add extends Command {
    private ArrayList<String> loginAndPassword;
    public Add(CollectionManager manager, ArrayList<String> loginAndPassword) {
        super(manager);
        this.loginAndPassword = loginAndPassword;
        setDescription("добавить новый элемент.");
    }

    @Override
    public String execute(Object args) {
        return getManager().add((StudyGroup) args, loginAndPassword.get(0));
    }
}
