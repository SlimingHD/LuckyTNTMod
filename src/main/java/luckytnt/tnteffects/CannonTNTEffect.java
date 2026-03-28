package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CannonTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		if (!level.isClientSide() && entity.getTNTFuse() <= 400) {
			List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(entity.getPos().add(-100, -100, -100), entity.getPos().add(100, 100, 100)));
			for (Player player : players) {
				if (!player.equals(entity.owner())) {
					double xVel = player.getX() - entity.x();
					double yVel = player.getY() - 0.6d;
					double zVel = player.getZ() - entity.z();
					Vec3 dir = new Vec3(xVel + random.nextDouble() * 0.4d - 0.2d, yVel - entity.y() - 0.5d + Math.sqrt(xVel * xVel + zVel * zVel) * 0.2d + Math.random() * 0.4d - 0.2d, zVel + random.nextDouble() * 0.4d - 0.2d);
					ItemRegistry.DYNAMITE_X5.get().shoot(level, entity.x(), entity.y() + 0.5d, entity.z(), dir, 3, entity.owner());
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CANNON_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 600;
	}
}
