package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class IgniterTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public IgniterTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getBlock() instanceof LTNTBlock) {
					((LTNTBlock)state.getBlock()).explode(level, false, pos.getX(), pos.getY(), pos.getZ(), entity.owner() instanceof LivingEntity ? (LivingEntity)entity.owner() : null);
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.IGNITER_TNT.get();
	}
}
