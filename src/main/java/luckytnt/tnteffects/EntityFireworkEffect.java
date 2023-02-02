package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class EntityFireworkEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
		if(ent.getTNTFuse() == 40) {
			List<LivingEntity> ents = ent.level().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 20, ent.y() - 20, ent.z() - 20, ent.x() + 20, ent.y() + 20, ent.z() + 20));
	      	double distance = 2000;
	      	for(LivingEntity lent : ents) {
	      		double xD = lent.getX() - ent.x();
	      		double yD = lent.getY() - ent.y();
	      		double zD = lent.getZ() - ent.z();
	      		double d = Math.sqrt(xD * xD + yD * yD + zD * zD);
	      		if(d < distance && !(lent instanceof Player)) {
	      			distance = d;
	      			ent.getPersistentData().putString("type", EntityType.getKey(lent.getType()).toString());
	      		}
	      	}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(ent.getPersistentData().getString("type")));
		if(type == null) {
			type = EntityType.PIG;
		}
		for(int count = 0; count < 300; count++) {
			Entity lent = type.create(ent.level());	
			lent.setPos(ent.x(), ent.y(), ent.z());
			lent.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
			if(lent instanceof Mob mob && ent.level() instanceof ServerLevel sLevel) {
				mob.finalizeSpawn(sLevel, sLevel.getCurrentDifficultyAt(new BlockPos(ent.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			ent.level().addFreshEntity(lent);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ENTITY_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 40;
	}
}
