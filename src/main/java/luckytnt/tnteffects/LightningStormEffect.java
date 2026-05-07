package luckytnt.tnteffects;

import java.util.List;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningStormEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() < 120) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();

			for (int count = 0; count < 10; count++) {
				double x = entity.x() + random.nextDouble() * 150d - 75d;
				double z = entity.z() + random.nextDouble() * 150d - 75d;
				Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
				lighting.setPos(x, LevelEvents.getTopBlock(level, x, z, false) + 1d, z);
				level.addFreshEntity(lighting);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();

		List<LivingEntity> entities = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().add(-75, -75, -75), entity.getPos().add(75, 75, 75)));
		for (LivingEntity living : entities) {
			double y = LevelEvents.getTopBlock(level, living.getX(), living.getZ(), false) + 1d;
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.getLevel());
			lighting.setPos(living.getX(), y, living.getZ());
			entity.getLevel().addFreshEntity(lighting);

			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), new Vec3(living.getX(), y, living.getZ()), 3);
			explosion.doEntityExplosion(1f, true);
			explosion.doImprovedBlockExplosion(1f, 1.2f, false, false, null);
			explosion.spawnExplosionParticles();
		}
		
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.UNLIMITED_POWER);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.LIGHTNING_STORM.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
