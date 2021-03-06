package minn.minnbot.entities.command.owner;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;

public class DebugCommand extends CommandAdapter {

	public DebugCommand(String prefix, Logger logger) {
		this.prefix = prefix;
		this.logger = logger;		
	}

	@Override
	public void onCommand(CommandEvent event) {
		try {
			event.sendMessage("**__Debug:__** " + logger.toggleDebug());
		} catch (Exception e) {
			event.sendMessage("Encountered Exception: " + e.getMessage());
		}
	}

	@Override
	public boolean isCommand(String message) {
		message = message.toLowerCase();
		return message.equals(prefix + "toggledebug") || message.equals(prefix + "td");
	}

	@Override
	public String usage() {
		return "`toggledebug` or `td`";
	}

	@Override
	public String getAlias() {
		return "toggledebug";
	}

	public boolean requiresOwner() {
		return true;
	}

	@Override
	public String example() {
		return "td";
	}

}
