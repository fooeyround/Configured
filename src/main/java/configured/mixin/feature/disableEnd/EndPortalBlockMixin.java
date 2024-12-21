package configured.mixin.feature.disableEnd;


import configured.Settings;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class EndPortalBlockMixin {


    private void configured$conditionalEndPortalTravel(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (Settings.disableEnd) {
            ci.cancel();
        }
    }



}
