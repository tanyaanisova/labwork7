package Command;

import Object.*;
import TCPServer.CollectionManager;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * обновить значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command{
    private int id;
    private ArrayList<String> loginAndPassword;
    public Update(CollectionManager manager, Integer id, ArrayList<String> loginAndPassword) {
        super(manager);
        this.loginAndPassword = loginAndPassword;
        this.id = id;
        setDescription("обновить значение элемента коллекции, id которого равен заданному.");
    }

    @Override
    public String execute(Object args) {return getManager().update(id, (StudyGroup) args, loginAndPassword.get(0));}
}