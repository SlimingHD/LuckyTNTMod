package luckytnt.block;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class TrollTNTBlock extends LTNTBlock {
   
	public TrollTNTBlock(BlockBehaviour.Properties properties) {
        super(properties, EntityRegistry.TROLL_TNT, false);
    }

	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluidState) {
		if (level instanceof ServerLevel server) {
			for (Direction dir : Direction.values()) {
				BlockPos blockpos = pos.relative(dir);
				if (level.getBlockState(blockpos).getExplosionResistance(level, blockpos, ImprovedExplosion.dummyExplosion(server)) < 200) {
					level.setBlock(blockpos, BlockRegistry.TROLL_TNT.get().defaultBlockState(), 3);
				}
			}
		}
		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluidState);
	}
}
