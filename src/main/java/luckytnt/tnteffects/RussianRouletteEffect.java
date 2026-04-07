package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;

public class RussianRouletteEffect extends RouletteTNTEffect {

	public RussianRouletteEffect() {
		super(() -> BlockRegistry.RUSSIAN_ROULETTE, 80);
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
