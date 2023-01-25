package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class DripstoneTNTEffect extends PrimedTNTEffect{

	@Override
	public Block getBlock() {
		return BlockRegistry.DRIPSTONE_TNT.get();
	}
}
