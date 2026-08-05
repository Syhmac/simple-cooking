package syhmac.simple_cooking.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class StoveRecipe implements Recipe<StoveRecipeInput> {
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;

    public StoveRecipe(ItemStackTemplate result, List<Ingredient> ingredients) {
        this.result = result;
        this.ingredients = ingredients;
    }

    public ItemStackTemplate getResult() {
        return this.result;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    /*
    -- CLAUDE SONNET 5
     */
    @Override
    public boolean matches(StoveRecipeInput recipeInput, @NonNull Level level) {
        List<ItemStack> nonEmptyInputs = recipeInput.ingredients().stream()
                .filter(stack -> !stack.isEmpty())
                .toList();

        if (nonEmptyInputs.size() != this.ingredients.size()) {
            return false;
        }

        return canMatch(0, nonEmptyInputs, new boolean[nonEmptyInputs.size()]);
    }

    private boolean canMatch(int ingredientIndex, List<ItemStack> inputs, boolean[] used) {
        if (ingredientIndex == this.ingredients.size()) {
            return true;
        }

        Ingredient ingredient = this.ingredients.get(ingredientIndex);

        for (int i = 0; i < inputs.size(); i++) {
            if (!used[i] && ingredient.test(inputs.get(i))) {
                used[i] = true;
                if (canMatch(ingredientIndex + 1, inputs, used)) {
                    return true;
                }
                used[i] = false; // backtrack
            }
        }

        return false;
    }
    /*
    -- OF CLAUDE SONNET 5
     */

    @Override
    public @NonNull ItemStack assemble(@NonNull StoveRecipeInput recipeInput) {
        return this.result.create();
    }

    public static final MapCodec<StoveRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(StoveRecipe::getResult),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(StoveRecipe::getIngredients)
        ).apply(instance, StoveRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, StoveRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStackTemplate.STREAM_CODEC,
            StoveRecipe::getResult,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
            StoveRecipe::getIngredients,
            StoveRecipe::new
    );

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<StoveRecipeInput>> getSerializer() {
        return ModRecipes.STOVE_RECIPE_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<StoveRecipeInput>> getType() {
        return ModRecipes.STOVE_RECIPE_TYPE;
    }

    @Override
    public @Nullable RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public @NonNull String group() {
        return "Stove";
    }
}
