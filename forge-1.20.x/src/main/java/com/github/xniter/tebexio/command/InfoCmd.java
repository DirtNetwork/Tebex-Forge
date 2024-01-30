package com.github.xniter.tebexio.command;

import com.github.xniter.tebexio.TebexForged;
import com.github.xniter.tebexio.util.CmdUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

import java.util.stream.Stream;

public class InfoCmd implements Command<CommandSourceStack> {
    private final TebexForged plugin;

    public InfoCmd(final TebexForged plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("generic_api_operation_error"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        if (plugin.getServerInformation() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), Component.literal(ForgeMessageUtil.format("information_no_server"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        String webstoreURL = plugin.getServerInformation().getAccount().getDomain();

        Component webstore = Component.literal(webstoreURL)
                .withStyle(style -> {
                    return style.withColor(ChatFormatting.GREEN)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, webstoreURL))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(webstoreURL)));
                });

        Component server = Component.literal(plugin.getServerInformation().getServer().getName()).withStyle(ChatFormatting.GREEN);

        Stream.of(
                Component.literal(ForgeMessageUtil.format("information_title") + " ").withStyle(ChatFormatting.GRAY),
                Component.literal(ForgeMessageUtil.format("information_sponge_server") + " ").withStyle(ChatFormatting.GRAY).append(server),
                Component.literal(ForgeMessageUtil.format("information_currency", plugin.getServerInformation().getAccount().getCurrency().getIso4217()))
                        .withStyle(ChatFormatting.GRAY),
                Component.literal(ForgeMessageUtil.format("information_domain", "")).withStyle(ChatFormatting.GRAY).append(webstore)
        ).forEach(message -> ForgeMessageUtil.sendMessage(context.getSource(), message));

        return 1;
    }
}