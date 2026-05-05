package luckytnt.tnteffects;

import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.resources.ResourceLocation;

public class AdvancementEffect extends PrimedTNTEffect {

	private final ResourceLocation key;
	
	public AdvancementEffect(ResourceLocation key) {
		this.key = key;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		AdvancementHelper.grantAdvancement(entity, key);
	}
}
