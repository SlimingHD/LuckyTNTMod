package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class RouletteTNTEffect extends PrimedTNTEffect {

	private final int strength;
	private final Supplier<RegistryObject<LTNTBlock>> block;
	
	public RouletteTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int strength) {
		this.block = block;
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (level.getRandom().nextDouble() < 0.2d) {
			ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), strength);
			explosion.doEntityExplosion(5f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
			explosion.spawnExplosionParticles();
		}
	}
	
	@Override
	public Block getBlock() {
		return block.get().get();
	}
}
