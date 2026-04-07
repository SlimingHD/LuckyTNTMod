package luckytnt.rules;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CopyBlockExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "copy_block");
	
	private final int xOffset, yOffset, zOffset;
	
	public CopyBlockExplosionRule(int xOffset, int yOffset, int zOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
	
	public CopyBlockExplosionRule(int yOffset) {
		this(0, yOffset, 0);
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		return level.getBlockState(BlockPos.containing(center).offset(offX + xOffset, offY + yOffset, offZ + zOffset));
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.addProperty("offX", xOffset);
		root.addProperty("offY", yOffset);
		root.addProperty("offZ", zOffset);
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		return new CopyBlockExplosionRule(root.get("offX").getAsInt(), root.get("offY").getAsInt(), root.get("offZ").getAsInt());
	}
}
