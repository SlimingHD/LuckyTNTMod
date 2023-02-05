package luckytnt.entity;

import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class HailstoneProjectile extends LExplosiveProjectile {

	public HailstoneProjectile(EntityType<LExplosiveProjectile> type, Level level, PrimedTNTEffect effect) {
		super(type, level, effect);
	}

	@Override
	public void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		level.playSound(null, new BlockPos(x(), y(), z()), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.5f, 1f);
		for(int count = 0; count < 10; count++)
			level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW.defaultBlockState()), x(), y(), z(), 0, 0, 0);
		destroy();
	}
	
	@Override
	public void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		if(result.getEntity() instanceof LivingEntity lent) {
			lent.hurt(new DamageSource("hailstone"), 4f);
		}
	} 
}
