package com.sollyw.biginv;

import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.data.EntitySlotLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BigInv implements ModInitializer {
    public static final String BIGINV = "biginv";
    public static final Logger LOGGER = LogManager.getLogger(BIGINV);

    public static Identifier id(String path) {
        return new Identifier(BIGINV, path);
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            //System.out.println("Server started!!!");
            //EntitySlotLoader.SERVER.sync(server.getPlayerManager().getPlayerList());
        });
        TrinketsApi.registerTrinketPredicate(new Identifier(BIGINV, "biginv"), (itemStack, slotReference, livingEntity) -> {
            return TriState.TRUE;
        });
    }
}
