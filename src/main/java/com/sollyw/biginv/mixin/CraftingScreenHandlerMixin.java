package com.sollyw.biginv.mixin;

import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin {
    @ModifyConstant(method = "quickMove",
            constant = @Constant(intValue = 46))
    private int littlebiginv$modifyInvEnd(int constant) {
        return 70;
    }

    @ModifyConstant(method = "quickMove",
            constant = @Constant(intValue = 37))
    private int littlebiginv$modifyHotbarStart(int constant) {
        return 58;
    }
}
