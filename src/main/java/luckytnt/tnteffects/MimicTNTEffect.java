package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class MimicTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide()) {
			Player player = entity.getLevel().getNearestPlayer(entity.x(), entity.y(), entity.z(), 5d, p -> p != entity.owner());
			if (player != null) {
				playExplosionSound(entity);
				serverExplosion(entity);
				entity.destroy();
			}
		}
		if (entity instanceof Entity ent) {
			ent.setDeltaMovement(0d, 0d, 0d);
			ent.setPos(ent.getPosition(0f));
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		explosion.spawnExplosionParticles();
		for (Player p : explosion.getHitPlayers().keySet()) {
			if (p == entity.owner() && p.isDeadOrDying()) {
				AdvancementHelper.grantAdvancementOnePlayer(p, AdvancementKeys.WHAT_GOES_AROUND);
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}

	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		BlockState state = entity.getLevel().getBlockState(toBlockPos(entity.getPos()).below());
		return state.isAir() ? BlockRegistry.MIMIC_TNT.get().defaultBlockState() : state;
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 20000;
	}
}
