package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HelixEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity instanceof Entity ent) {
			Level level = entity.getLevel();
			
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.2f, ent.getDeltaMovement().z);
			if (entity.getTNTFuse() == 140) {
				ent.getPersistentData().putFloat("power", 0.35f);
			}
			if (entity.getTNTFuse() < 60 && entity.getTNTFuse() % 6 == 0) {
				float power = ent.getPersistentData().getFloat("power");
				
				PrimedLTNT revolution = EntityRegistry.THE_REVOLUTION.get().create(level);
				revolution.setPos(entity.getPos());
				revolution.setOwner(entity.owner());
				revolution.setTNTFuse(140);
				revolution.setDeltaMovement(ent.getLookAngle().normalize().scale(power));
				level.addFreshEntity(revolution);
				
				level.playSound(null, toBlockPos(entity.getPos()), SoundEvents.DISPENSER_LAUNCH, SoundSource.MASTER, 3, 1);
				ent.getPersistentData().putFloat("power", power + 0.35f);
				ent.setYRot(ent.getYRot() + 60f);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HELIX.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
