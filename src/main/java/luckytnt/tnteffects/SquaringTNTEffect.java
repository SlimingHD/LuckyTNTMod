package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SquaringTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		if (!entity.getLevel().isClientSide() && ((Entity)entity).onGround() && entity.getPersistentData().getInt("level") > 0) {
			playExplosionSound(entity);
			serverExplosion(entity);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		int tntLevel = entity.getPersistentData().getInt("level");
		
		if (tntLevel == 5) {
			ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), 15);
			explosion.doEntityExplosion(1.5f, true);
			explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
			explosion.spawnExplosionParticles();

			entity.destroy();
			return;
		}

		RandomSource random = level.getRandom();
		int amount = tntLevel == 0 ? 4 : tntLevel * tntLevel;
		for (int count = 0; count < amount; count++) {
			PrimedLTNT tnt = EntityRegistry.SQUARING_TNT.get().create(level);
			tnt.setPos(entity.getPos());
			tnt.setOwner(entity.owner());
			tnt.setDeltaMovement(random.nextDouble() * 2.5d - 1.25d, 1 + random.nextDouble(), random.nextDouble() * 2.5d - 1.25d);
			tnt.getPersistentData().putInt("level", tntLevel + 1);
			level.addFreshEntity(tnt);
		}
		entity.destroy();
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SQUARING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return entity.getPersistentData().getInt("level") == 5 ? 100000 : 200;
	}
}
