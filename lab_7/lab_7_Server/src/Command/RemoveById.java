package Command;

import Object.*;
import TCPServer.CollectionManager;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * удалить элемент из коллекции по его id.
 */
public class RemoveById extends Command {
    private ArrayList<String> loginAndPassword;
    public RemoveById(CollectionManager manager, ArrayList<String> loginAndPassword) {
        super(manager);
        this.loginAndPassword = loginAndPassword;
        setDescription("удалить элемент из коллекции по его id.");
    }

    @Override
    public String execute(Object args) { return getManager().removeById((Integer) args, loginAndPassword.get(0));}
}
