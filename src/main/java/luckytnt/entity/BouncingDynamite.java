package luckytnt.entity;

import luckytnt.tnteffects.projectile.BouncingDynamiteEffect;
import luckytntlib.entity.LExplosiveProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BouncingDynamite extends LExplosiveProjectile {

	public BouncingDynamite(EntityType<LExplosiveProjectile> type, Level level) {
		super(type, level, new BouncingDynamiteEffect());
	}
	
	@Override
	public void onHitBlock(BlockHitResult hitResult) {
		Vec3 flyDir = getDeltaMovement();
		if (hitResult != null) {
			int bounces = getPersistentData().getInt("bounces");
			if (bounces >= 12) {
				if (level() instanceof ServerLevel) {
					getEffect().serverExplosion(this);
					level().playSound(this, BlockPos.containing(position()), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f);
				}
				discard();
			}
			Vec3 blockNormal = Vec3.atLowerCornerOf(hitResult.getDirection().getNormal());
			double num = blockNormal.dot(flyDir);
			double denom = blockNormal.dot(blockNormal);
			Vec3 result = blockNormal.scale(num / denom);
			Vec3 bounceDir = flyDir.subtract(result.scale(2f));
			setDeltaMovement(bounceDir.scale(0.7d + random.nextDouble() * 0.25d));
			getPersistentData().putInt("bounces", bounces + 1);
			level().playSound(null, x(), y(), z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1, 1);		
		}
	}
}
