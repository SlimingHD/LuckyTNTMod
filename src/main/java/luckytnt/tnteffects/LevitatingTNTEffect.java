package luckytnt.tnteffects;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;

public class LevitatingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.15f, ((Entity)ent).getDeltaMovement().z);
	}
}
