package tech.lemonlime.configured.mixin.feature.disableEnd;


import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.lemonlime.configured.Settings;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {


    @Inject(method="onEntityCollision",at=@At("HEAD"), cancellable = true)
    private void configured$conditionalEndPortalTravel(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (Settings.disableEnd) {
            ci.cancel();
        }
    }



}
