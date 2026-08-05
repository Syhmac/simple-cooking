package syhmac.simple_cooking;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import syhmac.simple_cooking.blocks.ModBlocks;
import syhmac.simple_cooking.items.ModItems;
import syhmac.simple_cooking.menus.ModMenuTypes;
import syhmac.simple_cooking.recipes.ModRecipes;

public class SimpleCooking implements ModInitializer {
	public static final String MOD_ID = "simple-cooking";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.init();
		ModBlocks.init();
		ModRecipes.init();
		RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.STOVE_RECIPE_SERIALIZER);
		ModMenuTypes.init();
		LOGGER.info("Simple cooking has been initialized.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
