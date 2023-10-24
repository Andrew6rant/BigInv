package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin {
    @ModifyConstant(method = {"setSelectedTab(Lnet/minecraft/item/ItemGroup;)V", "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V"},
            constant = @Constant(intValue = 9))
    private int littlebiginv$modifySelectedTabIndex(int constant) {
        return 12;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 45))
    private static int littlebiginv$modifyCreativeInventorySize(int constant) {
        return 60;
    }
}
