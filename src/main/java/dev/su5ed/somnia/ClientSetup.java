package dev.su5ed.somnia;


import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

import net.minecraft.resources.ResourceLocation;

public final class ClientSetup {
    public static final ResourceLocation LOCATION_FATIGUE_OVERLAY = ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "fatigue_overlay");

    static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerBelowAll(LOCATION_FATIGUE_OVERLAY, ClientSleepHandler.INSTANCE::renderGuiOverlay);
    }
    
    private ClientSetup() {}
}
