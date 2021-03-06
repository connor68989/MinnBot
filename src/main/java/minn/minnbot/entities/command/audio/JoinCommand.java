package minn.minnbot.entities.command.audio;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;
import minn.minnbot.manager.MinnAudioManager;
import minn.minnbot.util.EmoteUtil;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.entities.VoiceStatus;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.MusicPlayer;

public class JoinCommand extends CommandAdapter {


    public JoinCommand(String prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isPrivate())
            return;
        super.onMessageReceived(event);
    }

    @Override
    public void onCommand(CommandEvent event) {

        User user = event.event.getAuthor();
        Guild guild = event.event.getGuild();
        VoiceStatus status = guild.getVoiceStatusOfUser(user);
        if (status == null) {
            event.sendMessage("You must be in a voice channel to use this command.");
            return;
        }
        if (status.getChannel() == null) {
            event.sendMessage("You must be in a voice channel to use this command.");
            return;
        }
        VoiceChannel channel = status.getChannel();
        if (!channel.checkPermission(event.jda.getSelfInfo(), Permission.VOICE_CONNECT)) {
            event.sendMessage("I am not allowed to join you :(");
            return;
        }
        if (!channel.checkPermission(event.jda.getSelfInfo(), Permission.VOICE_SPEAK)) {
            event.sendMessage("I wouldn't be able to speak there anyway :(");
            return;
        }
        VoiceStatus myStatus = guild.getVoiceStatusOfUser(event.event.getJDA().getSelfInfo());
        if (myStatus == null || myStatus.getChannel() == null) {
            guild.getAudioManager().openAudioConnection(channel);
        } else {
            guild.getAudioManager().moveAudioConnection(channel);
        }
        MinnAudioManager.registerPlayer(new MusicPlayer(), event.event.getGuild());
        event.sendMessage("Joined `" + channel.getName() + "`! " + EmoteUtil.getRngOkHand());
    }

    @Override
    public boolean isCommand(String message) {
        try {
            String cmd = message.split(" ", 2)[0];
            return cmd.equalsIgnoreCase(prefix + "joinme");
        } catch (IndexOutOfBoundsException ignore) {
        }
        return false;
    }

    @Override
    public String getAlias() {
        return "joinme";
    }

    @Override
    public String example() {
        return "joinme";
    }

}
