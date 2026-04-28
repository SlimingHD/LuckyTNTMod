package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.BiomeSetter;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SuperflatTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createCylindricalCrater(entity.getLevel(), entity.getPos(), 400, 100, 1200f, new FilterOffYExplosionRule(-3, 100,
			new StackedExplosionRule(
				new FilterOffYExplosionRule(0, 100, new CraterExplosionRule()),
				new FilterOffYExplosionRule(-1, -1, new BlockExplosionRule(Blocks.GRASS_BLOCK.defaultBlockState())),
				new FilterOffYExplosionRule(-3, -2, new BlockExplosionRule(Blocks.DIRT.defaultBlockState()))
			)
		));
		BiomeSetter.setBiomeInCylinder(entity.getLevel(), entity.getPos(), 410, 50, Biomes.PLAINS);
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 400);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		int step = entity.getPersistentData().getInt("particleStep");
		entity.getPersistentData().putInt("particleStep", (step + 1) % 20);
		double radius = Math.sqrt(0.5d) + step / 6d;
		for (double angle = 0d; angle < 360d; angle += 6d / radius) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.33f, 0.13f, 0f), 1f), entity.x() + Math.sin(angle * Mth.DEG_TO_RAD) * radius, entity.y() + 0.45d, entity.z() + Math.cos(angle * Mth.DEG_TO_RAD) * radius, 0d, 0d, 0d);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0.41f, 0.04f), 1f), entity.x() + Math.sin(angle * Mth.DEG_TO_RAD) * radius, entity.y() + 0.55d, entity.z() + Math.cos(angle * Mth.DEG_TO_RAD) * radius, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 300;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SUPERFLAT_TNT.get();
	}
}
