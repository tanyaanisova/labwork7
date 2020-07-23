package TCPServer;

import Object.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InteractionBD {
    private final TreeSet<StudyGroup> groups = new TreeSet<>();
    private Logger LOGGER = Logger.getLogger(InteractionBD.class.getName());
    private final Connection connection;
    private MessageDigest md = null;
    private String pepper = "*63&^mVLC(#";

    public InteractionBD(Connection connection) {
        this.connection = connection;
    }

    {
        try {
            md = MessageDigest.getInstance("SHA-224");
        } catch (NoSuchAlgorithmException e) {
        }
    }

    /**
     * Получение коллекции из БД
     *
     */

     public TreeSet<StudyGroup> load() {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM groups");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float x = resultSet.getFloat("Coordinates_x");
                long y = resultSet.getInt("Coordinates_y");
                ZonedDateTime creationDate = resultSet.getTimestamp("creationDate").toLocalDateTime().atZone(ZoneId.systemDefault());
                int studentsCount = resultSet.getInt("studentsCount");
                long expelledStudents = resultSet.getInt("expelledStudents");
                Float averageMark = resultSet.getFloat("averageMark");
                Semester semester = Semester.valueOf(resultSet.getString("semester"));
                String namePerson = resultSet.getString("Person_name");
                double height = resultSet.getFloat("Person_height");
                int weight = resultSet.getInt("Person_weight");
                double locationX = resultSet.getFloat("Location_x");
                long locationY = resultSet.getInt("Location_y");
                int locationZ = resultSet.getInt("Location_z");
                String locationName = resultSet.getString("Location_name");
                groups.add(new StudyGroup(id, name, new Coordinates(x, y), creationDate, studentsCount, expelledStudents, averageMark, semester, new Person(namePerson, height, weight, new Location(locationX, locationY, locationZ, locationName))));
            }
            resultSet.close();
            return groups;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Коллекция не может быть загружена. Файл некорректен");
            System.exit(1);
        }
        return null;
    }

    /**
     * Выполнения комманд с БД
     */
    public int selectYourId(String login) throws SQLException {
        PreparedStatement getUserId = connection.prepareStatement("SELECT id FROM users WHERE login = ?");
        getUserId.setString(1, login);
        ResultSet resultSet = getUserId.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    public ArrayList<Integer> selectYourObjects(String login) throws SQLException {
        int userId = selectYourId(login);
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM groups WHERE user_id = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Integer> ids = new ArrayList<>();
        while (resultSet.next()) {
            ids.add(resultSet.getInt("id"));
        }
        return ids;
    }

    public void remove(Integer id) throws SQLException {
        synchronized (connection) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM groups WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
        }
    }

    public ArrayList<Integer> clear(String login) throws SQLException {
        int userId = selectYourId(login);
        ArrayList<Integer> ids = new ArrayList<>(selectYourObjects(login));
        synchronized (connection) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM groups WHERE user_id = ?");
            statement.setInt(1, userId);
            statement.execute();
        }
        return ids;
    }

    public void add(StudyGroup studyGroup, String login) throws SQLException {
        int id = selectYourId(login);
        synchronized (connection) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO groups VALUES(nextval('sequence_id'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, studyGroup.getName());
            statement.setFloat(2, studyGroup.getCoordinates().getX());
            statement.setInt(3, Math.toIntExact(studyGroup.getCoordinates().getY()));
            statement.setTimestamp(4, Timestamp.valueOf(studyGroup.getCreationDate().toLocalDateTime()));
            statement.setInt(5, studyGroup.getStudentsCount());
            statement.setInt(6, (int) studyGroup.getExpelledStudents());
            statement.setFloat(7, studyGroup.getAverageMark());
            statement.setString(8, String.valueOf(studyGroup.getSemesterEnum()));
            statement.setString(9, studyGroup.getGroupAdmin().getName());
            statement.setFloat(10, studyGroup.getGroupAdmin().getHeight().floatValue());
            statement.setInt(11, (int) studyGroup.getGroupAdmin().getWeight());
            statement.setFloat(12, (float)studyGroup.getGroupAdmin().getLocation().getX());
            statement.setInt(13, (int) studyGroup.getGroupAdmin().getLocation().getY());
            statement.setInt(14, studyGroup.getGroupAdmin().getLocation().getZ());
            statement.setString(15, studyGroup.getGroupAdmin().getLocation().getName());
            statement.setInt(16, id);
            statement.execute();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT currval('sequence_id')");
            resultSet.next();
            studyGroup.setId(id);
            resultSet.close();
        }
    }

    public void update(Integer id, StudyGroup studyGroup, String login) throws SQLException {
        int userId = selectYourId(login);
        synchronized (connection) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM groups WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.println(" ");
            if (userId == resultSet.getInt("user_id")) {
                statement = connection.prepareStatement("UPDATE groups SET name = ?, Coordinates_x = ?, Coordinates_y = ?, studentsCount = ?, expelledStudents = ?, averageMark = ?, semester = ?, Person_name = ?, Person_height = ?, Person_weight = ?, Location_x = ?, Location_y = ?, Location_z = ?, Location_name= ? WHERE id = ?");
                statement.setString(1, studyGroup.getName());
                statement.setFloat(2, studyGroup.getCoordinates().getX());
                statement.setInt(3, Math.toIntExact(studyGroup.getCoordinates().getY()));
                statement.setInt(4, studyGroup.getStudentsCount());
                statement.setInt(5, (int) studyGroup.getExpelledStudents());
                statement.setFloat(6, studyGroup.getAverageMark());
                statement.setString(7, String.valueOf(studyGroup.getSemesterEnum()));
                statement.setString(8, studyGroup.getGroupAdmin().getName());
                statement.setFloat(9, studyGroup.getGroupAdmin().getHeight().floatValue());
                statement.setInt(10, (int) studyGroup.getGroupAdmin().getWeight());
                statement.setFloat(11, (float) studyGroup.getGroupAdmin().getLocation().getX());
                statement.setInt(12, (int) studyGroup.getGroupAdmin().getLocation().getY());
                statement.setInt(13, studyGroup.getGroupAdmin().getLocation().getZ());
                statement.setString(14, studyGroup.getGroupAdmin().getLocation().getName());
                statement.setInt(15, id);
                statement.execute();
            }
        }
    }

    /**
     *регистрация
     */

    public String registration(String login, String password) throws SQLException {
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        String salt = new String(array, StandardCharsets.UTF_8);
        byte[] hash = md.digest((pepper + password + salt).getBytes());
        String passwordPlus = new String(hash, StandardCharsets.UTF_8);
        synchronized (connection) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement = connection.prepareStatement("INSERT INTO users VALUES(nextval('sequence_user_id'), ?, ?, ?)");
                statement.setString(1, login);
                statement.setString(2, passwordPlus);
                statement.setString(3, salt);
                statement.execute();
                return "Вы успешно зарегистрировались!";
            } else return "Пользователь с таким логином уже существует. Введите данные снова:";
        }
    }

    /**
     *вход
     */

    public boolean enter(String login, String password) throws SQLException {
        synchronized (connection) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            byte[] hash = md.digest((pepper + password + resultSet.getString("salt")).getBytes());
            String password_plus = new String(hash, StandardCharsets.UTF_8);
            return password_plus.equals(resultSet.getString("password"));
        }
    }
}
