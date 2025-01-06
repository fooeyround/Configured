package configured.mixin.feature.itemDespawnTimer;


import configured.Settings;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 6000))
    private int configured$ItemDespawnAge(int def) {
        return Settings.itemDespawnAge;
    }

}
