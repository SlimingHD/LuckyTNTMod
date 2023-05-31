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
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FirestormTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doCylindricalExplosion(ent.level(), ent.getPos(), 50, 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 50 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200) {
					if((state.getMaterial() == Material.BAMBOO || state.getMaterial() == Material.BAMBOO_SAPLING || state.getMaterial() == Material.CACTUS
					|| state.getMaterial() == Material.CLOTH_DECORATION || state.getMaterial() == Material.DECORATION || state.getMaterial() == Material.FIRE
					|| state.getMaterial() == Material.GRASS || state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.MOSS
					|| state.getMaterial() == Material.NETHER_WOOD || state.getMaterial() == Material.PLANT || state.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT
					|| state.getMaterial() == Material.REPLACEABLE_PLANT || state.getMaterial() == Material.REPLACEABLE_WATER_PLANT || state.getMaterial() == Material.SNOW
					|| state.getMaterial() == Material.TOP_SNOW || state.getMaterial() == Material.VEGETABLE || state.getMaterial() == Material.WATER_PLANT
					|| state.getMaterial() == Material.WOOD) && !(state.getBlock() instanceof GrassBlock) && !(state.getBlock() instanceof MyceliumBlock)) 
					{
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.level()));
					}
				}
			}
		});
		
		ExplosionHelper.doSphericalExplosion(ent.level(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.level())) <= 200 && !state.isAir()) {
					state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.level()));
					level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
				}
			}
		});
		
		ExplosionHelper.doTopBlockExplosionForAll(ent.level(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(Math.random() < 0.75f) {
					BlockPlaceContext ctx = new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, new ItemStack(Items.FLINT_AND_STEEL), new BlockHitResult(new Vec3(ent.x(), ent.y(), ent.z()), Direction.DOWN, pos, true));
					level.setBlock(pos, Blocks.FIRE.getStateForPlacement(ctx), 3);
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z(), 0.25D, 0.25D, 0);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z(), -0.25D, 0.25D, 0);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 1D, ent.z() + 0.5D, 0, 0.25D, 0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 1D, ent.z() - 0.5D, 0, 0.25D, -0.25D);
		
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z() + 0.5D, 0.25D, 0.25D, 0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z() + 0.5D, -0.25D, 0.25D, 0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z() - 0.5D, 0.25D, 0.25D, -0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z() - 0.5D, -0.25D, 0.25D, -0.25D);
		
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 0.5D, ent.z(), 0.25D, 0, 0);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 0.5D, ent.z(), -0.25D, 0, 0);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5D, ent.z() + 0.5D, 0, 0, 0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5D, ent.z() - 0.5D, 0, 0, -0.25D);
		
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 0.5D, ent.z() + 0.5D, 0.25D, 0, 0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 0.5D, ent.z() + 0.5D, -0.25D, 0, 0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 0.5D, ent.z() - 0.5D, 0.25D, 0, -0.25D);
		ent.level().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 0.5D, ent.z() - 0.5D, -0.25D, 0, -0.25D);

		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 1D, ent.z(), 0, 0.25D, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.FIRESTORM_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}
}
