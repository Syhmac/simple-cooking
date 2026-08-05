package syhmac.simple_cooking.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class StoveResultSlot extends Slot {
    private final StoveMenu menu;

    public StoveResultSlot(StoveMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public void onTake(@NonNull Player player, @NonNull ItemStack carried) {
        this.menu.onTake(player, carried);
    }

    @Override
    public boolean mayPlace(@NonNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFake() {
        return true;
    }
}
