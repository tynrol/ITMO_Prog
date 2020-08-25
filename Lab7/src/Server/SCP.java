package Server;

import Default.Command;
import Default.Commands;
import Default.MD2;
import Default.Ship;
import org.json.JSONException;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;

public class SCP {

    public static String select(Ship ship, Command command) throws InvalidParameterException, IOException {
        switch (command.getName()) {
            case "add":
                try {
                    return DB.addMidget(command.getObject(), command.getLogin(), command.getPassword());
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the object to add\n");
                } catch (InvalidParameterException e) {
                    throw new InvalidParameterException("Failed to initialize objects: \n" + e.getMessage());
                }
            case "show":
                return DB.showTable(command.getLogin(),command.getPassword());
            case "add_if_max":
                try {

                    return DB.addIfMax(command.getObject(),command.getLogin(),command.getPassword());
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the object to add\n");
                } catch (InvalidParameterException e) {
                    throw new InvalidParameterException("Failed to initialize objects: \n" + e.getMessage());
                }
            case "remove":
                try {
                    if (DB.remove(command.getObject(),command.getLogin(),command.getPassword()).equals("OK")) return "Item removed\n";
                    else return "Item wasn't removed due to some problems";
                } catch (InvalidParameterException e) {
                    return e.getMessage();
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the name of the object to be deleted.\n");
                }
            case "info":
                return DB.info(command.getLogin(),command.getPassword());
            case "help":
                return  "login - Авторизироваться\n" +
                        "register - Зарегистрироваться\n" +
                        "add {element} - Добавить карлика в базу данных\n" +
                        "add_if_max {element} - Добавить карлика если он самый крутой B)\n" +
                        "remove {element} - Убрать карлинка из коллекции\n" +
                        "clear - Удалить всех СВОИХ карликов\n" +
                        "show - Показать все элементы базы данных\n" +
                        "info - Вывести информацию базе данных\n" +
                        "help - Вывести этот текст\n" +
                        "element:\n" +
                        "\t{\"element\":{\n" +
                        "               \"name\":\"jopka\",\n" +
                        "               \"age\":4,\n" +
                        "               \"tall\":38,\n" +
                        "               \"iq\":\"1\",\n" +
                        "               \"location\":{\n" +
                        "                   \"x\":59,\n" +
                        "                   \"y\":30,\n" +
                        "               }\n" +
                        "        }\n" +
                        "\t};";
            case "exit":
                return "Bye...";
            case "login":
                return DB.checkUser(command.getLogin(), command.getPassword());
            case "register":
                String pass = Password.generate(8);
                String password = MD2.encrypt(pass);
                if (!DB.checkLogin(command.getLogin()).equals("OK")) {
                    MailSender.send(command.getEmail(), command.getLogin(), pass);
                }
                return DB.addUser(command.getLogin(), password, command.getEmail());
            case "clear":
                if (DB.clear(command.getLogin(),command.getPassword()).equals("OK")) return "It's cleared";
                else return "DB wasn't cleared due to some problems";
            default:
                throw new InvalidParameterException("Not found");
        }
    }
}
