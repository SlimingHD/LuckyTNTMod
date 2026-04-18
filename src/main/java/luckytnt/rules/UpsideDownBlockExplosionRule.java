package luckytnt.rules;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

public class UpsideDownBlockExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "upside_down_block");
	
	public UpsideDownBlockExplosionRule() {
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		if (state.hasProperty(BlockStateProperties.VERTICAL_DIRECTION)) {
			state = state.setValue(BlockStateProperties.VERTICAL_DIRECTION, state.getValue(BlockStateProperties.VERTICAL_DIRECTION).getOpposite());
		}
		
		if (state.hasProperty(BlockStateProperties.SLAB_TYPE)) {
			SlabType slab = state.getValue(BlockStateProperties.SLAB_TYPE);
			if (slab == SlabType.BOTTOM) {
				state = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
			} else if (slab == SlabType.TOP) {
				state = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
			}
		}
		
		if (state.hasProperty(BlockStateProperties.HALF)) {
			if (state.getValue(BlockStateProperties.HALF) == Half.BOTTOM) {
				state = state.setValue(BlockStateProperties.HALF, Half.TOP);
			} else {
				state = state.setValue(BlockStateProperties.HALF, Half.BOTTOM);
			}
		}
		
		if (state.hasProperty(BlockStateProperties.FACING) && state.getValue(BlockStateProperties.FACING).getAxis() == Axis.Y) {
			state = state.setValue(BlockStateProperties.FACING, state.getValue(BlockStateProperties.FACING).getOpposite());
		}
		
		if (state.hasProperty(BlockStateProperties.UP) && state.hasProperty(BlockStateProperties.DOWN)) {
			boolean upVal = state.getValue(BlockStateProperties.UP);
			boolean downVal = state.getValue(BlockStateProperties.DOWN);
			state = state.setValue(BlockStateProperties.UP, downVal).setValue(BlockStateProperties.DOWN, upVal);
		}
		
		if (state.hasProperty(BlockStateProperties.ATTACH_FACE)) {
			AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
			if (face == AttachFace.FLOOR) {
				state = state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.CEILING);
			} else if (face == AttachFace.CEILING) {
				state = state.setValue(BlockStateProperties.ATTACH_FACE, AttachFace.FLOOR);
			}
		}
		
		if (state.hasProperty(BlockStateProperties.BELL_ATTACHMENT)) {
			BellAttachType type = state.getValue(BlockStateProperties.BELL_ATTACHMENT);
			if (type == BellAttachType.FLOOR) {
				state = state.setValue(BlockStateProperties.BELL_ATTACHMENT, BellAttachType.CEILING);
			} else if (type == BellAttachType.CEILING) {
				state = state.setValue(BlockStateProperties.BELL_ATTACHMENT, BellAttachType.FLOOR);
			}
		}
		
		if (state.hasProperty(BlockStateProperties.HANGING)) {
			state = state.setValue(BlockStateProperties.HANGING, !state.getValue(BlockStateProperties.HANGING));
		}
		
		return state;
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		return new UpsideDownBlockExplosionRule();
	}
}
