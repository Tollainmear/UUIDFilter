package org.Tollainmear.UUIDFilter.CommandExecutor;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.Tollainmear.UUIDFilter.UUIDFilter;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

public class reloadExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        UUIDFilter plugin = UUIDFilter.getInstance();

        try {
            plugin.reloadList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        src.sendMessage(plugin.getPrifixText().concat(Text.of(TextColors.GREEN,"UUID_Filter's list reloaded!")));
        return CommandResult.success();
    }
}
