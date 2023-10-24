package com.sollyw.biginv.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sollyw.biginv.BigInv.id;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    @Shadow private static ItemGroup selectedTab;

    @ModifyConstant(method = "drawForeground(Lnet/minecraft/client/gui/DrawContext;II)V", constant = @Constant(intValue = 8))
    private int littlebiginv$modifyCreativeInventoryTextX(int constant) {
        return -19;
    }

    @ModifyConstant(method = "drawForeground(Lnet/minecraft/client/gui/DrawContext;II)V", constant = @Constant(intValue = 6))
    private int littlebiginv$modifyCreativeInventoryTextY(int constant) {
        return -34;
    }

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @ModifyConstant(method = {"setSelectedTab(Lnet/minecraft/item/ItemGroup;)V", "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V"},
            constant = @Constant(intValue = 9))
    private int littlebiginv$modifySelectedTabIndex(int constant) {
        return 14;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 45))
    private static int littlebiginv$modifyCreativeInventorySize(int constant) {
        return 84;
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1, shift = At.Shift.AFTER))
    public void littlebiginv$renderCreativeBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        context.drawTexture(new Identifier("biginv:textures/gui/littlebiginv_creative.png"), this.x-30, this.y-40, 0, 0, 512, 512, 512, 512);
    }

    /*@ModifyConstant(method = "renderTabIcon(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/item/ItemGroup;)V", constant = @Constant(floatValue = 0.0f, ordinal = 0))
    private static float littlebiginv$modifyTabXMatrices(float constant) {
        return ;
    }*/

    @Overwrite
    public void renderTabIcon(DrawContext context, ItemGroup group) {
        //System.out.println(this.backgroundWidth);
        boolean bl = group == selectedTab;
        boolean bl2 = group.getRow() == ItemGroup.Row.TOP;
        int i = group.getColumn();
        int j = i * 26;
        int k = 0;
        int l = this.x + this.getTabX(group);
        int m = this.y + 50; // + 20
        if (bl) {
            k += 32;
        }
        if (bl2) {
            m -= 118; // change this
        } else {
            k += 64;
            m += this.backgroundHeight;
        }
        context.drawTexture(new Identifier("minecraft:textures/gui/container/creative_inventory/tabs.png"), l, m, j, k, 26, 32);
        context.getMatrices().push();
        context.getMatrices().translate(0.0f, 0.0f, 100.0f);
        int n2 = bl2 ? 1 : -1;
        ItemStack itemStack = group.getIcon();
        context.drawItem(itemStack, l += 5, m += 8 + n2);
        context.drawItemInSlot(this.textRenderer, itemStack, l, m);
        context.getMatrices().pop();
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/resource/featuretoggle/FeatureSet;Z)V", at = @At("TAIL"))
    private void littlebiginv$injectProperCreativeBackgroundSize(PlayerEntity player, FeatureSet enabledFeatures, boolean operatorTabEnabled, CallbackInfo ci) {
        this.backgroundHeight = 136;
        this.backgroundWidth = 195;
    }

    @Overwrite
    private int getTabX(ItemGroup group) {
        int i = group.getColumn();
        int k = 27 * i;
        if (group.isSpecial()) {
            k = this.backgroundWidth - 27 * (7 - i) + 1;
        }
        return k;
    }

    @Overwrite
    private int getTabY(ItemGroup group) {
        int i = 0;
        i = group.getRow() == ItemGroup.Row.TOP ? i - 68 : i + this.backgroundHeight + 48;
        return i;
    }
}
