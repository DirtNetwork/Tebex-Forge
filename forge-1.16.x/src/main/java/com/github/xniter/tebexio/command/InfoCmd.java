package com.github.xniter.tebexio.command;

import com.github.xniter.tebexio.TebexForged;
import com.github.xniter.tebexio.util.CmdUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.stream.Stream;

public class InfoCmd implements Command<CommandSource> {
    private final TebexForged plugin;

    public InfoCmd(final TebexForged plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("generic_api_operation_error"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        if (plugin.getServerInformation() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("information_no_server"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        String webstoreURL = plugin.getServerInformation().getAccount().getDomain();

        ITextComponent webstore = new StringTextComponent(webstoreURL)
                .withStyle(style -> {
                    return style.withColor(TextFormatting.GREEN)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, webstoreURL))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(webstoreURL)));
                });

        ITextComponent server = new StringTextComponent(plugin.getServerInformation().getServer().getName()).withStyle(TextFormatting.GREEN);

        Stream.of(
                new StringTextComponent(ForgeMessageUtil.format("information_title") + " ").withStyle(TextFormatting.GRAY),
                new StringTextComponent(ForgeMessageUtil.format("information_sponge_server") + " ").withStyle(TextFormatting.GRAY).append(server),
                new StringTextComponent(ForgeMessageUtil.format("information_currency", plugin.getServerInformation().getAccount().getCurrency().getIso4217()))
                        .withStyle(TextFormatting.GRAY),
                new StringTextComponent(ForgeMessageUtil.format("information_domain", "")).withStyle(TextFormatting.GRAY).append(webstore)
        ).forEach(message -> ForgeMessageUtil.sendMessage(context.getSource(), message));

        return 1;
    }
}