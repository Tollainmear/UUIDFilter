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
import org.spongepowered.api.text.format.TextStyles;

import javax.print.attribute.TextSyntax;
import java.util.ArrayList;
import java.util.List;

public class listExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        UUIDFilter plugin = UUIDFilter.getInstance();
        String Title = "UUIF_Filter's List";
        List<Text> contents = new ArrayList<>();
        for (String playerName : plugin.getPlayerList()) {
            contents.add(Text.of(playerName));
        }
        PaginationList.Builder builder = PaginationList.builder();
        builder.title(Text.of(TextColors.GOLD, TextStyles.BOLD, Title))
                .contents(contents)
                .padding(Text.of(TextColors.YELLOW, "-"))
                .sendTo(src);
        return CommandResult.success();
    }
}
