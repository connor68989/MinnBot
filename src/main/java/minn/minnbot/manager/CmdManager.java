package minn.minnbot.manager;

import minn.minnbot.entities.Command;
import minn.minnbot.entities.Logger;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class CmdManager {

    protected AtomicReference<String> err;
    protected List<String> errors;
    protected Logger logger;
    private List<Command> commands = new LinkedList<>();
    private Map<Command, Boolean> disable = new HashMap<>();

    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    public List<String> getErrors() {
        if (errors == null) {
            return Collections.unmodifiableList(new LinkedList<>());
        }
        return Collections.unmodifiableList(errors);
    }

    public void shuffle() {
        Collections.shuffle(commands);
    }

    protected String registerCommand(Command command) {
        if (command == null) {
            return "NullPointerException";
        }
        commands.add(command);
        disable.put(command, false);
        return "";
    }

    public void call(MessageReceivedEvent event) {
        Thread t = new Thread(() -> commands.parallelStream().forEach(c -> {
            if (!disable.get(c)) c.onMessageReceived(event);
        }));
        if (logger != null)
            t.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler) logger);
        t.start();
    }

    public void callAsync(MessageReceivedEvent event, Consumer<Boolean> consumer) {
        Thread t = new Thread(() -> commands.parallelStream().forEach(c -> {
            if (!disable.get(c)) c.onMessageReceived(event);
        }));
        if (logger != null)
            t.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler) logger);
        t.start();
        if (consumer != null) {
            consumer.accept(true);
        }
    }

    public void disable(Command cmd, boolean input) {
        if (disable.containsKey(cmd)) {
            disable.replace(cmd, disable.get(cmd), input);
        }
    }

}
