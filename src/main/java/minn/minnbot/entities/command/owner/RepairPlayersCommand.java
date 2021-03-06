package minn.minnbot.entities.command.owner;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;
import minn.minnbot.manager.MinnAudioManager;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.player.MusicPlayer;

import java.util.Map;

public class RepairPlayersCommand extends CommandAdapter {// TODO: Test

    public RepairPlayersCommand(String prefix, Logger logger) {
        init(prefix, logger);
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.isPrivate())
            return;
        super.onMessageReceived(event);
    }

    @Override
    public void onCommand(CommandEvent event) {
        MinnAudioManager.clear();
        Map<Guild, MusicPlayer> players = MinnAudioManager.getPlayers();
        players.forEach((g,p) -> {
            g.getAudioManager().setSendingHandler(p);
            if(!p.isPlaying() && !p.getAudioQueue().isEmpty())
                p.play();
        });
        event.sendMessage("Players have been repaired.");
    }

    @Override
    public boolean isCommand(String message) {
        String[] p = message.split(" ",2);
        return p.length > 0 && p[0].equalsIgnoreCase(prefix + "vrepair");
    }

    @Override
    public String getAlias() {
        return "vRepair";
    }

    @Override
    public boolean requiresOwner() {
        return true;
    }
}
