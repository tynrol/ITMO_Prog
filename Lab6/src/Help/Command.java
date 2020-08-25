package Help;

        import Data.Midget;

        import java.io.Serializable;
        import java.security.InvalidParameterException;

public class Command implements Serializable {

    protected String name;
    protected Integer index;
    protected Midget object;

    public Command(String _name, Integer _index, Midget _object) throws InvalidParameterException {
        if (!Commands.check(_name))
            throw new InvalidParameterException("\n");
        name = _name;
        index = _index;
        object = _object;
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

    @Override
    public String toString() {
        return String.format("\nCommand: %s\nindex: %d\nObject: %s\n", this.name, this.index, this.object);
    }
}