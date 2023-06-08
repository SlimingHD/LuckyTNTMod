package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WitheringTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public WitheringTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(2f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
		explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.isCollisionShapeFullBlock(level, pos)) {
					state.onBlockExploded(level, pos, explosion);
					level.setBlockAndUpdate(pos, Math.random() < 0.5f ? Blocks.SOUL_SAND.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState());
				}
			}
		});
		for(int i = 0; i < strength * 2f; i++) {
			int offX = (int)Math.round(Math.random() * strength * 2f - strength);
			int offZ = (int)Math.round(Math.random() * strength * 2f - strength);
			WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, entity.getLevel());
			if(entity.getLevel() instanceof ServerLevel sl) {
				skeleton.finalizeSpawn(sl, entity.getLevel().getCurrentDifficultyAt(toBlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			for(int y = entity.getLevel().getMaxBuildHeight(); y >= entity.getLevel().getMinBuildHeight(); y--) {
				BlockPos pos = new BlockPos(Mth.floor(entity.x() + offX), y, Mth.floor(entity.z() + offZ));
				BlockState state = entity.getLevel().getBlockState(pos);
				if(!Block.isFaceFull(state.getCollisionShape(entity.getLevel(), pos), Direction.UP) && Block.isFaceFull(entity.getLevel().getBlockState(pos.below()).getCollisionShape(entity.getLevel(), pos.below()), Direction.UP)) {
					skeleton.setPos(pos.getX() + 0.5f, y, pos.getZ() + 0.5f);
					break;
				}
			}
			entity.getLevel().addFreshEntity(skeleton);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WITHERING_TNT.get();
	}
}
