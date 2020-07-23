package Command;

import Object.*;
import TCPServer.CollectionManager;
import java.util.TreeSet;

/**
 * вывести количество элементов, значение поля groupAdmin которых равно заданному.
 */
public class CountByGroupAdmin extends Command {
    public CountByGroupAdmin(CollectionManager manager) {
        super(manager);
        setDescription("вывести количество элементов, значение поля groupAdmin которых равно заданному.");
    }

    @Override
    public String execute(Object args) {
        return getManager().countByGroupAdmin((Person) args);
    }
}