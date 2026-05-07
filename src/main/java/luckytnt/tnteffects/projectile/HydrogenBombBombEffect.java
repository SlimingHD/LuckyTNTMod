package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.network.ClientboundHydrogenBombPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytnt.util.NuclearBombLike;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PacketDistributor;

public class HydrogenBombBombEffect extends PrimedTNTEffect implements NuclearBombLike {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> (Entity)entity), new ClientboundHydrogenBombPacket(((Entity)entity).getId()));
		
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 230);
		explosion.setExplosionFinishWork(exp -> finishNuclearExplosion(entity));
		explosion.doEntityExplosion(25f, true);
		explosion.doImprovedBlockExplosion(0.167f, 0.05f, true, false, null);
		
		List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() - 90, entity.y() - 65, entity.z() - 90, entity.x() + 90, entity.y() + 65, entity.z() + 90));
		for (LivingEntity living : list) {
			living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 4800, 0, true, true, true));
		}
		
		AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.BOUNDLESS_INCOMPETENCE);
	}
	
	private void finishNuclearExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSpheroidCrater(entity.getLevel(), entity.getPos(), 400, new Vector3f(1f, 0.3333f, 1f), 0.2f, FilterBlockExplosionRule.builder().filterForTag(BlockTags.LEAVES).filterForBlocks(Blocks.SNOW, Blocks.VINE).build(new CraterExplosionRule()));
		ExplosionHelper.createSpheroidCrater(entity.getLevel(), entity.getPos(), 250, new Vector3f(1f, 0.6666667f, 1f), 0, new FilterSurfaceExplosionRule(false,
			new FilterRandomExplosionRule(0.5f, 
				FilterRandomDistanceExplosionRule.quadraticDecrease(0, 250,
					new CanSurviveExplosionRule(BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState())
				)
			)
		));
	}
	
	@Override
	public void displayMushroomCloud(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		for (int count = 0; count < 3000; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 120 - random.nextDouble() * 120, entity.y() + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 120 - random.nextDouble() * 120, 0, 0, 0);
		}
		for (int count = 0; count < 2000; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 40 - random.nextDouble() * 40, entity.y() + 6 + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 40 - random.nextDouble() * 40, 0, 0, 0);
		}
		for (int count = 0; count < 1600; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 20 - random.nextDouble() * 20, entity.y() + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 20 - random.nextDouble() * 20, 0, 0, 0);
		}
		for (int count = 0; count < 1200; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 12 - random.nextDouble() * 12, entity.y() + 8 + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 12 - random.nextDouble() * 12, 0, 0, 0);
		}
		for (int count = 0; count < 1200; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 4 - random.nextDouble() * 4, entity.y() + 30 + random.nextDouble() * 24 - random.nextDouble() * 24, entity.z() + random.nextDouble() * 4 - random.nextDouble() * 4, 0, 0, 0);
		}
		for (int count = 0; count < 1200; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 12 - random.nextDouble() * 12, entity.y() + 44 + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 12 - random.nextDouble() * 12, 0, 0, 0);
		}
		for (int count = 0; count < 1200; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 12 - random.nextDouble() * 12, entity.y() + 58 + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 12 - random.nextDouble() * 12, 0, 0, 0);
		}
		for (int count = 0; count < 4000; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 24 - random.nextDouble() * 24, entity.y() + 48 + random.nextDouble() * 12 - random.nextDouble() * 12, entity.z() + random.nextDouble() * 24 - random.nextDouble() * 24, 0, 0, 0);
		}
		for (int count = 0; count < 4000; count++) {
			level.addParticle(ParticleTypes.LARGE_SMOKE, true, entity.x() + random.nextDouble() * 4 - random.nextDouble() * 4, entity.y() + 44 + random.nextDouble() * 4 - random.nextDouble() * 4, entity.z() + random.nextDouble() * 4 - random.nextDouble() * 4, random.nextDouble() * 4 - random.nextDouble() * 4, random.nextDouble() * 4 - random.nextDouble() * 4, random.nextDouble() * 4 - random.nextDouble() * 4);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
	}
	
	@Override
	public float getSize(IExplosiveEntity ent) {
		return 1.5f;
	}
}
