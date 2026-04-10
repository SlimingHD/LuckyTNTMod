package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class TNTFireworkEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 300; count++) {
			PrimedLTNT tnt = EntityRegistry.TNT.get().create(entity.getLevel());
			tnt.setPos(entity.getPos());
			tnt.setOwner(entity.owner());
			tnt.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
			tnt.setFuse(tnt.getFuse() / 2 + (int) (tnt.getFuse() * (random.nextDouble() + 0.2f)));
			entity.getLevel().addFreshEntity(tnt);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.TNT_FIREWORK.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
