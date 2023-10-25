package com.sollyw.biginv.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sollyw.biginv.BigInvScreenHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract void renderHotbarItem(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed);

    @Shadow
    private int scaledWidth;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    @Final
    private static Identifier WIDGETS_TEXTURE;

    @Redirect(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
                    ordinal = 0))
    private void littlebiginv$renderHotbarItem(InGameHud instance, DrawContext context, int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
        //int n2 = (x + 40 - this.scaledWidth / 2) / 20;
        //System.out.println("this.scaledWidth: " + this.scaledWidth);
        this.renderHotbarItem(context,
                x - 30,
                y,
                tickDelta,
                player,
                this.getCameraPlayer().getInventory().main.get(seed-1),
                seed);
        if (seed > 6) {
            this.renderHotbarItem(context,
                    x + 30,
                    y,
                    tickDelta,
                    player,
                    this.getCameraPlayer().getInventory().main.get(seed+2),
                    seed+3);
        }
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
                    ordinal = 1),
            index = 1)
    private int littlebiginv$moveOffhandLeft(int x) {
        return x - 30;
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
                    ordinal = 2),
            index = 1)
    private int littlebiginv$moveOffhandRight(int x) {
        return x + 30;
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
                    ordinal = 2),
            index = 1)
    private int littlebiginv$moveOffhandItemLeft(int x) {
        return x - 30;
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
                    ordinal = 3),
            index = 1)
    private int littlebiginv$moveOffhandItemRight(int x) {
        return x + 30;
    }

    @Redirect(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
                    ordinal = 0))
    private void littlebiginv$drawHotbar(DrawContext context, Identifier texture, int x, int y, int u, int v, int width, int height) {
        //RenderSystem.setShaderTexture(0, BigInvScreenHelper.BIG_HOTBAR);
        context.drawTexture(BigInvScreenHelper.BIG_HOTBAR, x - 30, y, 0, 0, 242, height, 256, 32);
        //RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
    }

    @ModifyArg(method = "renderHotbar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
                    ordinal = 1), index = 1)
    private int littlebiginv$moveSelectionOutline(int x) {
        return x - 30;
    }
}
