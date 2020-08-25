package Server;

import Data.Ship;
import Help.Command;
import Help.Commands;
import org.json.JSONException;

import java.io.IOException;
import java.security.InvalidParameterException;

public class SCP {

    public static String select(Ship ship, Command command, String defaultFileName) throws InvalidParameterException, IOException {
        switch (command.getName()) {
            case "add":
                try {
                    ship.add(command.getObject());
                    return "Item added\n";
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the name and the object to add\n");
                } catch (InvalidParameterException e) {
                    throw new InvalidParameterException("Failed to initialize object: " + e.getMessage());
                }
            case "show":
                return ship.show();
            case "save":
                return ship.save(defaultFileName) ? "File saved" : "File not saved";
            case "add_if_max":
                try {
                    return ship.addIfMax(command.getObject()) ? "Item added" : "Item not added";
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the object to add\n");
                } catch (InvalidParameterException e) {
                    throw new InvalidParameterException("Failed to initialize objects: \n" + e.getMessage());
                }
            case "remove":
                try {
                    if (command.getIndex() != null)
                        ship.remove(command.getIndex());
                    else if (command.getObject() != null)
                        ship.remove(command.getObject());
                    else
                        throw new InvalidParameterException("Wrong parameters for loading");
                    return "Item removed";
                } catch (InvalidParameterException e) {
                    return e.getMessage();
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the name of the object to be deleted.\n");
                }
            case "info":
                return ship.info();
            case "remove_last":
                try {
                    if (ship.getSize() > 0) {
                        ship.remove(ship.getSize()-1);
                        return "Item removed";
                    }else return "Collection is empty";
                } catch (JSONException e) {
                    throw new InvalidParameterException(e.getMessage());
                } catch (NullPointerException e) {
                    throw new InvalidParameterException("Specify the name\n");
                }
            case "help":
                return Commands.help();
            case "exit":
                ship.save(defaultFileName);
                return "File saved, bye...";
            default:
                throw new InvalidParameterException("not found");

        }
    }
}
