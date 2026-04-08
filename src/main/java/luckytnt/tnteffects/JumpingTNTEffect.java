package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class JumpingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity instanceof Entity ent && ent.onGround()) {
			Level level = ent.level();
			RandomSource random = level.getRandom();
			int bounces = ent.getPersistentData().getInt("bounces") + 1;
			ent.getPersistentData().putInt("bounces", bounces);
			
			ent.setDeltaMovement(random.nextDouble() * 4d - 2d, 1d + random.nextDouble() * 2d, random.nextDouble() * 4d - 2d);
			level.playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1f, 1f);
			playExplosionSound(entity);
			
			if (bounces >= 1) {
				serverExplosion(entity);
				if (bounces >= 10) {
					entity.destroy();
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		PrimedLTNT tnt = EntityRegistry.LEAPING_TNT.get().create(entity.getLevel());
		tnt.setPos(entity.getPos());
		tnt.setOwner(entity.owner());
		tnt.setTNTFuse(1000000);
		entity.getLevel().addFreshEntity(tnt);
		EntityRegistry.TNT_X20_EFFECT.build().serverExplosion(entity);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.JUMPING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 100000;
	}
}
