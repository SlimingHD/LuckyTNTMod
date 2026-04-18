package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.LuckyTNTDamageSources;
import luckytnt.registry.SoundRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class SayGoodbyeEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() == 30) {
			entity.getLevel().playSound(null, BlockPos.containing(entity.getPos()), SoundRegistry.SAY_GOODBYE.get(), SoundSource.HOSTILE, 20, 1);
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();		
		Player player = level.getNearestPlayer(entity.x(), entity.y(), entity.z(), 60, false);
		Vec3 position = entity.getPos();
		if (player != null) {
			position = player.getPosition(1f);
		}
		
		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, LuckyTNTDamageSources.sayGoodbye(level), position.x, position.y, position.z, 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		explosion.spawnExplosionParticles();
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SAY_GOODBYE.get();
	}
}
