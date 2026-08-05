package syhmac.simple_cooking.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import syhmac.simple_cooking.client.screens.StoveScreen;
import syhmac.simple_cooking.menus.ModMenuTypes;

public class SimpleCookingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenuTypes.STOVE_MENU_TYPE, StoveScreen::new);
	}
}