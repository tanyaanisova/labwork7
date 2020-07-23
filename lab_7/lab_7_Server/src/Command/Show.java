package Command;

import TCPServer.CollectionManager;

import java.util.*;
import java.util.stream.Collectors;

import Object.*;

/**
 * вывести в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class Show extends Command {
    public Show(CollectionManager manager) {
        super(manager);
        setDescription("вывести в стандартный поток вывода все элементы коллекции в строковом представлении.");
    }

    @Override
    public String execute(Object args) {
        return getManager().show();
    }
}
