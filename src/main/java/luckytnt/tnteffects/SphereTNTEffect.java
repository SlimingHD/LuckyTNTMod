package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class SphereTNTEffect extends PrimedTNTEffect {

	private Supplier<RegistryObject<LTNTBlock>> block;
	private final int strength;
	private final float maxResistance;

	public SphereTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int strength, float maxResistance) {
		this.block = block;
		this.strength = strength;
		this.maxResistance = maxResistance;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), strength, maxResistance);
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), strength);
		particleExplosion.spawnExplosionParticles();
	}

	@Override
	public Block getBlock() {
		return block.get().get();
	}
}
