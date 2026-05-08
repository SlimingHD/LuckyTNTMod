package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class RouletteTNTEffect extends PrimedTNTEffect {

	private final int strength, fuse;
	private final Supplier<RegistryObject<LTNTBlock>> block;
	
	public RouletteTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int strength) {
		this(block, strength, 80);
	}
	
	public RouletteTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int strength, int fuse) {
		this.block = block;
		this.strength = strength;
		this.fuse = fuse;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (level.getRandom().nextDouble() < 0.2d) {
			ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), strength);
			explosion.doEntityExplosion(5f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
			explosion.spawnExplosionParticles();
			
			for (Player player : explosion.getHitPlayers().keySet()) {
				if (!player.isDeadOrDying()) {
					AdvancementHelper.grantAdvancementOnePlayer(player, AdvancementKeys.GAMBLING);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return block.get().get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return fuse;
	}
}
