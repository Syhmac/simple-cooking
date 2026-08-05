package syhmac.simple_cooking.recipes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import syhmac.simple_cooking.SimpleCooking;

public class ModRecipes {
    public static void init() {

    }

    public static final RecipeSerializer<StoveRecipe> STOVE_RECIPE_SERIALIZER = Registry.register(
        BuiltInRegistries.RECIPE_SERIALIZER,
        Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, "stove"),
        new RecipeSerializer<>(StoveRecipe.CODEC, StoveRecipe.STREAM_CODEC)
    );

    public static final RecipeType<StoveRecipe> STOVE_RECIPE_TYPE = Registry.register(
            BuiltInRegistries.RECIPE_TYPE,
            Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, "stove"),
            new RecipeType<StoveRecipe>() {}
    );
}
