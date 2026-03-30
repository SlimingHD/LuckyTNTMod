package luckytnt.rules;

import java.util.Collection;
import java.util.LinkedList;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;

public class FilterMapColorExplosionRule implements ExplosionRule {
	
	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "filter_map_color");

	private final Collection<MapColor> colors;
	private final ExplosionRule rule;
	
	public FilterMapColorExplosionRule(Collection<MapColor> colors, ExplosionRule rule) {
		this.colors = colors;
		this.rule = rule;
	}

	@Override
	@Nullable
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		if (colors.contains(state.getMapColor(level, BlockPos.containing(center).offset(offX, offY, offZ)))) {
			return rule.getState(level, state, center, offX, offY, offZ, random);
		}
		return null;
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.add("rule", rule.encode(new JsonObject()));
		
		JsonArray encodedColors = new JsonArray();
		for (MapColor color : colors) {
			encodedColors.add(color.id);
		}
		root.add("colors", encodedColors);
		
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		JsonArray encodedColors = root.get("colors").getAsJsonArray();
		LinkedList<MapColor> colors = new LinkedList<>();
		for (int i = 0; i < encodedColors.size(); i++) {
			colors.add(MapColor.byId(encodedColors.get(i).getAsInt()));
		}
		
		return new FilterMapColorExplosionRule(colors, ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
}
