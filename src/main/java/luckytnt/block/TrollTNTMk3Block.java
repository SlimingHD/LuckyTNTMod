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

public class TrollTNTMk3Block extends LTNTBlock {

	public TrollTNTMk3Block(BlockBehaviour.Properties properties) {
		super(properties, EntityRegistry.TROLL_TNT_MK3, false);
	}

	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluidState) {
		if (level instanceof ServerLevel server) {
			for (Direction dir : Direction.values()) {
				BlockPos blockpos = pos.relative(dir);
				if (level.getBlockState(blockpos).getExplosionResistance(level, blockpos, ImprovedExplosion.dummyExplosion(server)) < 200) {
					level.setBlock(blockpos, BlockRegistry.TROLL_TNT_MK3.get().defaultBlockState(), 3);
				}
			}
		}
		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluidState);
	}
}
