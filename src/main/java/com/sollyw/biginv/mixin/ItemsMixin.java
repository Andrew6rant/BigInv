package com.sollyw.biginv.mixin;

import com.sollyw.biginv.ChestBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Items.class)
public class ItemsMixin {
    //@Mutable @Final @Shadow public static Item CHEST = Items.register(Blocks.CHEST);

    /*@Redirect(method = "<init>", at = @At(target = "Lnet/minecraft/item/Items;CHEST:Lnet/minecraft/item/Item;", value = "INVOKE"))
    public void test$initchest(CallbackInfo ci) {
        CHEST = Items.register(new ChestBlockItem(Blocks.CHEST, new Item.Settings()));
    }*/
    /*@ModifyArg(slice = @Slice(from = @At(value = "CONSTANT", args = "net/minecraft/item/Items.CHEST")), method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Items;register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;", ordinal = 0))
    private static Block modifyChest(Block block) {
        return new ChestBlockItem()
    }*/



    @Redirect(slice = @Slice(
            from = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Blocks;CHEST:Lnet/minecraft/block/Block;",
                    opcode = Opcodes.GETSTATIC,
                    ordinal = 0
            )
    ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/Items;register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;",
                    ordinal = 0
            ),
            method = "<clinit>")
    private static Item littlebiginv$redirectChestItem(Block block) {
        return Items.register(new ChestBlockItem(Blocks.CHEST, new Item.Settings()));
    }

    /*@Redirect(slice = @Slice(
            from = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/block/Blocks;SHULKER_BOX:Lnet/minecraft/block/Block;",
                    opcode = Opcodes.GETSTATIC,
                    ordinal = 0
            )
    ),
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/item/Items.register (Lnet/minecraft/item/BlockItem;)Lnet/minecraft/item/Item;",
                    ordinal = 0
            ),
            method = "<clinit>")
    private static Item littlebiginv$redirectShulkerBoxItem(BlockItem item) {
        return Items.register(new BlockItem(Blocks.SHULKER_BOX, new Item.Settings().maxCount(1)));
    }*/

    /*@ModifyArg(slice = @Slice(
            from = @At(value = "FIELD",
                target = "Lnet/minecraft/block/Blocks;SHULKER_BOX:Lnet/minecraft/block/Block;",
                opcode = Opcodes.GETSTATIC,
                ordinal = 0
        )
    ), method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;<init>(Lnet/minecraft/item/Item$Settings;)V", ordinal = 0))
    private static Item.Settings littlebiginv$redirectChestItem(Item.Settings settings) {
        return settings.food(new FoodComponent.Builder().hunger(0).saturationModifier(0)
                .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 15*20, 1), 1.0f)
                .build());
    }*/
}
