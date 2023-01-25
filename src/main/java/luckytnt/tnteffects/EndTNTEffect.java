package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class EndTNTEffect extends PrimedTNTEffect {

	@Override
	public Block getBlock() {
		return BlockRegistry.END_TNT.get();
	}
}
