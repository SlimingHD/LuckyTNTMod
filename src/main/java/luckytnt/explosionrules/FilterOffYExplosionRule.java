package luckytnt.explosionrules;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FilterOffYExplosionRule implements ExplosionRule {
	
	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "filter_offy");

	private final int minCutOff;
	private final int maxCutOff;
	private final ExplosionRule rule;
	
	public FilterOffYExplosionRule(int minCutOff, int maxCutOff, ExplosionRule rule) {
		this.minCutOff = minCutOff;
		this.maxCutOff = maxCutOff;
		this.rule = rule;
	}

	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		if (offY < minCutOff || offY > maxCutOff) {
			return null;
		}
		return rule.getState(level, state, center, offX, offY, offZ, random);
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.addProperty("min", minCutOff);
		root.addProperty("max", maxCutOff);
		root.add("rule", rule.encode(new JsonObject()));
		return root;
	}
	
	public static ExplosionRule decode(JsonObject root) {
		return new FilterOffYExplosionRule(root.get("min").getAsInt(), root.get("max").getAsInt(), ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
}
