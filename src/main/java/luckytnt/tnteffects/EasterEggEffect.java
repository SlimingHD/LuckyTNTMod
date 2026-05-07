package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class EasterEggEffect extends PrimedTNTEffect{
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		Entity ent = (Entity)entity;
		if (ent.onGround() && entity.getPersistentData().getInt("level") > 0) {
			serverExplosion(entity);
			playExplosionSound(entity);
			entity.destroy();
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int level = entity.getPersistentData().getInt("level");
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 15);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, new FilterAirExplosionRule(
			new FilterRandomExplosionRule(0.66f,
				new RandomBlockExplosionRule(RandomList.ofEqualProbability(Blocks.MELON.defaultBlockState(), Blocks.PUMPKIN.defaultBlockState()))
			)
		));
		if (level + 1 == 4) {
			return;
		}
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 4; count++) {
			PrimedLTNT tnt = EntityRegistry.EASTER_EGG.get().create(entity.getLevel());
			tnt.setPos(entity.getPos());
			tnt.setOwner(entity.owner());
			tnt.setDeltaMovement(random.nextDouble() * 2d - 1d, 1d + random.nextDouble(), random.nextDouble() * 2d - 1d);
			tnt.getPersistentData().putInt("level", level + 1);
			entity.getLevel().addFreshEntity(tnt);
		}
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.FALSE_ADVERTISING);
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
