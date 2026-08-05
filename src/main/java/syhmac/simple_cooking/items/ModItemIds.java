package syhmac.simple_cooking.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import syhmac.simple_cooking.SimpleCooking;

public class ModItemIds {
    public static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, name));
    }

    public static final ResourceKey<Item> CHOCOLATE = create("chocolate");
}
