package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class HeatWaveEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 150, new IForEachBlockExplosionEffect() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
					if(state.getMaterial() == Material.AIR && Blocks.FIRE.canSurvive(state, level, pos)) {
						BlockPlaceContext ctx = new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, new ItemStack(Items.FLINT_AND_STEEL), new BlockHitResult(ent.getPos(), Direction.DOWN, pos, true));
						BlockState stateForPlacement = Blocks.FIRE.getStateForPlacement(ctx);
						level.setBlock(pos, stateForPlacement, 3);
					}
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(int i = 0; i < 50; i++) {
			ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + Math.random() * 10 - Math.random() * 10, ent.y() + Math.random() * 10 - Math.random() * 10, ent.z() + Math.random() * 10 - Math.random() * 10, Math.random() * 0.1 - Math.random() * 0.1, Math.random() * 0.1 - Math.random() * 0.1, Math.random() * 0.1 - Math.random() * 0.1);
		}	
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HEAT_WAVE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 200;
	}
}
