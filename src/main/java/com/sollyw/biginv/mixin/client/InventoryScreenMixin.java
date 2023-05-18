package com.sollyw.biginv.mixin.client;

import dev.emi.trinkets.Point;
import dev.emi.trinkets.TrinketPlayerScreenHandler;
import dev.emi.trinkets.TrinketScreen;
import dev.emi.trinkets.api.SlotGroup;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InventoryScreen.class, priority = 1500)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements TrinketScreen {
    @Shadow
    private boolean narrow;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;narrow:Z",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER))
    private void init(CallbackInfo ci) {
        this.narrow = true;
    }

    @Redirect(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V"),
            allow = 1)
    private void drawBackground(InventoryScreen instance, MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // nom
    }

    @Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/util/math/MatrixStack;IIIFFLnet/minecraft/entity/LivingEntity;)V"))
    private void drawEntity(MatrixStack matrices, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        // nom
    }

    @Override
    public TrinketPlayerScreenHandler trinkets$getHandler() {
        return (TrinketPlayerScreenHandler) this.handler;
    }

    @Override
    public Rect2i trinkets$getGroupRect(SlotGroup group) {
        Point pos = ((TrinketPlayerScreenHandler) handler).trinkets$getGroupPos(group);
        if (pos != null) {
            if (group.getName().equals("hand") || group.getName().equals("cart")) {
                return new Rect2i(pos.x() - 52, pos.y() - 1, 17, 17);
            }
            return new Rect2i(pos.x() - 1, pos.y() - 1, 17, 17);
        }
        return new Rect2i(0, 0, 0, 0);
    }

}
