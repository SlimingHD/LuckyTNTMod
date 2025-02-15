package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class SphereTNTEffect extends PrimedTNTEffect{

	private Supplier<RegistryObject<LTNTBlock>> block;
	private final int strength;
	
	public SphereTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int strength) {
		this.block = block;
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), strength, 100);
	}

	@Override
	public Block getBlock() {
		return block.get().get();
	}
}
