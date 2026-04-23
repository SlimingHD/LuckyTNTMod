package luckytnt.rules;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class DrainAreaExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "drain_area");
	
	public DrainAreaExplosionRule() {
	}
	
	@Override
	@Nullable
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		if (state.getFluidState().getType() != Fluids.EMPTY && !state.hasProperty(BlockStateProperties.WATERLOGGED)) {
			return Blocks.AIR.defaultBlockState();
		}
		if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
			return state.setValue(BlockStateProperties.WATERLOGGED, false);
		}
		return null;
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		return new DrainAreaExplosionRule();
	}
}
