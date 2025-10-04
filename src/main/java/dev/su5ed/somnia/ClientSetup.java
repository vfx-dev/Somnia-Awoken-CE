package dev.su5ed.somnia;


import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;

public final class ClientSetup {

    static void registerGuiOverlays(AddGuiOverlayLayersEvent event) {
        event.getLayeredDraw().addBelow(ResourceLocation.fromNamespaceAndPath(SomniaAwoken.MODID, "fatigure_overlay"), ForgeLayeredDraw.HOTBAR, ClientSleepHandler.INSTANCE::renderGuiOverlay);
    }
    
    private ClientSetup() {}
}
