package Default;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Commands {
    SHOW("", new String[]{}),
    EXIT("", new String[]{}),
    INFO("", new String[]{}),
    HELP("", new String[]{}),
    ADD("", new String[]{"creature"}),
    ADD_IF_MAX("", new String[]{"element"}),
    REMOVE("", new String[]{"index"}),
    REMOVE_LAST("", new String[]{}),
    LOGIN("", new String[]{}),
    REGISTER("", new String[]{}),
    CLEAR("", new String[]{});


    private static final String commandNames = Arrays.asList(values()).stream().map(e -> String.format("<%s>,", e.name())).collect(Collectors.joining());
    private String manual;
    private String[] parameterNames;

    Commands(String _manual, String[] _parameterNames) {
        manual = _manual;
        parameterNames = _parameterNames;
    }

    public static boolean check(String command) {
        return commandNames.contains(String.format("<%s>",command.toUpperCase()));
    }

    public String[] getParameterNames() {
        return parameterNames;
    }
}