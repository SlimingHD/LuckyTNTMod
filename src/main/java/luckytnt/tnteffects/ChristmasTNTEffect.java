package luckytnt.tnteffects;

import luckytnt.block.ChristmasTNTBlock;
import luckytnt.network.ClientboundDoubleNBTPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class ChristmasTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ServerLevel serverLevel = (ServerLevel)entity.getLevel();
		RandomSource random = serverLevel.getRandom();
		serverLevel.sendParticles(ParticleTypes.WAX_OFF, entity.x() + random.nextDouble() - 0.5d, entity.y() + 1d + random.nextDouble() * 0.5d, entity.z() + random.nextDouble() - 0.5d, entity instanceof PrimedLTNT ? 500 : 100, 0.5d, 0.5d, 0.5d, 0d);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity instanceof Entity ent) {
			Level level = ent.level();
			RandomSource random = level.getRandom();
			if (!level.isClientSide() && entity.getTNTFuse() == 240) {
				ent.setNoGravity(true);
				Vec3 flying = new Vec3(random.nextDouble() * 2d - 1d, 0, random.nextDouble() * 2d - 1d).normalize().scale(40d);
				entity.getPersistentData().putDouble("flyingX", flying.x);
				entity.getPersistentData().putDouble("flyingY", flying.y);
				entity.getPersistentData().putDouble("flyingZ", flying.z);
				PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundDoubleNBTPacket("flyingX", flying.x, ent.getId()));
				PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundDoubleNBTPacket("flyingY", flying.y, ent.getId()));
				PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundDoubleNBTPacket("flyingZ", flying.z, ent.getId()));
				Vec3 flyingPos = new Vec3(entity.x() + flying.reverse().normalize().scale(20).x, entity.y() + 30, entity.z() + flying.reverse().normalize().scale(20d).z);
				ent.setPos(flyingPos.x, flyingPos.y, flyingPos.z);
			}
			if (entity.getTNTFuse() <= 220) {
				ent.setDeltaMovement(new Vec3(entity.getPersistentData().getDouble("flyingX"), entity.getPersistentData().getDouble("flyingY"), entity.getPersistentData().getDouble("flyingZ")).normalize().scale(40d / 220d));
				if (!level.isClientSide() && entity.getTNTFuse() % 10 == 0) {
					LExplosiveProjectile present = EntityRegistry.PRESENT.get().create(level);
					present.setPos(entity.getPos());
					present.setOwner(entity.owner());
					double randomX = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
					double randomZ = random.nextDouble() * (random.nextBoolean() ? 1 : -1);
					present.setDeltaMovement(randomX, -random.nextDouble() * 0.5d, randomZ);
					level.addFreshEntity(present);
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if (entity instanceof PrimedLTNT) {
			RandomSource random = entity.getLevel().getRandom();
			if (entity.getTNTFuse() < 230) {
				for (int i = 0; i <= 10; i++) {
					entity.getLevel().addParticle(ParticleTypes.WAX_OFF, true, entity.x() + random.nextDouble() - 0.5d, entity.y() + 1d + random.nextDouble() * 0.5d, entity.z() + random.nextDouble() - 0.5d, 0, 0, 0);
				}
			} else {
				super.spawnParticles(entity);
			}
		}
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		return entity instanceof PrimedLTNT ? BlockRegistry.CHRISTMAS_TNT.get().defaultBlockState() : BlockRegistry.CHRISTMAS_TNT.get().defaultBlockState().setValue(ChristmasTNTBlock.ONLY_PRESENT, true);
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return entity instanceof PrimedLTNT ? 300 : 10000;
	}
}
