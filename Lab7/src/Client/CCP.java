package Client;

import Default.Command;
import Default.Commands;
import Default.Midget;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.security.InvalidParameterException;

import static Client.Client.getEmail;
import static Client.Client.getLogin;
import static Client.Client.getPassword;

public class CCP {

    public static Command getCommand(String commandLine) throws IOException {
        int indexOfEnd = -1;
        int indexOfStart = -1;
        boolean i = true;
        char preChar = ' ';
        char c;
        for (int n = 0; n < commandLine.length(); n++) {
            c = commandLine.charAt(n);
            if ((indexOfEnd == -1) && preChar == '}' && c == '{' && i)
                indexOfEnd = n;
            if (((indexOfStart == -1) && (c == '{')) && i)
                indexOfStart = n;
            if ((indexOfStart != -1) && (indexOfEnd != -1))
                break;
            if (c == '"')
                i = !i;
            preChar = c;
        }
        String commandName = (indexOfStart == -1) ? commandLine.replace(" ", "") : commandLine.substring(0, indexOfStart).replace(" ", "");
        String rawParameter = (indexOfStart == -1) ? null : (indexOfEnd == -1) ? commandLine.substring(indexOfStart) : commandLine.substring(indexOfStart, indexOfEnd);
        if (!Commands.check(commandName))
                throw new InvalidParameterException(commandName + "\n");
        String[] commandParameters = Commands.valueOf(commandName.toUpperCase()).getParameterNames();
        if (commandParameters.length != (rawParameter == null ? 0 : 1)) {
            throw new InvalidParameterException("\nWrong parameters for: " + commandName + "\n");
        }
        Integer index = null;
        Midget object = null;
        if (commandParameters.length >= 1) {
            try {
                JSONObject tempObj = new JSONObject(rawParameter);
                if (tempObj.has("index") && commandParameters[0].equals("index")) {
                    index = tempObj.getInt("index");
                } else if (tempObj.has("element")) {
                    object = new Midget(new JSONObject(rawParameter).getJSONObject("element"));
                } else {
                    throw new InvalidParameterException("\nWrong parameters for: " + commandName + "\n");
                }
            } catch (JSONException e) {
                throw new InvalidParameterException(e.getMessage());
            }
        }
        String login = getLogin();
        String password = getPassword();
        String email = getEmail();
        return new Command(commandName, index, object, login, password, email);
    }

    public static String getMultiline(BufferedReader paramBufferedReader) throws IOException {
        StringBuilder mainStringBuilder = new StringBuilder();
        StringBuilder withoutParametersStringBuilder = new StringBuilder();
        boolean isParameter = false;
        int intChar = paramBufferedReader.read();
        if (intChar == 10 || intChar == 13)
            intChar = paramBufferedReader.read();
        char c;
        boolean wasEnd = false;
        boolean startParameters = false;
        for (; ; ) {
            if (wasEnd && (intChar == 10 || intChar == 13))
                break;
            c = (char) intChar;
            if (wasEnd) {
                while (paramBufferedReader.ready())
                    paramBufferedReader.read();
                throw new InvalidParameterException("Nothing can be after \";\"");
            }
            if (c == '"') {
                isParameter = !isParameter;
                mainStringBuilder.append(c);
            } else if (c == ';' && !isParameter) {
                wasEnd = true;
            } else if (((c != ';') && (c != '\n') && (c != '\r') && (c != ' ')) || (isParameter)) {
                mainStringBuilder.append(c);
            }
            if (!startParameters) {
                if (c != '{') {
                    c = c == '\n' ? '*' : c;
                    c = c == '\r' ? '*' : c;
                    withoutParametersStringBuilder.append(c);
                } else {
                    startParameters = true;
                }
            }
            intChar = paramBufferedReader.read();
        }
        return String.format("%s\n%s", mainStringBuilder.toString(), withoutParametersStringBuilder.toString().replace("\n", "^").replace(":", "^").replace("{", "^").replace("}", "^").replace("\"", "^"));

    }

    //getting command out of stream+multiline
    public static Command readCommand(BufferedReader bufferedReader) throws IOException {
        String multilineCommand = getMultiline(bufferedReader);
        int indexOfFullCommandName = multilineCommand.lastIndexOf("\n");
        String commandLine = multilineCommand.substring(0, indexOfFullCommandName); // могу убрать это в getCommand
        if (commandLine.equals("")) {
            throw new InvalidParameterException("Enter command\n");
        }
        Command command = getCommand(commandLine);
        return command;
    }

}