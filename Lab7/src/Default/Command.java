package Default;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class Command implements Serializable {

    protected String name;
    protected Integer index;
    protected Midget object;
    protected String login;
    protected String password;
    protected String email;

    public Command(String name, Integer index, Midget object, String login, String password, String email) throws InvalidParameterException {
        if (!Commands.check(name)) throw new InvalidParameterException("\n");
        this.name = name;
        this.index = index;
        this.object = object;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public Integer getIndex() {
        return index;
    }
    public Midget getObject() {
        return object;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("\nDefault.Command: %s\nindex: %d\nObject: %s\n", this.name, this.index, this.object);
    }
}
