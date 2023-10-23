package com.sollyw.biginv;

import com.mojang.datafixers.util.Pair;
import com.sollyw.biginv.mixin.SlotAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class BigInvScreenHandlerHelper {
    public static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET};

    public static Slot[] createSlots(ScreenHandler handler, PlayerInventory playerInventory) {
        ScreenHandlerExt handlerX = (ScreenHandlerExt) handler;
        Slot[] slots = new Slot[65];

        handlerX.biginv$setModStage(BigInvModStage.MODDING);

        for(int i = 0; i < 48; ++i) {
            int index = i + 12;
            slots[index] = handlerX.biginv$addSlot(new Slot(
                    playerInventory,
                    index,
                    -9999,
                    -9999));
        }

        for(int i = 0; i < 12; ++i) {
            slots[i] = handlerX.biginv$addSlot(new Slot(
                    playerInventory,
                    i,
                    -9999,
                    -9999));
        }


        slots[64] = handlerX.biginv$addSlot(new Slot(playerInventory, 64, -9999, -9999) {
            public void setStack(ItemStack stack) {
                PlayerScreenHandler.onEquipStack(playerInventory.player, EquipmentSlot.OFFHAND, stack, this.getStack());
                super.setStack(stack);
            }
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        });

        for(int i = 0; i < 4; ++i) {
            int index = 63 - i;
            final EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[i];
            slots[index] = handlerX.biginv$addSlot(new Slot(playerInventory, index, -9999, -9999) {
                public void setStack(ItemStack stack) {
                    PlayerScreenHandler.onEquipStack(playerInventory.player, equipmentSlot, stack, this.getStack());
                    super.setStack(stack);
                }

                public int getMaxItemCount() {
                    return 1;
                }

                public boolean canInsert(ItemStack stack) {
                    return equipmentSlot == MobEntity.getPreferredEquipmentSlot(stack);
                }

                public boolean canTakeItems(PlayerEntity playerEntity) {
                    ItemStack itemStack = this.getStack();
                    return (itemStack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.canTakeItems(playerEntity);
                }

                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_ARMOR_SLOT_TEXTURES[equipmentSlot.getEntitySlotId()]);
                }
            });
        }

        handlerX.biginv$setModStage(BigInvModStage.FINISHED);
        return slots;
    }

    public static void positionSlots(Slot[] slots, int offsetX, int offsetY, int armourOffsetX, int armourOffsetY, PlayerEntity player) {

        for(int row = 0; row < 4; ++row) {
            for(int col = 0; col < 12; ++col) {
                move(slots,
                        col + row * 12 + 12,
                        col * 18 - 46 + offsetX,
                        84 + row * 18 + offsetY);
            }
        }

        for(int col = 0; col < 12; ++col) {
            move(slots,
                    col,
                    col * 18 - 46 + offsetX,
                    offsetY + 160);
        }

        // offhand
        if (player.currentScreenHandler instanceof PlayerScreenHandler) {
            move(slots,
                    64,
                    armourOffsetX - 36,
                    armourOffsetY + 62);
        } else {
            move(slots,
                    64,
                    armourOffsetX - 90,
                    armourOffsetY + 62);
        }



        // armor
        for(int row = 0; row < 4; ++row) {
            move(slots,
                    63 - row,
                    armourOffsetX - 108,
                    armourOffsetY + 8 + row * 18);
        }

    }

    private static void move(Slot[] slots, int index, int x, int y) {
        SlotAccessor slotX = (SlotAccessor) slots[index];
        slotX.setX(x);
        slotX.setY(y);
    }
}
