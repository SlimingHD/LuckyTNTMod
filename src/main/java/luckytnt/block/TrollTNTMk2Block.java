package luckytnt.block;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class TrollTNTMk2Block extends LTNTBlock{

	public TrollTNTMk2Block(BlockBehaviour.Properties properties) {
		super(properties, EntityRegistry.TROLL_TNT_MK2, false);
	}

	@Override
	public void onCaughtFire(BlockState state, Level level, BlockPos pos, Direction direction, LivingEntity igniter) {
    	if(level.getBlockState(pos.above()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) < 200) {
    		level.setBlock(pos.above(), BlockRegistry.TROLL_TNT_MK2.get().defaultBlockState(), 3);
    	}
    	if(level.getBlockState(pos.below()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) < 200) {
    		level.setBlock(pos.below(), BlockRegistry.TROLL_TNT_MK2.get().defaultBlockState(), 3);
    	}
    	if(level.getBlockState(pos.north()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) < 200) {
    		level.setBlock(pos.north(), BlockRegistry.TROLL_TNT_MK2.get().defaultBlockState(), 3);
    	}
    	if(level.getBlockState(pos.east()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) < 200) {
    		level.setBlock(pos.east(), BlockRegistry.TROLL_TNT_MK2.get().defaultBlockState(), 3);
    	}
    	if(level.getBlockState(pos.south()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) < 200) {
    		level.setBlock(pos.south(), BlockRegistry.TROLL_TNT_MK2.get().defaultBlockState(), 3);
    	}
    	if(level.getBlockState(pos.west()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(level)) < 200) {
    		level.setBlock(pos.west(), BlockRegistry.TROLL_TNT_MK2.get().defaultBlockState(), 3);
    	}
	}
	
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluidState) {
		explode(level, false, pos.getX(), pos.getY(), pos.getZ(), player);
    	return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluidState);
    }
}
