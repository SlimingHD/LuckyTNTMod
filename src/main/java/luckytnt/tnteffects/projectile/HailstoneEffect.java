package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class HailstoneEffect extends PrimedTNTEffect {
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		
	}
	
	@Override
	public Block getBlock() {
		return Blocks.ICE;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 20000;
	}
	
	@Override
	public float getSize(IExplosiveEntity ent) {
		return 0.1f;
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}
}
