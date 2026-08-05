package syhmac.simple_cooking.menus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import syhmac.simple_cooking.blocks.ModBlocks;
import syhmac.simple_cooking.recipes.StoveRecipe;
import syhmac.simple_cooking.recipes.StoveRecipeInput;

import java.util.List;
import java.util.Optional;

import static syhmac.simple_cooking.recipes.ModRecipes.STOVE_RECIPE_TYPE;

public class StoveMenu extends AbstractContainerMenu {
    private final Container input = new SimpleContainer(5) {
        @Override
        public void setChanged() {
            super.setChanged();
            StoveMenu.this.slotsChanged(this);
        }
    };

    private final ResultContainer output = new ResultContainer();

    private final ContainerLevelAccess access;

    @Nullable
    private final Player player;

    public StoveMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public StoveMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.STOVE_MENU_TYPE, containerId);

        this.access = access;
        this.player = inventory.player;

        addSlot(new Slot(this.input, 0, 43, 17));
        addSlot(new Slot(this.input, 1, 61, 17));
        addSlot(new Slot(this.input, 2, 79, 17));
        addSlot(new Slot(this.input, 3, 97, 17));
        addSlot(new Slot(this.input, 4, 115, 17));

        addSlot(new StoveResultSlot(this, this.output, 0, 111, 53));

        addStandardInventorySlots(inventory, 8, 84);
    }

    protected void onTake(final Player player, final ItemStack stack) {
        stack.onCraftedBy(player, stack.getCount());
        this.output.awardUsedRecipes(player, List.of(
                this.input.getItem(0), this.input.getItem(1),
                this.input.getItem(2), this.input.getItem(3), this.input.getItem(4)
        ));

        for (int i = 0; i < 5; i++) {
            consumeIngredient(i);
        }
    }

    private void consumeIngredient(int slotIndex) {
        ItemStack stack = this.input.getItem(slotIndex);

        if (stack.isEmpty()) {
            return;
        }

        ItemStackTemplate remainderTemplate = stack.getItem().getCraftingRemainder();
        stack.shrink(1);

        if (remainderTemplate != null && stack.isEmpty()) {
            this.input.setItem(slotIndex, remainderTemplate.create());
        }
    }

    @Override
    public void slotsChanged(@NonNull Container container) {
        super.slotsChanged(container);

        if (container != this.input) {
            return;
        }

        this.access.execute((level, blockPos) -> {
            if (level instanceof ServerLevel serverLevel) {
                updateOutput(serverLevel);
            }
        });
    }

    private void updateOutput(ServerLevel serverLevel) {
        if (!(this.player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        StoveRecipeInput recipeInput = buildRecipeInput();
        ItemStack result = resolveCraftingResult(serverLevel, recipeInput);

        this.output.setItem(0, result);
    }

    private StoveRecipeInput buildRecipeInput() {
        return new StoveRecipeInput(List.of(
                this.input.getItem(0),
                this.input.getItem(1),
                this.input.getItem(2),
                this.input.getItem(3),
                this.input.getItem(4)
        ));
    }

    private ItemStack resolveCraftingResult(ServerLevel serverLevel, StoveRecipeInput recipeInput) {
        Optional<RecipeHolder<StoveRecipe>> maybeRecipe =
                serverLevel.recipeAccess().getRecipeFor(STOVE_RECIPE_TYPE, recipeInput, serverLevel);

        if (maybeRecipe.isEmpty()) {
            return ItemStack.EMPTY;
        }

        RecipeHolder<StoveRecipe> recipeHolder = maybeRecipe.get();

        if (!this.output.setRecipeUsed((ServerPlayer) this.player, recipeHolder)) {
            return ItemStack.EMPTY;
        }

        ItemStack recipeResult = recipeHolder.value().assemble(recipeInput);
        return recipeResult.isItemEnabled(serverLevel.enabledFeatures()) ? recipeResult : ItemStack.EMPTY;
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return stillValid(this.access, player, ModBlocks.STOVE);
    }

    @Override
    public void removed(@NonNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.output));
    }

    @Override
    public boolean canTakeItemForPickAll(final @NonNull ItemStack carried, final Slot target) {
        return target.container != this.output && super.canTakeItemForPickAll(carried, target);
    }
}
