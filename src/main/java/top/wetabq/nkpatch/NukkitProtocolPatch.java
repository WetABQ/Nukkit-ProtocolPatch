package top.wetabq.nkpatch;

import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import top.wetabq.nkpatch.network.InventoryContentPacket;
import top.wetabq.nkpatch.network.InventoryTransactionPacket;
import top.wetabq.nkpatch.network.LoginPacket;
import top.wetabq.nkpatch.utils.MetricsLite;


public class NukkitProtocolPatch extends PluginBase {

    public void onEnable() {
        new MetricsLite(this);
        this.getLogger().info(TextFormat.colorize("&cNukkit&eProtocol&aPatch &b&lEnabled."));
        this.getServer().getNetwork().registerPacket(ProtocolInfo.LOGIN_PACKET, LoginPacket.class);
        this.getServer().getNetwork().registerPacket(ProtocolInfo.INVENTORY_TRANSACTION_PACKET, InventoryTransactionPacket.class);
        this.getServer().getNetwork().registerPacket(ProtocolInfo.INVENTORY_CONTENT_PACKET, InventoryContentPacket.class);
    }

}