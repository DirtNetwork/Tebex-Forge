package com.github.xniter.tebexio.command;

import com.github.xniter.tebexio.ForgedTebex;
import com.github.xniter.tebexio.util.CmdUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class ForceCheckCmd implements Command<CommandSourceStack> {
    private final ForgedTebex plugin;

    public ForceCheckCmd(final ForgedTebex plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponent(ForgeMessageUtil.format("need_secret_key"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        if (plugin.getDuePlayerFetcher().inProgress()) {
            ForgeMessageUtil.sendMessage(context.getSource(), new TextComponent(ForgeMessageUtil.format("already_checking_for_purchases"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        plugin.getExecutor().submit(() -> plugin.getDuePlayerFetcher().run(false));
        ForgeMessageUtil.sendMessage(context.getSource(), new TextComponent(ForgeMessageUtil.format("forcecheck_queued"))
                .setStyle(CmdUtil.SUCCESS_STYLE));
        return 1;
    }
}