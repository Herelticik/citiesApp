package server.commands;


import lib.Answer;
import lib.AnswerType;
import lib.element.CityBuilder;
import lib.exceptions.*;
import lib.element.City;
import lib.interaction.Command;
import server.converter.FileInput;
import lib.interaction.Input;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Класс события исполнения скрипта
 */
public class ExecuteScriptAction extends BinaryAction {
    /**
     * Содержит названия открытых файлов
     */
    private boolean crush;
    private final ArrayList<String> availablePrograms = new ArrayList<>();
    private final Stack<String> openFiles = new Stack<>();
    private final ArrayList<String> message = new ArrayList<>();
    private ArrayList<City> collectionReserveCopy;
    private final ActionInvoker invoker = ActionInvoker.getInstance();
    private final ReentrantLock lock = new ReentrantLock();

    public ExecuteScriptAction() {
        availablePrograms.add("ELEMENT");
        availablePrograms.add("TEST");
    }

    @Override
    public Answer execute(Command command) {
        setParametersOnStart();
        try {
            executeLoop(command);
        } catch (IOException | EnvException | IncorrectArgumentException | IncorrectCommandException |
                 IncorrectCollectionException | DatabaseElementException |
                 ExecuteScriptException e) {
            message.add(openFiles.pop() + ": " + e.getMessage() + "\nНе выполнено!");
            crush = true;
        }
        if (openFiles.size() == 0) {
            rollbackEventsIfError();
            lock.unlock();
            return new Answer(AnswerType.DIALOG_STATE,
                    message.stream()
                            .filter((element) -> !element.equals(""))
                            .collect(Collectors.joining("\n")));
        }
        return notifyAboutResult("");
    }

    public void setParametersOnStart() {
        if (openFiles.size() == 0) {
            lock.lock();
            message.clear();
            crush = false;
            saveCollection();
        }
    }


    public void saveCollection() {
        this.collectionReserveCopy = new ArrayList<>(manager.getCollectionArr());
    }

    public void executeLoop(Command command) throws IOException {
        String cursorFileName = System.getenv(command.getParameter());
        openFiles.push(command.getParameter());
        fileValidation(command.getParameter());
        Input fileReader = getFileScriptReader(cursorFileName);
        while (fileReader.hasNext()) {
            try {
                errorChecking();
            } catch (ExecuteScriptException e) {
                break;
            }
            Command scriptCommand = fileReader.readCommand();
            scriptCommand.setSender(command.getSender());
            Answer answer = invoker.execute(scriptCommand);
            if (isNeedAnElement(answer)) {
                City newElement = new CityBuilder(fileReader.readElement())
                        .creator(command.getSender())
                        .build();
                scriptCommand.setElement(newElement);
                answer = invoker.execute(scriptCommand);
            }
            message.add(answer.getMessage());
        }
        openFiles.pop();
    }

    private void fileValidation(String cursorFileName) {
        boolean isFileRepeat = openFiles.stream().filter((element) -> element.equals(cursorFileName)).count() > 1;
        if (!availablePrograms.contains(cursorFileName)) {
            throw new ExecuteScriptException("Такого скрипта не существует!");
        }
        if (isFileRepeat) {
            throw new ExecuteScriptException("Обнаружено рекурсивное открытие файла!");
        }
    }

    public Input getFileScriptReader(String fileAddress) throws IOException {
        Path file = Paths.get(fileAddress);
        return new FileInput(new Scanner(file));
    }

    private void errorChecking() {
        if (crush) {
            throw new ExecuteScriptException("Произошла ошибка во время исполнения скрипта!");
        }
    }

    private boolean isNeedAnElement(Answer answer) {
        return answer.getType().toString().equals("NEED_ELEMENT");
    }

    private void rollbackEventsIfError() {
        if (crush) {
            manager.updateCollection(collectionReserveCopy);
        }
    }

}

