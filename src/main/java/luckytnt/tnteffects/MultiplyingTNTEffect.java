package luckytnt.tnteffects;

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
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.PacketDistributor;

public class MultiplyingTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		if(((Entity)entity).isOnGround() && ((Entity)entity).getPersistentData().getInt("level") > 0) {
			serverExplosion(entity);
			if(((Entity)entity).getPersistentData().getInt("level") == 4) {
				LuckyTNTLibPacketHandler.CHANNEL.send(PacketDistributor.ALL.noArg(), new ClientboundExplosionPacket("luckytntlib.client.ClientExplosions", "playExplosionSoundAt", new BlockPos(entity.getPos())));
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int level = ((Entity)entity).getPersistentData().getInt("level");
		if(level == 4) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), entity.getPos(), 10f);
			explosion.doEntityExplosion(1f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		}
		else if(level == 0) {
			for(int count = 0; count < 4; count++) {
				PrimedLTNT tnt = EntityRegistry.MULTIPLYING_TNT.get().create(entity.level());
				tnt.setPos(entity.getPos());
				tnt.setDeltaMovement(Math.random() * 2 - 1, 1 + Math.random(), Math.random() * 2 - 1);
				tnt.getPersistentData().putInt("level", level + 1);
				entity.level().addFreshEntity(tnt);
			}
		}
		else {
			for(int count = 0; count < level * 2; count++) {
				PrimedLTNT tnt = EntityRegistry.MULTIPLYING_TNT.get().create(entity.level());
				tnt.setPos(entity.getPos());
				tnt.setDeltaMovement(Math.random() * 2 - 1, 1 + Math.random(), Math.random() * 2 - 1);
				tnt.getPersistentData().putInt("level", level + 1);
				entity.level().addFreshEntity(tnt);
			}
		}
		entity.destroy();
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.MULTIPLYING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return ((Entity)entity).getPersistentData().getInt("level") == 4 ? 100000 : 120;
	}
}
