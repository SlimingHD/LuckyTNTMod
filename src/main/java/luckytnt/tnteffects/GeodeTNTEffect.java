package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class GeodeTNTEffect extends PrimedTNTEffect{

	@Override
	public Block getBlock() {
		return BlockRegistry.GEODE_TNT.get();
	}
}
