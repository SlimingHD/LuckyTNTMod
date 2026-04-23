package luckytnt.rules;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class CopyPropertiesExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "copy_properties");
	
	private final ExplosionRule rule;
	
	public CopyPropertiesExplosionRule(ExplosionRule rule) {
		this.rule = rule;
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		BlockState stateToPlace = rule.getState(level, state, center, offX, offY, offZ, random);
		if (stateToPlace != null) {
			for (Property<?> property : state.getProperties()) {
				if (stateToPlace.hasProperty(property)) {
					stateToPlace = copyProperty(state, stateToPlace, property);
				}
			}
		}
		return stateToPlace;
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.add("rule", rule.encode(new JsonObject()));
		return root;
	}
	
	public static ExplosionRule decode(JsonObject root) {
		return new CopyPropertiesExplosionRule(ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
	
	private static <T extends Comparable<T>, V extends T> BlockState copyProperty(BlockState copyFrom, BlockState copyTo, Property<T> property) {
		return copyTo.setValue(property, copyFrom.getValue(property));
	}
}
