package luckytnt.tnteffects;


import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SnowstormTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 10 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200 && Block.isFaceFull(state.getCollisionShape(level, pos), Direction.UP)) {
					state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					level.setBlock(pos, Blocks.BLUE_ICE.defaultBlockState(), 3);
				} else if(distance > 10 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200 && state.getBlock() == Blocks.WATER) {
					level.setBlock(pos, Blocks.ICE.defaultBlockState(), 3);
				}
			}
		});
		
		ExplosionHelper.doTopBlockExplosionForAll(ent.getLevel(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 3);
			}
		});
	}
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() % 4 == 0) {
			Vec3 vec31 = new Vec3(0.5D, 1D, 0D).normalize();
			Snowball ball1 = new Snowball(ent.getLevel(), ent.x() + 0.5D, ent.y() + 1D, ent.z());
			ball1.shoot(vec31.x, vec31.y, vec31.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball1);
			
			Vec3 vec32 = new Vec3(-0.5D, 1D, 0D).normalize();
			Snowball ball2 = new Snowball(ent.getLevel(), ent.x() - 0.5D, ent.y() + 1D, ent.z());
			ball2.shoot(vec32.x, vec32.y, vec32.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball2);
			
			Vec3 vec33 = new Vec3(0D, 1D, 0.5D).normalize();
			Snowball ball3 = new Snowball(ent.getLevel(), ent.x(), ent.y() + 1D, ent.z() + 0.5D);
			ball3.shoot(vec33.x, vec33.y, vec33.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball3);
			
			Vec3 vec34 = new Vec3(0D, 1D, -0.5D).normalize();
			Snowball ball4 = new Snowball(ent.getLevel(), ent.x(), ent.y() + 1D, ent.z() - 0.5D);
			ball4.shoot(vec34.x, vec34.y, vec34.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball4);
			
			Vec3 vec35 = new Vec3(0.5D, 1D, 0.5D).normalize();
			Snowball ball5 = new Snowball(ent.getLevel(), ent.x() + 0.5D, ent.y() + 1D, ent.z() + 0.5D);
			ball5.shoot(vec35.x, vec35.y, vec35.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball5);
			
			Vec3 vec36 = new Vec3(-0.5D, 1D, 0.5D).normalize();
			Snowball ball6 = new Snowball(ent.getLevel(), ent.x() - 0.5D, ent.y() + 1D, ent.z() + 0.5D);
			ball6.shoot(vec36.x, vec36.y, vec36.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball6);
			
			Vec3 vec37 = new Vec3(0.5D, 1D, -0.5D).normalize();
			Snowball ball7 = new Snowball(ent.getLevel(), ent.x() + 0.5D, ent.y() + 1D, ent.z() - 0.5D);
			ball7.shoot(vec37.x, vec37.y, vec37.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball7);
			
			Vec3 vec38 = new Vec3(-0.5D, 1D, -0.5D).normalize();
			Snowball ball8 = new Snowball(ent.getLevel(), ent.x() - 0.5D, ent.y() + 1D, ent.z() - 0.5D);
			ball8.shoot(vec38.x, vec38.y, vec38.z, 1f, 5f);
			ent.getLevel().addFreshEntity(ball8);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x(), ent.y() + 1D, ent.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SNOWSTORM_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
