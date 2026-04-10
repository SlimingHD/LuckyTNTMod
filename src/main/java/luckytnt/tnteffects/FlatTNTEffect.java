package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class FlatTNTEffect extends PrimedTNTEffect {
	
	private final int radius;
	private final int radiusY;
	private int fuse;
	private Supplier<RegistryObject<LTNTBlock>> block;
	
	public FlatTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int radius, int radiusY, int fuse) {
		this.radius = radius;
		this.radiusY = radiusY;
		this.block = block;
		this.fuse = fuse;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (radius > 30) { 
			ExplosionHelper.createCylindricalCrater(entity.getLevel(), entity.getPos(), radius, radiusY, 200f, new FilterOffYExplosionRule(0, radiusY, new CraterExplosionRule()));
		} else {
			ExplosionHelper.legacyCylindricalExplosion(entity.getLevel(), entity.getPos(), radius, radiusY, 200f, new FilterOffYExplosionRule(0, radiusY, new CraterExplosionRule()));
		}
	}
	
	@Override
	public Block getBlock() {
		return block.get().get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return fuse;
	}
}
