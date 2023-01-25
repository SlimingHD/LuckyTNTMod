package luckytnt.tnteffects;

import java.util.Random;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.network.ClientboundExplosionPacket;
import luckytntlib.network.LuckyTNTLibPacketHandler;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.PacketDistributor;

public class DividingTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		if(entity.getTNTFuse() == 60 && entity.getPersistentData().getInt("level") != 0) {
			double x = entity.getPersistentData().getDouble("x") - entity.x();
			double z = entity.getPersistentData().getDouble("z") - entity.z();
			double magnitude = Math.sqrt(x * x + z * z) + 0.01f;
			((Entity)entity).setDeltaMovement(x / magnitude, 1, z / magnitude);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(entity.getPersistentData().getInt("level") == 0) {		
			for(int offX = -50; offX < 50; offX += 10) {
				for(int offZ = -50; offZ < 50; offZ += 10) {
					findBlock: for(int offY = 320; offY > -64; offY--) {
						BlockPos pos = new BlockPos(entity.x() + offX, entity.y() + offY, entity.z() + offZ);
						if(entity.level().getBlockState(pos).isCollisionShapeFullBlock(entity.level(), pos) && !entity.level().getBlockState(pos.above()).isCollisionShapeFullBlock(entity.level(), pos.above())) {
							PrimedLTNT projectile = EntityRegistry.DIVIDING_TNT.get().create(entity.level());
							projectile.setPos(entity.getPos().add(offX, offY, offZ));
							projectile.setOwner(entity.owner() instanceof LivingEntity ? (LivingEntity)entity.owner() : null);
							projectile.getPersistentData().putInt("maxLevel", new Random().nextInt(5));
							projectile.getPersistentData().putInt("level", entity.getPersistentData().getInt("level") + 1);
							projectile.getPersistentData().putDouble("x", entity.x());
							projectile.getPersistentData().putDouble("z", entity.z());
							entity.level().addFreshEntity(projectile);
							break findBlock;
						}
					}
				}
			}
			entity.destroy();
		}
		else {
			if(entity.getPersistentData().getInt("level") >= entity.getPersistentData().getInt("maxLevel")) {
				ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10);
				explosion.doEntityExplosion(1f, true);
				explosion.doBlockExplosion();
				if(entity.getPersistentData().getInt("level") >= entity.getPersistentData().getInt("maxLevel")) {
					LuckyTNTLibPacketHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), new ClientboundExplosionPacket("luckytntlib.client.ClientExplosions", "playExplosionSoundAt", new BlockPos(entity.getPos())));
				}
				entity.destroy();
			}
			else {
				ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10);
				explosion.doEntityExplosion(1.5f, true);
				explosion.doBlockExplosion();
				PrimedLTNT projectile = EntityRegistry.DIVIDING_TNT.get().create(entity.level());
				projectile.setOwner(entity.owner() instanceof LivingEntity ? (LivingEntity)entity.owner() : null);
				projectile.setPos(entity.getPos());
				projectile.setDeltaMovement(Math.random() - Math.random(), 1 + Math.random() * 0.75f, Math.random() - Math.random());
				projectile.getPersistentData().putInt("maxLevel", entity.getPersistentData().getInt("maxLevel"));
				projectile.getPersistentData().putInt("level", entity.getPersistentData().getInt("level") + 1);
				projectile.getPersistentData().putDouble("x", entity.getPersistentData().getDouble("x"));
				projectile.getPersistentData().putDouble("z", entity.getPersistentData().getDouble("z"));
				projectile.setDeltaMovement(0, 0, 0);
				entity.level().addFreshEntity(projectile);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DIVIDING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 80;
	}
}
