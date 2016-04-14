package minn.minnbot.entities.command.owner;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class FlushCommand extends CommandAdapter {

	private User owner;
	
	public FlushCommand(String prefix, Logger logger, User owner) {
		this.prefix = prefix;
		this.logger = logger;
		this.owner = owner;
	}

	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.isPrivate())
			return;
		if (event.getAuthor() == owner && isCommand(event.getMessage().getContent())) {
			logger.logCommandUse(event.getMessage());
			onCommand(new CommandEvent(event));
		}
	}

	@Override
	public void onCommand(CommandEvent event) {
		try {
			User u = event.event.getJDA().getSelfInfo();
			Thread t = new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					java.util.List<Message> hist = new net.dv8tion.jda.MessageHistory(event.event.getJDA(),
							event.event.getChannel()).retrieve(100);
					for (Message m : hist) {
						if (m.getAuthor() == u) {
							Thread t = new Thread() {
								public void run() {
									m.deleteMessage();
									this.stop();
								}
							};
							t.start();
						}
					}
					this.stop();
				}
			};
			t.start();
		} catch (Exception e) {
			logger.logError(e);
		}
	}

	@Override
	public boolean isCommand(String message) {
		String[] parts = message.split(" ", 2);
		if (parts.length < 1)
			return false;
		return parts[0].equalsIgnoreCase(prefix + "flush");
	}

	@Override
	public String getAlias() {
		return "flush";
	}

	@Override
	public boolean requiresOwner() {
		return true;
	}
}