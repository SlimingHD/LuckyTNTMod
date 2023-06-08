package luckytnt.tnteffects.projectile;

import luckytnt.util.Materials;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SolarEruptionProjectileEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 8);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.2f, true, false);
		
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 5, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if((ent.y() - pos.getY()) >= 0 && (ent.y() - pos.getY()) <= 3) {
					if((state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 100 || state.getBlock() instanceof LiquidBlock || state.isAir()) && Materials.isStone(state)) {
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, true, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
	
	@Override
	public float getSize(IExplosiveEntity entity) {
		return 1f;
	}
}
