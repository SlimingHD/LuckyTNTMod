package luckytnt.rules;

import java.util.Collection;

import com.google.gson.JsonObject;

import luckytnt.LuckyTNTMod;
import luckytntlib.util.explosions.rules.ExplosionRule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MirrorExplosionRule implements ExplosionRule {

	public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(LuckyTNTMod.MODID, "mirror");

	private final boolean flipX, flipY, flipZ;
	private final ExplosionRule rule;
	
	public MirrorExplosionRule(Collection<Axis> axes, ExplosionRule rule) {
		this(axes.contains(Axis.X), axes.contains(Axis.Y), axes.contains(Axis.Z), rule);
	}
	
	public MirrorExplosionRule(boolean flipX, boolean flipY, boolean flipZ, ExplosionRule rule) {
		this.flipX = flipX;
		this.flipY = flipY;
		this.flipZ = flipZ;
		this.rule = rule;
	}
	
	@Override
	public BlockState getState(Level level, BlockState state, Vec3 center, int offX, int offY, int offZ, RandomSource random) {
		return rule.getState(level, level.getBlockState(BlockPos.containing(center).offset(flipX ? -offX : offX, flipY ? -offY : offY, flipZ ? -offZ : offZ)), center, flipX ? -offX : offX, flipY ? -offY : offY, flipZ ? -offZ : offZ, random);
	}

	@Override
	public JsonObject encode(JsonObject root) {
		root.addProperty("type", RESOURCE_LOCATION.toString());
		root.addProperty("flipX", flipX);
		root.addProperty("flipY", flipY);
		root.addProperty("flipZ", flipZ);
		root.add("rule", rule.encode(new JsonObject()));
		return root;
	}

	public static ExplosionRule decode(JsonObject root) {
		return new MirrorExplosionRule(root.get("flipX").getAsBoolean(), root.get("flipY").getAsBoolean(), root.get("flipZ").getAsBoolean(), ExplosionRule.parse(root.get("rule").getAsJsonObject()));
	}
}
