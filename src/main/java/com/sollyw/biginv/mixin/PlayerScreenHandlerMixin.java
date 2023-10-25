package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.LockableCraftingSlot;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<RecipeInputInventory> implements ScreenHandlerExt {
    @Shadow @Final private RecipeInputInventory craftingInput;
    @Unique
    private static final Identifier PLAYER = new Identifier("minecraft", "player");

    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }


    @ModifyConstant(method = "<init>",
            constant = {
                    @Constant(intValue = 2,
                            ordinal = 0),
                    @Constant(intValue = 2,
                            ordinal = 1)})
    private int invcrafting$craftingInvSize(int value) {
        return 3;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            constant = @Constant(intValue = 2, ordinal = 2))
    public int invcrafting$makeForLoopNotRun(int constant) {
        // Vanilla Minecraft adds the 2x2 crafting slots inm a loop. Instead of modifying it, I will stop the loop from running
        // and add slots individually (since I need to add slots both before and after the player inventory
        return -1;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
                    value = "INVOKE", ordinal = 0, shift = At.Shift.AFTER))
    private void invcrafting$swapCraftingInput2(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 0, 80, 18));
        this.addSlot(new Slot(this.craftingInput, 1, 98, 18));
        this.addSlot(new Slot(this.craftingInput, 2, 116, 18));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 3, 80, 36));
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void invcrafting$PlayerScreenHandler(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        this.addSlot(new Slot(this.craftingInput, 4, 98, 36));
        this.addSlot(new Slot(this.craftingInput, 5, 116, 36));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 6, 80, 54));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 7, 98, 54));
        this.addSlot(new LockableCraftingSlot(owner, this.craftingInput, 8, 116, 54));
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            constant = @Constant(intValue = 154, ordinal = 0))
    public int tifyi$moveCraftingResultSlotX(int constant) {
        return 152;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            constant = @Constant(intValue = 28, ordinal = 0))
    public int tifyi$moveCraftingResultSlotY(int constant) {
        return 36;
    }

    /**
     * @author SollyW
     * @reason Account for larger hotbar
     */
    @Overwrite
    public static boolean isInHotbar(int slot) {
        return slot >= 53 && slot <= 64;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 9,
                            ordinal = 1),
                    @Constant(intValue = 9,
                            ordinal = 3),
                    @Constant(intValue = 9,
                            ordinal = 4),
                    @Constant(intValue = 9,
                            ordinal = 5),
                    @Constant(intValue = 9,
                            ordinal = 6)})
    private int mainInvStart(int value) {
        return 5;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 36,
                            ordinal = 0),
                    @Constant(intValue = 36,
                            ordinal = 1),
                    @Constant(intValue = 36,
                            ordinal = 2),
                    @Constant(intValue = 36,
                            ordinal = 3)})
    private int mainInvEndHotbarStart(int value) {
        return 52;
    }
// these don't work properly
    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 45,
                            ordinal = 1),
                    @Constant(intValue = 45,
                            ordinal = 2),
                    @Constant(intValue = 5,
                            ordinal = 1),
                    @Constant(intValue = 45,
                            ordinal = 5),
                    @Constant(intValue = 45,
                            ordinal = 6),
                    @Constant(intValue = 45,
                            ordinal = 7)})
    private int hotbarEndArmourStart(int value) {
        return 66;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 8,
                            ordinal = 0),
                    @Constant(intValue = 8,
                            ordinal = 1)})
    private int armourLast(int value) {
        return 69;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 9,
                            ordinal = 2),
                    @Constant(intValue = 45,
                            ordinal = 3),
                    @Constant(intValue = 45,
                            ordinal = 4)})
    private int armourEndOffhandStart(int value) {
        return 70;
    }

    @ModifyConstant(method = "quickMove",
            constant = @Constant(intValue = 46,
                            ordinal = 0))
    private int offhandEnd(int value) {
        return 71;
    }

    /**
     * @author Andrew6rant (Andrew Grant)
     * @reason I made the inventory crafting grid 3x3 (+ output slot)
     * It returns 10 always even if the 2x2 grid is valid because the disabled slots are still there
     */
    @Overwrite
    public int getCraftingSlotCount() {
        return 10;
    }

    @Override
    public BigInvModInfo biginv$getModInfo() {
        return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(PLAYER, BigInvModInfo.PLAYER);
    }
}
