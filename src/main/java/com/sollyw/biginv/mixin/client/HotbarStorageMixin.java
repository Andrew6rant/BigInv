package com.sollyw.biginv.mixin.client;

import net.minecraft.client.option.HotbarStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HotbarStorage.class)
public class HotbarStorageMixin {

    @ModifyConstant(method = {"<clinit>", "<init>(Ljava/io/File;Lcom/mojang/datafixers/DataFixer;)V", "load()V", "save()V"},
            constant = @Constant(intValue = 9))
    private static int littlebiginv$modifySelectedTabIndex(int constant) {
        return 14;
    }
}
