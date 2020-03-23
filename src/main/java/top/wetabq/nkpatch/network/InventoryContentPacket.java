package top.wetabq.nkpatch.network;

import cn.nukkit.Server;
import cn.nukkit.item.Item;

public class InventoryContentPacket extends cn.nukkit.network.protocol.InventoryContentPacket {

    public void decode() {
        this.inventoryId = (int)this.getUnsignedVarInt();
        int count = (int)this.getUnsignedVarInt();
        if (count > 2000000) {
            Server.getInstance().getLogger().warning("[NukkitProtoclPatch] 存在潜在的崩溃包攻击. length="+ count +"- InventoryContentPacket");
            return;
            //throw new ArrayIndexOutOfBoundsException("Too long size data");
        }
        this.slots = new Item[count];

        for(int s = 0; s < count && !this.feof(); ++s) {
            this.slots[s] = this.getSlot();
        }

    }

}

