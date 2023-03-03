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
import luckytntlib.util.explosions.IBlockExplosionCondition;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class HydrogenBombBombEffect extends PrimedTNTEffect implements NuclearBombLike {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		if(ent.level() instanceof ServerLevel sl) {
			PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> (Entity)ent), new ClientboundHydrogenBombPacket(((Entity)ent).getId()));
		}
		
		ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), ent.getPos(), 230f);
		explosion.doEntityExplosion(25f, true);
		explosion.doBlockExplosion(1f, 1f, 0.167f, 0.05f, false, true);
		
		ExplosionHelper.doModifiedSphericalExplosion(ent.level(), ent.getPos(), 250, new Vec3(1f, (2f/3f), 1f), new IBlockExplosionCondition() {
			
			@Override
			public boolean conditionMet(Level level, BlockPos pos, BlockState state, double distance) {
				return true;
			}
		}, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, 1, 0);
				BlockState stateTop = level.getBlockState(posTop);
				if(distance <= 250) {
					if(Math.random() < 0.25f) {
						if(Block.isFaceFull(state.getCollisionShape(level, pos), Direction.UP) && state.getMaterial() != Material.AIR && !Block.isFaceFull(stateTop.getCollisionShape(level, posTop), Direction.UP) && stateTop.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion()) < 200) {
							level.setBlock(posTop, BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState(), 3);
						}
					}
				}
			}
		});
		
		List<LivingEntity> list = ent.level().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 90, ent.y() - 65, ent.z() - 90, ent.x() + 90, ent.y() + 65, ent.z() + 90));
		for(LivingEntity living : list) {
			living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 4800, 0, true, true, true));
		}
	}
	
	@Override
	public void displayMushroomCloud(IExplosiveEntity ent) {
		for(int count = 0; count < 3000; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 120 - Math.random() * 120, ent.y() + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 120 - Math.random() * 120, 0, 0, 0);
		}
		for(int count = 0; count < 2000; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 40 - Math.random() * 40, ent.y() + 6 + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 40 - Math.random() * 40, 0, 0, 0);
		}
		for(int count = 0; count < 1600; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 20 - Math.random() * 20, ent.y() + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 20 - Math.random() * 20, 0, 0, 0);
		}
		for(int count = 0; count < 1200; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 12 - Math.random() * 12, ent.y() + 8 + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 12 - Math.random() * 12, 0, 0, 0);
		}
		for(int count = 0; count < 1200; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 4 - Math.random() * 4, ent.y() + 30 + Math.random() * 24 - Math.random() * 24, ent.z() + Math.random() * 4 - Math.random() * 4, 0, 0, 0);
		}
		for(int count = 0; count < 1200; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 12 - Math.random() * 12, ent.y() + 44 + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 12 - Math.random() * 12, 0, 0, 0);
		}
		for(int count = 0; count < 1200; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 12 - Math.random() * 12, ent.y() + 58 + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 12 - Math.random() * 12, 0, 0, 0);
		}
		for(int count = 0; count < 4000; count++) {
			ent.level().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 24 - Math.random() * 24, ent.y() + 48 + Math.random() * 12 - Math.random() * 12, ent.z() + Math.random() * 24 - Math.random() * 24, 0, 0, 0);
		}
		for(int count = 0; count < 4000; count++) {
			ent.level().addParticle(ParticleTypes.LARGE_SMOKE, true, ent.x() + Math.random() * 4 - Math.random() * 4, ent.y() + 44 + Math.random() * 4 - Math.random() * 4, ent.z() + Math.random() * 4 - Math.random() * 4, Math.random() * 4 - Math.random() * 4, Math.random() * 4 - Math.random() * 4, Math.random() * 4 - Math.random() * 4);
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
