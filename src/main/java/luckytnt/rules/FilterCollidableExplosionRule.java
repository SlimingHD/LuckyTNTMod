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

public class FilterCollidableExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "filter_collidable");
	
	private final ExplosionRule rule;
	
	public FilterCollidableExplosionRule(ExplosionRule rule) {
		this.rule = rule;
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		if (!state.getCollisionShape(level, BlockPos.containing(center).offset(offX, offY, offZ)).isEmpty()) {
			return rule.getState(level, state, center, offX, offY, offZ, random);
		}
		return null;
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.add("rule", rule.encode(new JsonObject()));
		return root;
	}
	
	public static ExplosionRule decode(JsonObject root) {
		return new FilterCollidableExplosionRule(ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
}
