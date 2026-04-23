package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.RegistryManager;

public class EntityFireworkEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
		if (entity.getTNTFuse() == 40) {
  			entity.getPersistentData().putString("type", EntityType.getKey(EntityType.PIG).toString());
			List<LivingEntity> entities = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() - 20, entity.y() - 20, entity.z() - 20, entity.x() + 20, entity.y() + 20, entity.z() + 20));
	      	double maxDistance = 2000;
	      	for (LivingEntity livingEntity : entities) {
	      		double distance = livingEntity.position().distanceTo(entity.getPos());
	      		if (distance < maxDistance && !(livingEntity instanceof Player)) {
	      			maxDistance = distance;
	      			entity.getPersistentData().putString("type", EntityType.getKey(livingEntity.getType()).toString());
	      		}
	      	}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		EntityType<?> type = RegistryManager.FROZEN.getRegistry(Registries.ENTITY_TYPE).getDelegateOrThrow(new ResourceLocation(entity.getPersistentData().getString("type"))).get();
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 300; count++) {
			Entity ent = type.create(entity.getLevel());	
			ent.setPos(entity.x(), entity.y(), entity.z());
			ent.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
			if (ent instanceof Mob mob && entity.getLevel() instanceof ServerLevel sLevel) {
				ForgeEventFactory.onFinalizeSpawn(mob, sLevel, sLevel.getCurrentDifficultyAt(toBlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			entity.getLevel().addFreshEntity(ent);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ENTITY_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
