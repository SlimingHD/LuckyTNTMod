package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EndTNTEffect extends PrimedTNTEffect {

	private final int strength;
	
	public EndTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		explosion.spawnExplosionParticles();
		ImprovedExplosion endExplosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), Mth.floor(strength * 1.5f));
		endExplosion.doImprovedBlockExplosion(1f, 1.5f, false, false, new BlockExplosionRule(Blocks.END_STONE.defaultBlockState()));
		RandomSource random = entity.getLevel().getRandom();
		ImprovedExplosion decorationExplosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, null, entity.x(), entity.y(), entity.z(), Mth.floor(strength * 1.5f), false, (level, center, pos, state) -> {
			if (state.isAir() && level.getBlockState(pos.below()).is(Blocks.END_STONE) && random.nextFloat() < 0.1f) {
				level.setBlockAndUpdate(pos, Blocks.CHORUS_FLOWER.defaultBlockState());
			} else if (state.isAir() && level.getBlockState(pos.above()).isAir() && level.getBlockState(pos.above(2)).isAir() && level.getBlockState(pos.below()).is(Blocks.END_STONE) && random.nextFloat() < 0.025f) {
				EnderMan enderMan = EntityType.ENDERMAN.create(level);
				enderMan.setPos(Vec3.atBottomCenterOf(pos));
				level.addFreshEntity(enderMan);
			}
		});
		decorationExplosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.END_ROD, entity.x() + 0.5D, entity.y() + 1D, entity.z() + 0.5D, 0, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.END_ROD, entity.x() + 0.5D, entity.y() + 1D, entity.z() - 0.5D, 0, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.END_ROD, entity.x() - 0.5D, entity.y() + 1D, entity.z() + 0.5D, 0, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.END_ROD, entity.x() - 0.5D, entity.y() + 1D, entity.z() - 0.5D, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.END_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
