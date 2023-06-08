package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FlakTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if(!level.isClientSide && entity.getTNTFuse() <= 600) {
			List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(entity.getPos().add(-150, -150, -150), entity.getPos().add(150, 150, 150)));
			for(Player player : players) {
				if(!player.equals(entity.owner())) {
					double xVel = player.getX() - entity.x();
					double yVel = player.getY() - 0.6f;
					double zVel = player.getZ() - entity.z();
					Vec3 dir = new Vec3(xVel + (Math.random() * 0.4f - 0.2f), yVel - entity.y() - 0.5f + Math.sqrt(xVel * xVel + zVel * zVel) * 0.2f + (Math.random() * 0.4f - 0.2f), zVel + (Math.random() * 0.4f - 0.2f));
					ItemRegistry.DYNAMITE_X20.get().shoot(level, entity.x(), entity.y() + 0.5f, entity.z(), dir, 3, entity.owner());
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FLAK_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 800;
	}
}
