package syhmac.simple_cooking.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record StoveRecipeInput(List<ItemStack> ingredients) implements RecipeInput {
    @Override
    public @NonNull ItemStack getItem(int index) {
        return ingredients.get(index);
    }

    @Override
    public int size() {
        return ingredients.size();
    }
}
