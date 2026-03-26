package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.network.ClientboundHydrogenBombPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytnt.util.NuclearBombLike;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.ScheduleTickExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PacketDistributor;

public class TsarBombaBombEffect extends PrimedTNTEffect implements NuclearBombLike {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(entity.getLevel() instanceof ServerLevel) {
			PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> (Entity)entity), new ClientboundHydrogenBombPacket(((Entity)entity).getId()));
		}
		
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 160);
		explosion.doEntityExplosion(15f, true);
		explosion.doImprovedBlockExplosion(0.167f, 0.05f, false, true, null);
		ExplosionHelper.createSpheroidCrater(entity.getLevel(), entity.getPos(), 300, new Vector3f(1f, 0.3333f, 1f), 0.2f, new FilterAirExplosionRule(FilterBlockExplosionRule.applyOnlyWhen(BlockTags.LEAVES, new CraterExplosionRule())));
		ExplosionHelper.createSpheroidCrater(entity.getLevel(), entity.getPos(), 150, new Vector3f(1f, 0.6666667f, 1f), 0, new FilterRandomExplosionRule(0.4f,
				FilterRandomDistanceExplosionRule.quadraticDecrease(0, 150,
						new ScheduleTickExplosionRule(
								new CanSurviveExplosionRule(BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState())
						)
				)
		));
		
		List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() - 90, entity.y() - 65, entity.z() - 90, entity.x() + 90, entity.y() + 65, entity.z() + 90));
		for(LivingEntity living : list) {
			living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 3600, 0, true, true, true));
		}
	}
	
	@Override
	public void displayMushroomCloud(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		for(int count = 0; count < 1500; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 60 - random.nextDouble() * 60, entity.y() + random.nextDouble() * 3 - random.nextDouble() * 3, entity.z() + random.nextDouble() * 60 - random.nextDouble() * 60, 0, 0, 0);
		}
		for(int count = 0; count < 1000; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 20 - random.nextDouble() * 20, entity.y() + 3 + random.nextDouble() * 3 - random.nextDouble() * 3, entity.z() + random.nextDouble() * 20 - random.nextDouble() * 20, 0, 0, 0);
		}
		for(int count = 0; count < 800; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 10 - random.nextDouble() * 10, entity.y() + random.nextDouble() * 3 - random.nextDouble() * 3, entity.z() + random.nextDouble() * 10 - random.nextDouble() * 10, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 6 - random.nextDouble() * 6, entity.y() + 4 + random.nextDouble() * 3 - random.nextDouble() * 3, entity.z() + random.nextDouble() * 6 - random.nextDouble() * 6, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 2 - random.nextDouble() * 2, entity.y() + 15 + random.nextDouble() * 12 - random.nextDouble() * 12, entity.z() + random.nextDouble() * 2 - random.nextDouble() * 2, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 6 - random.nextDouble() * 6, entity.y() + 22 + random.nextDouble() * 3 - random.nextDouble() * 3, entity.z() + random.nextDouble() * 6 - random.nextDouble() * 6, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 6 - random.nextDouble() * 6, entity.y() + 29 + random.nextDouble() * 3 - random.nextDouble() * 3, entity.z() + random.nextDouble() * 6 - random.nextDouble() * 6, 0, 0, 0);
		}
		for(int count = 0; count < 2000; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, entity.x() + random.nextDouble() * 12 - random.nextDouble() * 12, entity.y() + 24 + random.nextDouble() * 6 - random.nextDouble() * 6, entity.z() + random.nextDouble() * 12 - random.nextDouble() * 12, 0, 0, 0);
		}
		for(int count = 0; count < 2000; count++) {
			level.addParticle(ParticleTypes.LARGE_SMOKE, true, entity.x() + random.nextDouble() * 2 - random.nextDouble() * 2, entity.y() + 22 + random.nextDouble() * 2 - random.nextDouble() * 2, entity.z() + random.nextDouble() * 2 - random.nextDouble() * 2, random.nextDouble() * 2 - random.nextDouble() * 2, random.nextDouble() * 2 - random.nextDouble() * 2, random.nextDouble() * 2 - random.nextDouble() * 2);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
	}
}
