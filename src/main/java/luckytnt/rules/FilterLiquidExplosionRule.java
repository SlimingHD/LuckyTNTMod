package luckytnt.rules;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FilterLiquidExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "filter_liquid");
	
	private final ExplosionRule rule;
	
	public FilterLiquidExplosionRule(ExplosionRule rule) {
		this.rule = rule;
	}
	
	@Override
	@Nullable
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		Block block = state.getBlock();
		if (DrainAreaExplosionRule.BLOCKS_TO_DRY.contains(block) || block instanceof LiquidBlock) {
			return rule.getState(level, state, center, offX, offY, offZ, random);
		}
		return null;
	}

	@Override
	public JsonObject encode(JsonObject root) {
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		return new FilterLiquidExplosionRule(null);
	}
}
