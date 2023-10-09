package tech.lemonlime.Configurable.mixin.feature.disableNether;


import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.lemonlime.Configurable.Settings;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {




    @Inject(method="onEntityCollision",at=@At("HEAD"), cancellable = true)
    private void configured$conditionalNetherPortalTravel(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (Settings.disableNether) {
            ci.cancel();
        }

    }








}
