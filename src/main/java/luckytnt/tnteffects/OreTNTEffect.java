package luckytnt.tnteffects;

import java.util.LinkedList;
import java.util.List;

import luckytnt.entity.OreTNTMinecart;
import luckytnt.entity.PrimedOreTNT;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class OreTNTEffect extends PrimedTNTEffect {

	private static Block[] ORES;
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide()) {
			tryPlaceOres(entity, 5, 100);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		tryPlaceOres(entity, 750, 1000);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ORE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 150;
	}
	
	@SuppressWarnings("deprecation")
	private static void tryPlaceOres(IExplosiveEntity entity, int maxOres, int maxAttempts) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		if (ORES == null) {
			ORES = level.registryAccess().registryOrThrow(Registries.BLOCK).getOrCreateTag(Tags.Blocks.ORES).stream().map(Holder<Block>::value).toArray(Block[]::new);
		}
		
		List<BlockPos> positions = null;
		if (entity instanceof PrimedOreTNT tnt) {
			positions = tnt.availablePos;
		} else if (entity instanceof OreTNTMinecart minecart) {
			positions = minecart.availablePos;
		} else {
			return;
		}
		
		if (positions.size() == 0) {
			calculateAvailablePositions(entity, positions);
		}
		
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		int placedOres = 0;
		int attempts = 0;
		while (placedOres < maxOres && attempts < maxAttempts) {
			if (positions.size() == 0) {
				return;
			}
			BlockPos pos = positions.remove(random.nextInt(positions.size()));
			BlockState state = level.getBlockState(pos);
			if (!state.isAir() && !state.is(Tags.Blocks.ORES) && Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && state.isCollisionShapeFullBlock(level, pos)) {
				level.setBlockAndUpdate(pos, getRandomOre(random));
				state.getBlock().wasExploded(level, pos, dummy);
				level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
				placedOres++;
			}
			attempts++;
		}
	}
	
	private static void calculateAvailablePositions(IExplosiveEntity entity, List<BlockPos> list) {
		LinkedList<BlockPos> availablePositions = new LinkedList<>();
		ExplosionHelper.customSphericalExplosion(entity.getLevel(), entity.getPos(), 12, (lev, center, pos, state) -> {
			if (!state.isAir() && !state.is(Tags.Blocks.ORES) && state.isCollisionShapeFullBlock(lev, pos)) {
				availablePositions.add(pos);
			}
		});
		list.addAll(availablePositions);
	}
	
	private static BlockState getRandomOre(RandomSource random) {
		return ORES == null ? Blocks.COAL_ORE.defaultBlockState() : ORES[random.nextInt(ORES.length)].defaultBlockState();
	}
}
