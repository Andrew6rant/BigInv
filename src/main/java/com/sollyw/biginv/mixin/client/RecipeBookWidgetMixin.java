package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(RecipeBookWidget.class)
public class RecipeBookWidgetMixin {
    @ModifyConstant(method = {"reset()V", "refreshTabButtons()V", "render(Lnet/minecraft/client/gui/DrawContext;IIF)V", "mouseClicked(DDI)Z", "isClickOutsideBounds(DDIIIII)Z"},
            constant = @Constant(intValue = 147, ordinal = 0))
    private int littlebiginv$recipeBookWidgetOffset(int value) {
        return 254;
    }

    /*@ModifyConstant(method = "render(Lnet/minecraft/client/gui/DrawContext;IIF)V",
            constant = @Constant(floatValue = 0.0F, ordinal = 0))
    private float recipeBookWidgetOffsetXMatrices(float constant) {
        return -54;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/gui/DrawContext;IIF)V",
            at = @At(value = "HEAD"), index = 2, argsOnly = true)
    private int recipeBookWidgetOffsetXArg(int x) {
        return x + 54;
    }*/
}
