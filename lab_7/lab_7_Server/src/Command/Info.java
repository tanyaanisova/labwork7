package Command;

import TCPServer.CollectionManager;

/**
 * вывод информации о коллекции в стандартный поток вывода (тип, дата инициализации, количество элементов и т.д.)
 */
public class Info extends Command{
    public Info(CollectionManager manager)
    {
        super(manager);
        setDescription("вывод информации о коллекции в стандартный поток вывода.");
    }

    @Override
    public String execute(Object args) {
        return "Laba's maker is Anisova Tatyana!\n" + getManager().toString();
    }
}
