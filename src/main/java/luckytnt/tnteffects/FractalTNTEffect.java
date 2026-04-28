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

public class FractalTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		if (!entity.getLevel().isClientSide()) {
			Level level = entity.getLevel();
			
			if (entity.getTNTFuse() == 260) {
				ent.getPersistentData().putFloat("power", 0.25f);
			}
			if (entity.getTNTFuse() < 60 && entity.getTNTFuse() % 6 == 0) {
				float power = ent.getPersistentData().getFloat("power");
				
				PrimedLTNT helix = EntityRegistry.HELIX.get().create(level);
				helix.setPos(entity.getPos());
				helix.setOwner(entity.owner());
				helix.setTNTFuse(200);
				helix.setDeltaMovement(ent.getLookAngle().normalize().scale(power));
				level.addFreshEntity(helix);
				
				level.playSound(null, toBlockPos(entity.getPos()), SoundEvents.DISPENSER_LAUNCH, SoundSource.MASTER, 3, 1);
				ent.getPersistentData().putFloat("power", power + 0.25f);
				ent.setYRot(ent.getYRot() + 30f);
			}
		}
		ent.setDeltaMovement(ent.getDeltaMovement().x, 0.2f, ent.getDeltaMovement().z);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FRACTAL_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 260;
	}
}
