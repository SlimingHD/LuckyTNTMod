package luckytnt.tnteffects;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import luckytnt.entity.PrimedResetTNT;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ResetTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() == 2400 && entity instanceof PrimedResetTNT ent) {
			saveBlocks(ent);
			saveEntities(ent);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity instanceof PrimedResetTNT ent) {
			Level level = ent.level();
			for (Pair<BlockPos, BlockState> pair : ent.blocks) {
				BlockPos pos = pair.getFirst();
				BlockState state = pair.getSecond();
				
				if (level.getBlockState(pos) != state) {
					level.setBlock(pos, state, 3);
				}
			}

			for (Pair<Vec3, Entity> pair : ent.entities) {
				Vec3 pos = pair.getFirst();
				Entity e = pair.getSecond();
				
				if (e instanceof ServerPlayer player) {
					player.teleportTo(pos.x, pos.y, pos.z);
				} else {
					if (!e.isAlive()) {
						e.revive();
					}
					e.setPos(pos);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.RESET_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 2400;
	}
	
	private static void saveBlocks(PrimedResetTNT entity) {
		ExplosionHelper.customSphericalExplosion(entity.level(), entity.getPos(), 100, (level, center, pos, state) -> {
			if (state.is(BlockRegistry.RESET_TNT.get())) {
				entity.blocks.add(Pair.of(pos, Blocks.AIR.defaultBlockState()));
				return;
			}
			entity.blocks.add(Pair.of(pos, state));
		});
	}
	
	private static void saveEntities(PrimedResetTNT entity) {
		List<Entity> list = entity.level().getEntities(entity, new AABB(entity.getPos().add(-100d, -100d, -100d), entity.getPos().add(100d, 100d, 100d)));
		for (Entity ent : list) {
			entity.entities.add(Pair.of(ent.getPosition(1f), ent));
		}
	}
}
