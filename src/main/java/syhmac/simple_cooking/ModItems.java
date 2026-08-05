package syhmac.simple_cooking;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class ModItems {
    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SIMPLE_COOKING_TAB_KEY, SIMPLE_COOKING_TAB);
    }

    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static final Item CHOCOLATE = register(
            ModItemIds.CHOCOLATE,
            Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(3).build())
    );

    public static final ResourceKey<CreativeModeTab> SIMPLE_COOKING_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, "simple_cooking_tab")
    );

    public static final CreativeModeTab SIMPLE_COOKING_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(CHOCOLATE))
            .title(Component.translatable("creativeTab.simple_cooking_tab"))
            .displayItems((params, output) -> {
                output.accept(CHOCOLATE);
            }).build();
}
