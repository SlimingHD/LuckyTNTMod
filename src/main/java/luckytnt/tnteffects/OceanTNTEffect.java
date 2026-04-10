package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

public class OceanTNTEffect extends PrimedTNTEffect {

	private final int radius;
	private final int radiusY;
	private final int squidCount;
	private Supplier<RegistryObject<LTNTBlock>> block;

	public OceanTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int radius, int radiusY, int squidCount) {
		this.radius = radius;
		this.radiusY = radiusY;
		this.squidCount = squidCount;
		this.block = block;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();

		ExplosionHelper.legacyCylindricalExplosion(level, entity.getPos(), radius, radiusY, 99.9f, new FilterOffYExplosionRule(-radiusY, 0,
			new StackedExplosionRule(
				new FilterBlastResistanceExplosionRule(3.9f, new SimpleExplosionRule(Blocks.WATER.defaultBlockState())),
				LogicExplosionRule.not(
					new FilterFullBlockExplosionRule(new AlwaysExplosionRule()),
					new SimpleExplosionRule(Blocks.WATER.defaultBlockState())
				)
			)
		));

		for (int i = 0; i < squidCount; i++) {
			Squid squid = new Squid(EntityType.SQUID, level);
			squid.setPos(entity.x() + random.nextDouble() * radius * 2d - radius, entity.y(), entity.z() + random.nextDouble() * radius * 2d - radius);
			level.addFreshEntity(squid);
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SPLASH, entity.x(), entity.y() + 0.7d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return block.get().get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
