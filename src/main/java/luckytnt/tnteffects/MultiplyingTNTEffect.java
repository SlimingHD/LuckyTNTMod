package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MultiplyingTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void baseTick(IExplosiveEntity entity) {
		super.baseTick(entity);
		if(((Entity)entity).onGround() && ((Entity)entity).getPersistentData().getInt("level") > 0) {
			serverExplosion(entity);
			if(((Entity)entity).getPersistentData().getInt("level") == 4) {
				Level level = entity.getLevel();
				entity.getLevel().playSound((Entity)entity, toBlockPos(entity.getPos()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int level = ((Entity)entity).getPersistentData().getInt("level");
		if(level == 4) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
			explosion.doEntityExplosion(1f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
		}
		else if(level == 0) {
			for(int count = 0; count < 4; count++) {
				PrimedLTNT tnt = EntityRegistry.MULTIPLYING_TNT.get().create(entity.getLevel());
				tnt.setPos(entity.getPos());
				tnt.setOwner(entity.owner());
				tnt.setDeltaMovement(Math.random() * 2 - 1, 1 + Math.random(), Math.random() * 2 - 1);
				tnt.getPersistentData().putInt("level", level + 1);
				entity.getLevel().addFreshEntity(tnt);
			}
		}
		else {
			for(int count = 0; count < level * 2; count++) {
				PrimedLTNT tnt = EntityRegistry.MULTIPLYING_TNT.get().create(entity.getLevel());
				tnt.setPos(entity.getPos());
				tnt.setOwner(entity.owner());
				tnt.setDeltaMovement(Math.random() * 2 - 1, 1 + Math.random(), Math.random() * 2 - 1);
				tnt.getPersistentData().putInt("level", level + 1);
				entity.getLevel().addFreshEntity(tnt);
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
