package syhmac.simple_cooking.client.screens;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import syhmac.simple_cooking.SimpleCooking;
import syhmac.simple_cooking.menus.StoveMenu;

public class StoveScreen extends AbstractContainerScreen<StoveMenu> {
    private static final Identifier SCREEN_TEXTURE = Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, "textures/gui/container/stove.png");

    public StoveScreen(StoveMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SCREEN_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}
