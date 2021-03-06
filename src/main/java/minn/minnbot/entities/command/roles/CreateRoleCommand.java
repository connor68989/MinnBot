package minn.minnbot.entities.command.roles;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;
import minn.minnbot.util.EmoteUtil;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.PermissionUtil;

public class CreateRoleCommand extends CommandAdapter {

    public CreateRoleCommand(Logger logger, String prefix) {
        this.prefix = prefix;
        this.logger = logger;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.isPrivate()) {
            return;
        }
        if(isCommand(event.getMessage().getContent())) {
            if(!PermissionUtil.checkPermission(event.getAuthor(), Permission.MANAGE_ROLES, event.getGuild()))
                event.getChannel().sendMessageAsync("You are not allowed to manage roles. " + EmoteUtil.getRngThumbsdown(), null);
            else {
                logger.logCommandUse(event.getMessage());
                onCommand(new CommandEvent(event));
            }
        }
    }

    @Override
    public void onCommand(CommandEvent event) {
       if(!PermissionUtil.checkPermission(event.event.getJDA().getSelfInfo(), Permission.MANAGE_ROLES, event.event.getGuild())) {
           event.sendMessage("I am unable to manage roles. " + EmoteUtil.getRngThumbsdown());
           return;
       }
        event.event.getGuild().createRole().setName(((event.allArguments.isEmpty()) ? "new role" : event.allArguments)).update();
        event.sendMessage(":thumbsup::skin-tone-" + ((int)Math.ceil(Math.random() * 5)) + ":");
    }

    @Override
    public boolean isCommand(String message) {
        String[] p = message.split(" ",2);
        return p.length > 0 && p[0].equalsIgnoreCase(prefix + "createrole");
    }

    @Override
    public String usage() {
        return "`createrole <name>` or just `createrole`";
    }

    @Override
    public String getAlias() {
        return "createrole <name>";
    }

    @Override
    public String example() {
        return "createrole Mods";
    }
}
