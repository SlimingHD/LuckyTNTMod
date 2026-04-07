package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.entity.LTNTMinecart;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

public class PickyTNTEffect extends PrimedTNTEffect {

	private final int radius;

	public PickyTNTEffect(int radius) {
		this.radius = radius;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		Vec3 pos = entity.getPos();

		Block blockToDestroy = Blocks.AIR;
		if (entity instanceof PrimedLTNT || entity instanceof LTNTMinecart) {
			blockToDestroy = level.getBlockState(BlockPos.containing(pos).below()).getBlock();
		} else {
			if (entity instanceof Entity ent) {
				BlockHitResult result = level.clip(new ClipContext(pos, pos.add(ent.getDeltaMovement().normalize().scale(0.5f)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, ent));
				if (result != null && result.getType() != Type.MISS) {
					blockToDestroy = level.getBlockState(result.getBlockPos()).getBlock();
				}
			}
		}

		ExplosionHelper.legacySphericalExplosion(level, pos, radius, 99f, FilterBlockExplosionRule.applyOnlyWhen(blockToDestroy, new AlwaysExplosionRule()));
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.PICKY_TNT.get();
	}
}
