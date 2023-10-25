package com.sollyw.biginv.mixin;

import net.minecraft.screen.ForgingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ForgingScreenHandler.class)
public abstract class ForgingScreenHandlerMixin {

    @ModifyConstant(method = "getPlayerHotbarEndIndex()I",
            constant = @Constant(intValue = 9))
    private int littlebiginv$changePlayerHotbarEndIndex(int constant) {
        return 12;
    }

    @ModifyConstant(method = "getPlayerInventoryEndIndex()I",
            constant = @Constant(intValue = 27))
    private int littlebiginv$changePlayerInventoryEndIndex(int constant) {
        return 36;
    }
}
