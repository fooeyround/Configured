package tech.lemonlime.configured.mixin.fix.stringDupeFix;



import net.minecraft.block.TripwireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tech.lemonlime.configured.Fixes;

/**
 * Fixes the wring dupe at the cost of removing the ability to disarm tripwire. Wait you didn't know you could do that?
 * That's why I decided to do it xD
 *  - FX
 *
 *
 * I wish I had time to make a real fix D:
 *  - Jack
 */

@Mixin(TripwireBlock.class)
public class TripwireBlockMixin {


    @ModifyArg(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/TripwireHookBlock;update(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;" +
                            "ZZILnet/minecraft/block/BlockState;)V"
            ),
            index = 5
    )
    private int alwaysNegativeOne(int i) {
        return Fixes.stringDupeFix ? -1 : i;
    }
}