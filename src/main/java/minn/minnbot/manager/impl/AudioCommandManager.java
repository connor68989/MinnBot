package minn.minnbot.manager.impl;

import minn.minnbot.entities.Command;
import minn.minnbot.entities.Logger;
import minn.minnbot.entities.command.audio.*;
import minn.minnbot.entities.command.custom.HelpSplitter;
import minn.minnbot.manager.CmdManager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AudioCommandManager extends CmdManager {

    public AudioCommandManager(String prefix, Logger logger) {
        this.logger = logger;
        List<String> errors = new LinkedList<>();
        HelpSplitter splitter = new HelpSplitter("Voice commands", "voice", prefix, false);
        AtomicReference<String> err = new AtomicReference<>(registerCommand(splitter));
        if (!err.get().isEmpty())
            errors.add(err.get());

        Command com = new LeaveVoiceCommand(prefix, logger);
        err.set(registerCommand(com));
        if (!err.get().isEmpty())
            errors.add(err.get());
        else
            splitter.add(com);

        PlayCommand pCom = new PlayCommand(prefix, logger);
        err.set(registerCommand(pCom));
        if (!err.get().isEmpty())
            errors.add(err.get());
        else {
            splitter.add(pCom);
        }
        com = new JoinCommand(prefix, logger);
        err.set(registerCommand(com));
        if (!err.get().isEmpty())
            errors.add(err.get());
        else
            splitter.add(com);

        com = new CurrentCommand(prefix, logger);
        err.set(registerCommand(com));
        if (!err.get().isEmpty())
            errors.add(err.get());
        else
            splitter.add(com);

        com = new SkipCommand(prefix, logger);
        err.set(registerCommand(com));
        if (!err.get().isEmpty())
            errors.add(err.get());
        else
            splitter.add(com);

        com = new VolumeCommand(prefix,logger);
        err.set(registerCommand(com));
        if (!err.get().isEmpty())
            errors.add(err.get());
        else
            splitter.add(com);

        this.errors = errors;
    }
}
