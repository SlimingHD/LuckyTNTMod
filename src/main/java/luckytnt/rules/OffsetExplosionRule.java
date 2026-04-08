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

public class OffsetExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "offset");
	
	private final int xOffset, yOffset, zOffset;
	private final ExplosionRule rule;
	
	public OffsetExplosionRule(int xOffset, int yOffset, int zOffset, ExplosionRule rule) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		this.rule = rule;
	}
	
	public OffsetExplosionRule(int yOffset, ExplosionRule rule) {
		this(0, yOffset, 0, rule);
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		return rule.getState(level, level.getBlockState(BlockPos.containing(center).offset(offX + xOffset, offY + yOffset, offZ + zOffset)), center, offX + xOffset, offY + yOffset, offZ + zOffset, random);
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.addProperty("offX", xOffset);
		root.addProperty("offY", yOffset);
		root.addProperty("offZ", zOffset);
		root.add("rule", rule.encode(new JsonObject()));
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		return new OffsetExplosionRule(root.get("offX").getAsInt(), root.get("offY").getAsInt(), root.get("offZ").getAsInt(), ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
}
