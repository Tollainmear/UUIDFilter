package org.Tollainmear.UUIDFilter.CommandExecutor;

import org.Tollainmear.UUIDFilter.UUIDFilter;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RemoveExecutor implements CommandExecutor {
    UUIDFilter plugin = UUIDFilter.getInstance();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String name;

        Optional<CommandArgs> playerName = args.getOne(Text.of("Player"));
        if (playerName.isPresent()) {
            name = args.<User>getOne(Text.of("Player")).get().getName();
            List<String> list;
            if ((list = plugin.getPlayerList()).remove(name.toString())) {
                try {
                    plugin.saveList(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                src.sendMessage(plugin.getPrifixText().concat(Text.of(TextColors.GOLD, "The player(" + name + ") has been removed from UUID_Filter's list!")));
                return CommandResult.success();
            } else {
                src.sendMessage(plugin.getPrifixText().concat(Text.of(TextColors.RED, "The player(" + name + ") may not exist!")));
                return CommandResult.empty();
            }
        }
        src.sendMessage(plugin.getPrifixText().concat(Text.of(TextColors.RED, "Did you just typed the wrong command?")));
        return CommandResult.empty();
    }
}
