package org.Tollainmear.UUIDFilter;

import org.Tollainmear.UUIDFilter.CommandExecutor.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class UUFCommand {

    UUIDFilter plugin;
    CommandSpec remove;
    CommandSpec add;
    CommandSpec list;
    CommandSpec help;
    CommandSpec reload;

    public UUFCommand(UUIDFilter plugin) {
         this.plugin = plugin;

        remove = CommandSpec.builder()
                .permission(plugin.PLUGINNAME + ".remove")
                .description(Text.of("Remove a player's name from UUID_Filter's list"))
                .arguments(GenericArguments.onlyOne(GenericArguments.user(Text.of("Player"))))
                .executor(new RemoveExecutor())
                .build();

        add = CommandSpec.builder()
                .permission(plugin.PLUGINNAME + ".add")
                .description(Text.of("Add a player's name to UUID_Filter's list"))
                .arguments(GenericArguments.onlyOne(GenericArguments.user(Text.of("Player"))))
                .executor(new addExecutor())
                .build();

        list = CommandSpec.builder()
                .arguments(GenericArguments.none())
                .permission(plugin.PLUGINNAME+".list")
                .description(Text.of("Show you the UUID_Filter's list"))
                .executor(new listExecutor())
                .build();
        help = CommandSpec.builder()
                .description(Text.of("Show some infomations."))
                .permission(plugin.PLUGINNAME+".help")
                .arguments(GenericArguments.none())
                .executor(new helpExecutor())
                .build();
        reload = CommandSpec.builder()
                .permission(plugin.PLUGINNAME+".reload")
                .description(Text.of("Reload UUF's list from config"))
                .arguments(GenericArguments.none())
                .executor(new reloadExecutor())
                .build();
    }
    public void init()
    {
        CommandManager commandManager = Sponge.getCommandManager();
        commandManager.register(this.plugin, this.get(), "UUIDFilter", "uuf", "uu","uf");
    }


    public CommandCallable get()
    {
        return CommandSpec.builder()
                .executor(new MainExecutor())
                .child(this.reload,"reload")
                .child(this.add, "add", "a")
                .child(this.remove, "remove", "r")
                .child(this.list, "list", "l")
                .child(this.help, "help", "h")
                .build();
    }
}
