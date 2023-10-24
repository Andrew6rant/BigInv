package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.INVENTORY;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public abstract class CreativeInventoryScreenCreativeScreenHandlerMixin extends ScreenHandler  {

    @Shadow protected abstract int getRow(float scroll);

    @Shadow @Final public DefaultedList<ItemStack> itemList;

    protected CreativeInventoryScreenCreativeScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }



    /**
     * @author
     * @reason
     *
    @Overwrite
    public void scrollItems(float position) {
        int i = this.getRow(position);
        for (int j = 0; j < 5; ++j) {
            for (int k = 0; k < 9; ++k) {
                int l = k + (j + i) * 9;
                if (l >= 0 && l < this.itemList.size()) {
                    INVENTORY.setStack(k + j * 9, this.itemList.get(l));
                    continue;
                }
                INVENTORY.setStack(k + j * 9, ItemStack.EMPTY);
            }
        }
    }*/

    @ModifyConstant(method = {"getOverflowRows()I", "quickMove(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;", "scrollItems(F)V"}, //, "scrollItems(F)V"
            constant = @Constant(intValue = 9))
    private int littlebiginv$changeCreativeScreenHotbar(int constant) {
        return 12;
    }

    @ModifyConstant(method = "<init>",
            constant = @Constant(intValue = 9))
    private int littlebiginv$removeInitialCreativeSlots(int constant) {
        return 0; //12
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void littlebiginv$addCreativeSlots(PlayerEntity player, CallbackInfo ci) {
        PlayerInventory playerInventory = player.getInventory();
        int i;
        for(i = 0; i < 5; ++i) {
            for(int j = 0; j < 12; ++j) {
                this.addSlot(new CreativeInventoryScreen.LockableSlot(INVENTORY, i * 12 + j, -9 + j * 18, -36 + i * 18));
            }
        }

        // I just need to add a single slot, my addSlot mixin will catch this and actually place the proper slots
        this.addSlot(new Slot(playerInventory, 0, 12, 150));
    }


        @ModifyConstant(method = "shouldShowScrollbar()Z", constant = @Constant(intValue = 45))
    private int littlebiginv$changeShowScrollbar(int constant) {
        return 60;
    }
}
