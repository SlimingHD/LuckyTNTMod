package luckytnt.rules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MirrorExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "mirror");
	
	private final Set<Axis> axes;
	private final ExplosionRule rule;
	
	public MirrorExplosionRule(Collection<Axis> axes, ExplosionRule rule) {
		this.axes = Set.copyOf(axes);
		this.rule = rule;
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		return rule.getState(level, state, center, axes.contains(Axis.X) ? -offX : offX, axes.contains(Axis.Y) ? -offY : offY, axes.contains(Axis.Z) ? -offZ : offZ, random);
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		
		JsonArray encodedAxes = new JsonArray();
		for (Axis axis : axes) {
			encodedAxes.add(axis.getName());
		}
		root.add("axes", encodedAxes);
		
		root.add("rule", rule.encode(new JsonObject()));
		
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		JsonArray encodedAxes = root.getAsJsonArray();
		HashSet<Axis> axes = new HashSet<>(encodedAxes.size());
		for (int i = 0; i < encodedAxes.size(); i++) {
			axes.add(Axis.byName(encodedAxes.get(i).getAsString()));
		}
		
		return new MirrorExplosionRule(axes, ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
}
