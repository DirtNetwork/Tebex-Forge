package com.github.xniter.tebexio.util;


import com.github.xniter.tebexio.TebexForged;
import net.buycraft.plugin.data.responses.Version;

import java.io.IOException;

public class VersionCheck {
    private final TebexForged plugin;
    private final String pluginVersion;
    private final String secret;
    private Version lastKnownVersion;
    private boolean upToDate = true;

    public VersionCheck(final TebexForged plugin, final String pluginVersion, final String secret) {
        this.plugin = plugin;
        this.pluginVersion = pluginVersion;
        this.secret = secret;
    }

    public void verify() throws IOException {
        // skip
    }

    // we don't care
    // @SubscribeEvent
    // public void onPostLogin(final PlayerEvent.PlayerLoggedInEvent event) {
    //     if (event.getPlayer().hasPermissions(2) && !upToDate) {
    //         plugin.getPlatform().executeAsyncLater(() ->
    //                         event.getPlayer().sendMessage(new TextComponent(ForgeMessageUtil
    //                        .format("update_available", lastKnownVersion.getVersion()))
    //                                 .setStyle(CmdUtil.INFO_STYLE), Util.NIL_UUID),
    //                 3, TimeUnit.SECONDS);
    //     }
    // }

    public Version getLastKnownVersion() {
        return this.lastKnownVersion;
    }

    public boolean isUpToDate() {
        return this.upToDate;
    }
}