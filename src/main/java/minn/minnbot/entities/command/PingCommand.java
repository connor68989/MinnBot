package minn.minnbot.entities.command;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;
import net.dv8tion.jda.entities.Message;

public class PingCommand extends CommandAdapter {

    public PingCommand(String prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
    }

    @Override
    public void onCommand(CommandEvent event) {
        long ping = System.currentTimeMillis();
        event.channel.sendMessageAsync("**__Ping:__** ", (Message m) -> m.updateMessageAsync("**__Ping:__** **" + (System.currentTimeMillis() - ping) + "ms**", null));
    }

    @Override
    public boolean isCommand(String message) {
        message = message.toLowerCase();
        if (!message.startsWith(prefix))
            return false;
        message = message.substring(prefix.length());
        String command = message.split(" ", 2)[0];
        return command.equalsIgnoreCase("ping");
    }

    @Override
    public String usage() {
        return "Returns ping.";
    }

    @Override
    public String getAlias() {
        return "ping";
    }

    @Override
    public String example() {
        return "ping";
    }

}
