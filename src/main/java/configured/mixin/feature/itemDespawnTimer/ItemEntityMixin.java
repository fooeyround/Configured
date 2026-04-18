package configured.mixin.feature.itemDespawnTimer;


import configured.Settings;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @ModifyConstant(method = {"tick", "isMergable"}, constant = @Constant(intValue = 6000))
    private int configured$ItemDespawnAge(int def) {
        return Settings.itemDespawnAge;
    }

    @ModifyConstant(method = "makeFakeItem", constant = @Constant(intValue = 5999))
    private int configured$ItemDespawnAge$setDespawnImmediately(int def) {
        return Settings.itemDespawnAge-1;
    }

    @ModifyConstant(method = "setExtendedLifetime", constant = @Constant(intValue = -6000))
    private int configured$setCov(int def) {
        return -Settings.itemDespawnAge;
    }

}
