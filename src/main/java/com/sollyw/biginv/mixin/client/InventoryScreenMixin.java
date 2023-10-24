package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    @Shadow @Final private RecipeBookWidget recipeBook;

    @Shadow private boolean mouseDown;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE",
           target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", shift = At.Shift.AFTER))
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        int i = this.x;
        int j = this.y;
        if (this.client.player.getInventory().contains(Items.CRAFTING_TABLE.getDefaultStack())) {
            context.drawTexture(new Identifier("biginv:textures/gui/inventory.png"), i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        } else {
            context.drawTexture(new Identifier("biginv:textures/gui/inventory_grid_disabled.png"), i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        }
    }

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

    @ModifyArg(method = "init()V", index = 8, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TexturedButtonWidget;<init>(IIIIIIILnet/minecraft/util/Identifier;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V"))
    private ButtonWidget.PressAction tifyi$modifyRecipeBookXY(ButtonWidget.PressAction action) {
        return (buttonWidget) -> {
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            buttonWidget.setPosition(this.x + 142, this.height / 2 - 24);
            this.mouseDown = true;
        };
    }

    @ModifyConstant(method = "init()V", constant = @Constant(intValue = 104))
    private int tifyi$moveRecipeBookX(int constant) {
        return 142;
    }

    @ModifyConstant(method = "init()V", constant = @Constant(intValue = 22, ordinal = 0))
    private int tifyi$moveRecipeBookY(int constant) {
        return 24;
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
