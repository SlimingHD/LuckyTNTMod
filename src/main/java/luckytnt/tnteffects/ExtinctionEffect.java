package luckytnt.tnteffects;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.LuckyTNTDamageSources;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.entity.EntityTypeTest;

public class ExtinctionEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel serverLevel) {
			List<LightningBolt> lightnings = new ArrayList<LightningBolt>();
			for (Entity toKill : serverLevel.getEntities(EntityTypeTest.forClass(LivingEntity.class), e -> true)) {
				toKill.hurt(LuckyTNTDamageSources.extinction(serverLevel, entity.owner()), 10000f);
				LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
				lightning.setPos(toKill.position());
				lightnings.add(lightning);
			}
			for (LightningBolt lightning : lightnings) {
				serverLevel.addFreshEntity(lightning);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.EXTINCTION.get();
	}
}
