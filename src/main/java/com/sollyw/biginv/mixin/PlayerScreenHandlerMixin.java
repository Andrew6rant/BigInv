package com.sollyw.biginv.mixin;

import com.sollyw.biginv.BigInvModInfo;
import com.sollyw.biginv.ScreenHandlerOverrides;
import com.sollyw.biginv.ScreenHandlerExt;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin implements ScreenHandlerExt {
    @Unique
    private static final Identifier PLAYER = new Identifier("minecraft", "player");

    @ModifyConstant(method = "<init>",
            constant = {
                    @Constant(intValue = 2,
                            ordinal = 0),
                    @Constant(intValue = 2,
                            ordinal = 1),
                    @Constant(intValue = 2,
                            ordinal = 2),
                    @Constant(intValue = 2,
                            ordinal = 3),
                    @Constant(intValue = 2,
                            ordinal = 4),})
    private int craftingInvSize(int value) {
        return 3;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            constant = @Constant(intValue = 98, ordinal = 0))
    private int craftingInvStart(int value) {
        return 71;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
        constant = @Constant(intValue = 154, ordinal = 0))
    private int craftingResultSlotX(int value) {
        return 152;
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V",
            constant = @Constant(intValue = 28, ordinal = 0))
    private int craftingResultSlotY(int value) {
        return 19;
    }


    //public final CraftingInventory craftingInput = new CraftingInventory(this, 2, 2);

    /**
     * @author SollyW
     * @reason Account for larger hotbar
     */
    @Overwrite
    public static boolean isInHotbar(int slot) {
        return slot >= 72 && slot <= 84;
    }

    /**
     * @author Andrew6rant (Andrew Grant)
     * @reason Account for larger crafting screen
     */
    @Overwrite
    public int getCraftingSlotCount() {
        return 10;
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
        return 65;
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
        return 77;
    }

    @ModifyConstant(method = "quickMove",
            constant = {
                    @Constant(intValue = 8,
                            ordinal = 0),
                    @Constant(intValue = 8,
                            ordinal = 1)})
    private int armourLast(int value) {
        return 80;
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
        return 81;
    }

    @ModifyConstant(method = "quickMove",
            constant = @Constant(intValue = 46,
                            ordinal = 0))
    private int offhandEnd(int value) {
        return 82;
    }

    @Override
    public BigInvModInfo biginv$getModInfo() {
        return ScreenHandlerOverrides.SCREEN_HANDLER_OVERRIDES.getOrDefault(PLAYER, BigInvModInfo.PLAYER);
    }
}
