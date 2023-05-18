package com.sollyw.biginv.mixin.compat;

import com.sollyw.biginv.mixin.SlotAccessor;
import dev.emi.trinkets.SurvivalTrinketSlot;
import dev.emi.trinkets.api.SlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public class TrinketsScreenHandlerMixin {

    @Inject(method = "addSlot", at = @At("HEAD"))
    private void getSlot(Slot slot, CallbackInfoReturnable<Slot> cir) {
        if (slot instanceof SurvivalTrinketSlot) {
            SlotType type = ((SurvivalTrinketSlot)slot).getType();
            //System.out.println(type.getGroup());
            if(type.getGroup().equals("hand") || type.getGroup().equals("cart")) {
                SlotAccessor slotX = (SlotAccessor) slot;
                slotX.setX(slot.x - 51);
            }
        }
    }
}
