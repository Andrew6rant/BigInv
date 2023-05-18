package com.sollyw.biginv.mixin.compat;

import dev.emi.trinkets.Point;
import dev.emi.trinkets.SurvivalTrinketSlot;
import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.TrinketInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev/emi/trinkets/mixin/PlayerScreenHandlerMixin")
public class TrinketsPlayerScreenHandlerMixinMixin {

    //@SuppressWarnings("UnresolvedMixinReference")
    @ModifyArg(method = "trinkets$updateTrinketSlots",
            at = @At(value = "RETURN",target = "Ldev/emi/trinkets/mixin/PlayerScreenHandlerMixin;trinkets$updateTrinketSlots(Z)V"),
            index = 0)
    public SurvivalTrinketSlot addSlot(TrinketInventory stacks, int i, int x, int y, Point pos, SlotGroup group, int groupOffset, CallbackInfo ci) {
        return new SurvivalTrinketSlot(stacks, i, x + pos.x(), y, group, stacks.getSlotType(), i, groupOffset == 1 && i == 0);
    }
}
