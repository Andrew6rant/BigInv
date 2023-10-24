package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {
    @Shadow
    private boolean narrow;

    @Inject(method = "init",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;narrow:Z",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER))
    private void init(CallbackInfo ci) {
        //this.narrow = true;
    }

    /*@ModifyConstant(method = "drawBackground(Lnet/minecraft/client/gui/DrawContext;FII)V",
            constant = @Constant(intValue = 30, ordinal = 0))
    public int tifyi$changePlayerRenderSize(int constant) {
        return playerRenderSize;
    }*/

    @ModifyConstant(method = "drawBackground(Lnet/minecraft/client/gui/DrawContext;FII)V",
            constant = {@Constant(intValue = 51, ordinal = 0),@Constant(intValue = 51, ordinal = 1)})
    public int tifyi$movePlayerRenderX(int constant) {
        return -2;
    }

    @ModifyConstant(method = "drawBackground(Lnet/minecraft/client/gui/DrawContext;FII)V",
            constant = {@Constant(intValue = 75, ordinal = 0),@Constant(intValue = 75, ordinal = 1)})
    public int tifyi$movePlayerRenderY(int constant) {
        return 72;
    }

    /*
    @Redirect(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V"),
            allow = 1)
    private void drawBackground(InventoryScreen instance, MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // nom
    }*/

    /*@Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIFFLnet/minecraft/entity/LivingEntity;)V"))
    private void drawEntity(DrawContext context, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        // nom
    }*/
}
