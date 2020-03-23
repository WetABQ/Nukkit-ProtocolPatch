package top.wetabq.nkpatch.network;

import cn.nukkit.Server;
import cn.nukkit.inventory.transaction.data.ReleaseItemData;
import cn.nukkit.inventory.transaction.data.UseItemData;
import cn.nukkit.inventory.transaction.data.UseItemOnEntityData;
import cn.nukkit.network.protocol.types.NetworkInventoryAction;

public class InventoryTransactionPacket extends cn.nukkit.network.protocol.InventoryTransactionPacket {

    public void decode() {

        this.transactionType = (int)this.getUnsignedVarInt();
        int size = (int)this.getUnsignedVarInt();
        if (size > 2000000) {
            Server.getInstance().getLogger().warning("[NukkitProtoclPatch] 存在潜在的崩溃包攻击. length="+ size +" - InventoryTransactionPacket");
            return;
            //throw new ArrayIndexOutOfBoundsException("Too long transaction data.");
        }
        this.actions = new NetworkInventoryAction[size];

        for(int i = 0; i < this.actions.length; ++i) {
            this.actions[i] = (new NetworkInventoryAction()).read(this);
        }

        switch(this.transactionType) {
            case 0:
            case 1:
                break;
            case 2:
                UseItemData itemData = new UseItemData();
                itemData.actionType = (int)this.getUnsignedVarInt();
                itemData.blockPos = this.getBlockVector3();
                itemData.face = this.getBlockFace();
                itemData.hotbarSlot = this.getVarInt();
                itemData.itemInHand = this.getSlot();
                itemData.playerPos = this.getVector3f().asVector3();
                itemData.clickPos = this.getVector3f();
                itemData.blockRuntimeId = (int)this.getUnsignedVarInt();
                this.transactionData = itemData;
                break;
            case 3:
                UseItemOnEntityData useItemOnEntityData = new UseItemOnEntityData();
                useItemOnEntityData.entityRuntimeId = this.getEntityRuntimeId();
                useItemOnEntityData.actionType = (int)this.getUnsignedVarInt();
                useItemOnEntityData.hotbarSlot = this.getVarInt();
                useItemOnEntityData.itemInHand = this.getSlot();
                useItemOnEntityData.playerPos = this.getVector3f().asVector3();
                useItemOnEntityData.clickPos = this.getVector3f().asVector3();
                this.transactionData = useItemOnEntityData;
                break;
            case 4:
                ReleaseItemData releaseItemData = new ReleaseItemData();
                releaseItemData.actionType = (int)this.getUnsignedVarInt();
                releaseItemData.hotbarSlot = this.getVarInt();
                releaseItemData.itemInHand = this.getSlot();
                releaseItemData.headRot = this.getVector3f().asVector3();
                this.transactionData = releaseItemData;
                break;
            default:
                throw new RuntimeException("Unknown transaction type " + this.transactionType);
        }

    }

}

