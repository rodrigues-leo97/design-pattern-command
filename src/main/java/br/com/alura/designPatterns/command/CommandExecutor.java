package br.com.alura.designPatterns.command;

public class CommandExecutor {

    public void executeCommand(Command command) {
        command.execute();
    }
}
