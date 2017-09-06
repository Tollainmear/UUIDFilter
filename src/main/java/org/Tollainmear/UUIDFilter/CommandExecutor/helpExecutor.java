package org.Tollainmear.UUIDFilter.CommandExecutor;

import org.Tollainmear.UUIDFilter.UUIDFilter;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;

public class helpExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        PaginationList.Builder builder = PaginationList.builder();
        List<Text> helpText = new ArrayList<>();
        helpText.add(Text.of(TextColors.GOLD, "/uuf ", TextColors.GRAY, " - ", TextColors.YELLOW, "The main command of UUF."));
        helpText.add(Text.of(TextColors.GOLD, "/uuf add [player] ", TextColors.GRAY, " - ", TextColors.YELLOW, "Add a player's name to UUID_Filter's list."));
        helpText.add(Text.of(TextColors.GOLD, "/uuf remove [player]", TextColors.GRAY, " - ", TextColors.YELLOW, "Remove a player's name from UUID_Filter's list."));
        helpText.add(Text.of(TextColors.GOLD, "/uuf list", TextColors.GRAY, " - ", TextColors.YELLOW, "Show you the UUID_Filter's entire list."));
        helpText.add(Text.of(TextColors.GOLD, "/uuf reload", TextColors.GRAY, " - ", TextColors.YELLOW,"Reload UUID_Filter's list from config"));
        builder.title(UUIDFilter.getInstance().getPrifixText())
                .padding(Text.of(TextColors.GREEN,"-"))
                .contents(helpText)
                .sendTo(src);
        return CommandResult.success();
    }
}
