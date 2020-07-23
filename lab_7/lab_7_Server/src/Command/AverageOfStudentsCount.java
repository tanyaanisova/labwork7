package Command;

import TCPServer.CollectionManager;
import Object.*;
import java.util.TreeSet;

/**
 *  вывести среднее значение поля studentsCount для всех элементов коллекции.
 */
public class AverageOfStudentsCount extends Command {
    public AverageOfStudentsCount(CollectionManager manager) {
        super(manager);
        setDescription("вывести среднее значение поля studentsCount для всех элементов коллекции.");
    }

    @Override
    public String execute(Object args) { return getManager().averageOfStudentsCount(); }
}
