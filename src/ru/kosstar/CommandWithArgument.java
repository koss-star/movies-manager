package ru.kosstar;

/**
 * Класс для объединения имени команды и ее аргумента
 */
public class CommandWithArgument {
    String name;
    Object argument;

    public CommandWithArgument(String name, Object argument) {
        this.name = name;
        this.argument = argument;
    }

    public String getName() {
        return name;
    }

    public Object getArgument() {
        return argument;
    }
}
