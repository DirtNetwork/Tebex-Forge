package com.github.xniter.tebexio.command;

import com.github.xniter.tebexio.TebexForged;
import com.github.xniter.tebexio.util.CmdUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class BuyCommand implements Command<CommandSource> {

    private final TebexForged plugin;

    public BuyCommand(TebexForged plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        if(plugin.getServerInformation() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("information_no_server"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent("                                            ").withStyle(TextFormatting.STRIKETHROUGH));
        ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("To view the webstore, click this link: ")).withStyle(style -> {
            return style.withColor(TextFormatting.GREEN)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getServerInformation().getAccount().getDomain()));
        }));
        ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(plugin.getServerInformation().getAccount().getDomain()).withStyle().withStyle(style -> {
            return style.withColor(TextFormatting.BLUE).withUnderlined(true)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getServerInformation().getAccount().getDomain()));
        }));
        ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent("                                            ").withStyle(TextFormatting.STRIKETHROUGH));
        return 1;
    }
}