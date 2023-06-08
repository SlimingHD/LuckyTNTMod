package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.network.ClientboundHydrogenBombPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytnt.util.NuclearBombLike;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class TsarBombaBombEffect extends PrimedTNTEffect implements NuclearBombLike {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(entity.getLevel() instanceof ServerLevel sl) {
			PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> (Entity)entity), new ClientboundHydrogenBombPacket(((Entity)entity).getId()));
		}
		
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 160);
		explosion.doEntityExplosion(15f, true);
		explosion.doBlockExplosion(1f, 1f, 0.167f, 0.05f, false, true);
		
		List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.x() - 90, entity.y() - 65, entity.z() - 90, entity.x() + 90, entity.y() + 65, entity.z() + 90));
		for(LivingEntity living : list) {
			living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 3600, 0, true, true, true));
		}
		
		for(int offX = -300; offX <= 300; offX++) {
			for(int offY = -300 / 3; offY <= 300 / 3; offY++) {
				for(int offZ = -300; offZ <= 300; offZ++) {
					double distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
					BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, entity.y() + offY, entity.z() + offZ));
					BlockState state = entity.getLevel().getBlockState(pos);
					if(distance <= 300 && state.getExplosionResistance(entity.getLevel(), pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 200) {
						if(distance <= 150 && entity.getLevel().getBlockState(pos.below()).isFaceSturdy(entity.getLevel(), pos.below(), Direction.UP) && Math.random() < 0.2D && (state.isAir() || state.getBlock().defaultDestroyTime() <= 0.2f)) {
							entity.getLevel().setBlock(pos, BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState(), 3);
						}
						if(state.is(BlockTags.LEAVES)) {
							entity.getLevel().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void displayMushroomCloud(IExplosiveEntity ent) {
		for(int count = 0; count < 1500; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 60 - Math.random() * 60, ent.y() + Math.random() * 3 - Math.random() * 3, ent.z() + Math.random() * 60 - Math.random() * 60, 0, 0, 0);
		}
		for(int count = 0; count < 1000; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 20 - Math.random() * 20, ent.y() + 3 + Math.random() * 3 - Math.random() * 3, ent.z() + Math.random() * 20 - Math.random() * 20, 0, 0, 0);
		}
		for(int count = 0; count < 800; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 10 - Math.random() * 10, ent.y() + Math.random() * 3 - Math.random() * 3, ent.z() + Math.random() * 10 - Math.random() * 10, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 6 - Math.random() * 6, ent.y() + 4 + Math.random() * 3 - Math.random() * 3, ent.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 2 - Math.random() * 2, ent.y() + 15 + Math.random() * 12 - Math.random() * 12, ent.z() + Math.random() * 2 - Math.random() * 2, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 6 - Math.random() * 6, ent.y() + 22 + Math.random() * 3 - Math.random() * 3, ent.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
		}
		for(int count = 0; count < 600; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 6 - Math.random() * 6, ent.y() + 29 + Math.random() * 3 - Math.random() * 3, ent.z() + Math.random() * 6 - Math.random() * 6, 0, 0, 0);
		}
		for(int count = 0; count < 2000; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 2f, 0f), 10f), true, ent.x() + Math.random() * 12 - Math.random() * 12, ent.y() + 24 + Math.random() * 6 - Math.random() * 6, ent.z() + Math.random() * 12 - Math.random() * 12, 0, 0, 0);
		}
		for(int count = 0; count < 2000; count++) {
			ent.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, true, ent.x() + Math.random() * 2 - Math.random() * 2, ent.y() + 22 + Math.random() * 2 - Math.random() * 2, ent.z() + Math.random() * 2 - Math.random() * 2, Math.random() * 2 - Math.random() * 2, Math.random() * 2 - Math.random() * 2, Math.random() * 2 - Math.random() * 2);
		}
	}
}
