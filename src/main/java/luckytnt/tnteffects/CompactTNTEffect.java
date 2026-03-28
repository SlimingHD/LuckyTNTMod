package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.RegistryObject;

public class CompactTNTEffect extends PrimedTNTEffect {
	
	private final float probability;
	private final int size;
	private final Supplier<RegistryObject<LTNTBlock>> toPlace;

	public CompactTNTEffect(float probability, int size, Supplier<RegistryObject<LTNTBlock>> toPlace) {
		this.probability = probability;
		this.size = size;
		this.toPlace = toPlace;
	}

	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), size);
		explosion.doImprovedBlockExplosion(size, size, false, true, new FilterAirExplosionRule(
				new FilterRandomExplosionRule(probability, new SimpleExplosionRule(toPlace.get().get().defaultBlockState()))
		));
	}
}
