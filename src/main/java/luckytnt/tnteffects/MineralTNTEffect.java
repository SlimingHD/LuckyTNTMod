package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MineralTNTEffect extends PrimedTNTEffect {

	private static final RandomList<Block> BLOCKS = new RandomList<>(
		List.of(Blocks.COAL_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.COPPER_BLOCK, Blocks.REDSTONE_BLOCK, Blocks.EMERALD_BLOCK, Blocks.LAPIS_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.NETHERITE_BLOCK), 
		List.of(0.9f / 7f, 0.9f / 7f, 0.9f / 7f, 0.9f / 7f, 0.9f / 7f, 0.9f / 7f, 0.9f / 7f, 0.06f, 0.04f)
	);
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacyCylindricalExplosion(entity.getLevel(), entity.getPos(), 30, 30, 200f, new FilterAirExplosionRule(
			new StackedExplosionRule(
				FilterBlockExplosionRule.builder().filterForTags(List.of(BlockTags.LEAVES, BlockTags.LOGS)).build(new AlwaysExplosionRule()),
				LogicExplosionRule.not(
					new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
					new AlwaysExplosionRule()
				)
			)
		));
		
		ExplosionHelper.legacySpheroidExplosion(entity.getLevel(), entity.getPos(), 33, new Vector3f(1f, 1f / 5f, 1f), 200f, FilterRandomDistanceExplosionRule.quadraticDecrease(28, 33, new CraterExplosionRule()));
		
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(entity.getLevel());
		ExplosionHelper.customSpheroidExplosion(entity.getLevel(), entity.getPos(), 33, new Vector3f(1f, 1f / 5f, 1f), (level, center, pos, state) -> {
			if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f && !state.is(BlockTags.LOGS) && !state.is(BlockTags.LEAVES) && state.isCollisionShapeFullBlock(level, pos) && touchesAir(level, pos)) {
				level.setBlockAndUpdate(pos, BLOCKS.getRandomItem(level.getRandom()).defaultBlockState());
				state.getBlock().wasExploded(level, pos, dummy);
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.MINERAL_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 150;
	}

	private static boolean touchesAir(Level level, BlockPos pos) {
		for (Direction dir : Direction.values()) {
			BlockPos offset = pos.relative(dir);
			if (level.getBlockState(offset).isAir()) {
				return true;
			}
		}
		return false;
	}
}
