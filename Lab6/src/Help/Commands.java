package Help;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Commands {
    SHOW("\nshow;\n\tвывести в стандартный поток вывода всех " +
            "существ коллекции в строковом представлении\n", new String[]{}),
    EXIT("\nexit;\n\tвыход с сохранением\n", new String[]{}),
    INFO("\ninfo;\n\tвывести в стандартный поток вывода информацию " +
            "о коллекции (тип, дата \n\tинициализации, количество существ)\n",
            new String[]{}),
    SAVE("\nsave;\n\tсохранить коллекцию в файл\n", new String[]{}),
    HELP("\nhelp;\n\tпоказать этот текст\n", new String[]{}),
    ADD("\nadd {element};\n\tдобавить новое существо с заданным именем\n\tпример:" +
            "\n\tadd {\n" +
            "\t            \"element\":{\n" +
            "\t               \"name\":\"cat\",\n" +
            "\t               \"age\":4,\n" +
            "\t               \"tall\":38,\n" +
            "\t               \"iq\":\"0\",\n" +
            "\t               \"location\":{\n" +
            "\t                   \"x\":59,\n" +
            "\t                   \"y\":30,\n" +
            "\t                 }\n" +
            "\t              }\n" +
            "\t         };\n", new String[]{"creature"}),
    ADD_IF_MAX("\nadd_if_max {element};\n\tдобавить новое существо в коллекцию, если его значение\n\tбольше, чем у наибольшего существа этой коллекции(сравнение по возрасту)\n\tпример:" +
            "\n\tadd_if_max {\n" +
            "\t            \"element\":{\n" +
            "\t               \"name\":\"cat\",\n" +
            "\t               \"age\":4,\n" +
            "\t               \"tall\":38,\n" +
            "\t               \"iq\":\"0\",\n" +
            "\t               \"location\":{\n" +
            "\t                   \"x\":59,\n" +
            "\t                   \"y\":30,\n" +
            "\t                 }\n" +
            "\t              }\n" +
            "\t         };\n", new String[]{"element"}),
    REMOVE("\nremove {Int index | element}; \n\tудалить существо из коллекции по его индексу или по значению\n\tпример: " +
            "\n\tremove {\n" +
            "\t              \"index\":\"0\"\n" +
            "\t            }\n", new String[]{"index"}),
    REMOVE_LAST("\nremove_last; \n\tудалить последний элемент\n\tпример: " +
            "\n\tremove_last;", new String[]{});


    private static final String commandNames = Arrays.asList(values()).stream().map(e -> String.format("<%s>,", e.name())).collect(Collectors.joining());
    private static String help = ADD_IF_MAX.manual + EXIT.manual + HELP.manual + REMOVE.manual + REMOVE_LAST.manual + ADD.manual + INFO.manual + SHOW.manual + SAVE.manual;
    private String manual;
    private String[] parameterNames;

    Commands(String _manual, String[] _parameterNames) {
        manual = _manual;
        parameterNames = _parameterNames;
    }

    public static boolean check(String command) {
        return commandNames.contains(String.format("<%s>",command.toUpperCase()));
    }

    public static String help() {
        return help;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }
}

