package Command;

import Object.*;
import TCPServer.CollectionManager;
import java.util.TreeSet;

/**
 * вывести количество элементов, значение поля groupAdmin которых больше заданного.
 */
public class CountGreaterThanGroupAdmin extends Command {
    public CountGreaterThanGroupAdmin(CollectionManager manager) {
        super(manager);
        setDescription("вывести количество элементов, значение поля groupAdmin которых больше заданного.");
    }

    @Override
    public String execute(Object args) {
        return getManager().countGreaterThanGroupAdmin((Person) args);
    }
}
