package com.github.xniter.tebexio.command;

import com.github.xniter.tebexio.TebexForged;
import com.github.xniter.tebexio.util.CmdUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class ForceCheckCmd implements Command<CommandSource> {
    private final TebexForged plugin;

    public ForceCheckCmd(final TebexForged plugin) {
        this.plugin = plugin;
    }

    @Override
    public int run(CommandContext<CommandSource> context) {
        if (plugin.getApiClient() == null) {
            ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("need_secret_key"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        if (plugin.getDuePlayerFetcher().inProgress()) {
            ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("already_checking_for_purchases"))
                    .setStyle(CmdUtil.ERROR_STYLE));
            return 1;
        }

        plugin.getExecutor().submit(() -> plugin.getDuePlayerFetcher().run(false));
        ForgeMessageUtil.sendMessage(context.getSource(), new StringTextComponent(ForgeMessageUtil.format("forcecheck_queued"))
                .setStyle(CmdUtil.SUCCESS_STYLE));
        return 1;
    }
}