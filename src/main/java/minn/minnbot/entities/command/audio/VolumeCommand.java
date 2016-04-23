package minn.minnbot.entities.command.audio;

import minn.minnbot.entities.Logger;
import minn.minnbot.entities.audio.MinnAudioManager;
import minn.minnbot.entities.command.listener.CommandAdapter;
import minn.minnbot.events.CommandEvent;
import net.dv8tion.jda.player.MusicPlayer;

public class VolumeCommand extends CommandAdapter {

    public VolumeCommand(String prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
    }

    @Override
    public void onCommand(CommandEvent event) {
        MusicPlayer player = MinnAudioManager.getPlayer(event.event.getGuild());
        if (event.allArguments.isEmpty()) {
            event.sendMessage("**__Volume:__** " + player.getVolume());
        } else {
            try {
                float vol = Float.parseFloat(event.allArguments);
                if(vol > 1) {
                    vol = 1;
                } else if(vol < 0) {
                    vol = 0;
                }
                player.setVolume(vol);
                event.sendMessage("**__Volume:__** " + vol);
            } catch (NumberFormatException ignored) {
                event.sendMessage("Volume must be a number between 1 and 0!");
            }

        }
    }

    @Override
    public boolean isCommand(String message) {
        String[] p = message.split(" ", 2);
        return p.length > 0 && p[0].equalsIgnoreCase(prefix + "volume");
    }

    @Override
    public String getAlias() {
        return "volume";
    }

    @Override
    public String example() {
        return "volume .5";
    }
}
