package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;

public class TheRevolutionEffect extends PrimedTNTEffect {
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity instanceof PrimedLTNT ent) {
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.15f, ent.getDeltaMovement().z);
			if (entity.getTNTFuse() < 60) {
				if (entity.getTNTFuse() % 6 == 0) {
					ent.getPersistentData().putFloat("spiral_power", ent.getPersistentData().getFloat("spiral_power") + 0.15f);
					PrimedLTNT spiralTnt = EntityRegistry.SPIRAL_TNT.get().create(entity.getLevel());
					spiralTnt.setTNTFuse(140);
					spiralTnt.setPos(entity.x(), entity.y(), entity.z());
					spiralTnt.setOwner(entity.owner());
					spiralTnt.setDeltaMovement(ent.getLookAngle().normalize().scale(ent.getPersistentData().getFloat("spiral_power")));
					entity.getLevel().playSound(null, toBlockPos(entity.getPos()), SoundEvents.DISPENSER_LAUNCH, SoundSource.MASTER, 3, 1);
					entity.getLevel().addFreshEntity(spiralTnt);
					ent.setYRot(ent.getYRot() + 60f);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.THE_REVOLUTION.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
