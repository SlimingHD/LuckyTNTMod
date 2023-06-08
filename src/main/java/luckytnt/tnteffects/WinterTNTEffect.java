package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.entity.SnowySnowball;
import luckytnt.registry.BlockRegistry;
import luckytnt.util.Materials;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WinterTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 150, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200) {
					if(state.is(Blocks.BUBBLE_COLUMN) || state.is(Blocks.WATER) || Materials.isWaterPlant(state) || state.getBlock() == Blocks.WATER) {
						level.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
					}
				}
			}
		});
		
		ExplosionHelper.doTopBlockExplosionForAll(ent.getLevel(), ent.getPos(), 150, new IForEachBlockExplosionEffect() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200 && Blocks.SNOW.canSurvive(state, level, pos)) {
					level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 3);
				}
			}
		});
	}
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		for(int i = 0; i <= 50; i++) {
			SnowySnowball ball = new SnowySnowball(ent.getLevel(), ent.x() + Math.random() * 100 - Math.random() * 100, ent.y() + 30D, ent.z() + Math.random() * 100 - Math.random() * 100);
			ball.setDeltaMovement(Math.random() * 0.1D - Math.random() * 0.1D, -0.1D - Math.random() * 0.4D, Math.random() * 0.1D - Math.random() * 0.1D);
			ent.getLevel().addFreshEntity(ball);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x(), ent.y() + 1D, ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WINTER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 200;
	}
}
