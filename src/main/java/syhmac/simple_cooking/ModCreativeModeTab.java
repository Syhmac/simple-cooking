package syhmac.simple_cooking;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import syhmac.simple_cooking.blocks.ModBlocks;
import syhmac.simple_cooking.items.ModItems;

public class ModCreativeModeTab {
    public static final ResourceKey<CreativeModeTab> SIMPLE_COOKING_TAB_KEY = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, "simple_cooking_tab")
    );

    public static final CreativeModeTab SIMPLE_COOKING_TAB = FabricCreativeModeTab.builder()
        .icon(() -> new ItemStack(ModBlocks.STOVE))
        .title(Component.translatable("creativeTab.simple_cooking_tab"))
        .displayItems((params, output) -> {
            //blocks
            output.accept(ModBlocks.STOVE.asItem());
            // items
            output.accept(ModItems.CHOCOLATE);
        }).build();
}
