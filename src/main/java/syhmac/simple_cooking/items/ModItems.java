package syhmac.simple_cooking.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import syhmac.simple_cooking.ModCreativeModeTab;

import java.util.function.Function;

public class ModItems {
    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModCreativeModeTab.SIMPLE_COOKING_TAB_KEY, ModCreativeModeTab.SIMPLE_COOKING_TAB);
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
}
