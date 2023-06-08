package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.util.Materials;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FirestormTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ExplosionHelper.doCylindricalExplosion(ent.getLevel(), ent.getPos(), 50, 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(distance <= 50 && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
					if((!state.isCollisionShapeFullBlock(level, pos) || state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE) 
					|| state.is(BlockTags.LEAVES) || Materials.isPlant(state) || state.is(BlockTags.SNOW)
					|| Materials.isWood(state)) && !(state.getBlock() instanceof GrassBlock) && !(state.getBlock() instanceof MyceliumBlock)) 
					{
						state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					}
				}
			}
		});
		
		ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200 && !state.isAir()) {
					state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
					level.setBlock(pos, Blocks.NETHERRACK.defaultBlockState(), 3);
				}
			}
		});
		
		ExplosionHelper.doTopBlockExplosionForAll(ent.getLevel(), ent.getPos(), 50, new IForEachBlockExplosionEffect() {
			
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
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z(), 0.25D, 0.25D, 0);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z(), -0.25D, 0.25D, 0);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 1D, ent.z() + 0.5D, 0, 0.25D, 0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 1D, ent.z() - 0.5D, 0, 0.25D, -0.25D);
		
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z() + 0.5D, 0.25D, 0.25D, 0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z() + 0.5D, -0.25D, 0.25D, 0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 1D, ent.z() - 0.5D, 0.25D, 0.25D, -0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 1D, ent.z() - 0.5D, -0.25D, 0.25D, -0.25D);
		
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 0.5D, ent.z(), 0.25D, 0, 0);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 0.5D, ent.z(), -0.25D, 0, 0);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5D, ent.z() + 0.5D, 0, 0, 0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5D, ent.z() - 0.5D, 0, 0, -0.25D);
		
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 0.5D, ent.z() + 0.5D, 0.25D, 0, 0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 0.5D, ent.z() + 0.5D, -0.25D, 0, 0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() + 0.5D, ent.y() + 0.5D, ent.z() - 0.5D, 0.25D, 0, -0.25D);
		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x() - 0.5D, ent.y() + 0.5D, ent.z() - 0.5D, -0.25D, 0, -0.25D);

		ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 1D, ent.z(), 0, 0.25D, 0);
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
