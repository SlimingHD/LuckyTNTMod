package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EasterEggEffect extends PrimedTNTEffect{
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		if(((Entity)entity).isOnGround() && ((Entity)entity).getPersistentData().getInt("level") > 0) {
			serverExplosion(entity);
			Level level = entity.getLevel();
			entity.getLevel().playSound((Entity)entity, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
			entity.destroy();
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int level = ((Entity)entity).getPersistentData().getInt("level");
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 15);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(Math.random() < 0.66f && !state.isAir()) {
					state.onBlockExploded(level, pos, explosion);
					if(Math.random() < 0.5f) {
						entity.getLevel().setBlockAndUpdate(pos, Blocks.MELON.defaultBlockState());
					}
					else {
						entity.getLevel().setBlockAndUpdate(pos, Blocks.PUMPKIN.defaultBlockState());
					}
				}
			}
		});
		if(level + 1 == 4) {
			entity.destroy();
		}
		else {
			for(int count = 0; count < 4; count++) {
				PrimedLTNT tnt = EntityRegistry.EASTER_EGG.get().create(entity.getLevel());
				tnt.setPos(entity.getPos());
				tnt.setOwner(entity.owner());
				tnt.setDeltaMovement(Math.random() * 2 - 1, 1 + Math.random(), Math.random() * 2 - 1);
				tnt.getPersistentData().putInt("level", level + 1);
				entity.getLevel().addFreshEntity(tnt);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0.5f, 0f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0f), 1), entity.x() + 0.2f, entity.y() + 1f, entity.z() + 0.2f, 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0f), 1), entity.x() - 0.2f, entity.y() + 1f, entity.z() - 0.2f, 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0f), 1), entity.x() + 0.2f, entity.y() + 1f, entity.z() - 0.2f, 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0f), 1), entity.x() - 0.2f, entity.y() + 1f, entity.z() + 0.2f, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.EASTER_EGG.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
