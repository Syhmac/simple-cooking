package syhmac.simple_cooking.menus;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import syhmac.simple_cooking.SimpleCooking;

public class ModMenuTypes {
    public static void init(){

    }

    public static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(SimpleCooking.MOD_ID, name), new MenuType<>(supplier, FeatureFlagSet.of()));
    }

    public static final MenuType<StoveMenu> STOVE_MENU_TYPE = register("stove", StoveMenu::new);
}
