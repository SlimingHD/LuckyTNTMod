package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EndGateEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 30, new IForEachBlockExplosionEffect() {
		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0);
				BlockState stateTop = level.getBlockState(posTop);
				
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 200 && stateTop.isAir() && !state.isAir() && Math.abs(entity.y() - pos.getY()) <= 20) {
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					if(Materials.isWood(state)) {
						level.setBlock(posTop, Blocks.OBSIDIAN.defaultBlockState(), 3);
					} else if(state.is(BlockTags.LEAVES)) {
						level.setBlock(posTop, Blocks.END_STONE.defaultBlockState(), 3);
					} else if(state.getBlock() instanceof LiquidBlock) {
						level.setBlock(posTop, Blocks.AIR.defaultBlockState(), 3);
					} else {
						level.setBlock(posTop, Blocks.END_STONE.defaultBlockState(), 3);
					}
				}
			}
		});
		
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 30, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				BlockPos posTop = pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0);
				BlockState stateTop = level.getBlockState(posTop);
				BlockPos posAbove = pos.offset(0, LuckyTNTConfigValues.ISLAND_HEIGHT.get() + 1, 0);
				BlockState stateAbove = level.getBlockState(posAbove);
				
				if(stateAbove.isAir() && Math.random() <= 0.05D && stateTop.getBlock() == Blocks.END_STONE) {
					level.setBlock(posAbove, Blocks.CHORUS_FLOWER.defaultBlockState(), 3);
				}
			}
		});
		
		for(int i = 0; i < 80; i++) {
			int offX = (int)Math.round(Math.random() * 30D - 15D);
			int offZ = (int)Math.round(Math.random() * 30D - 15D);
			EnderMan man = new EnderMan(EntityType.ENDERMAN, entity.getLevel());
			for(int offY = 320; offY >= -64; offY--) {
				BlockPos pos = toBlockPos(new Vec3(entity.x() + offX, offY, entity.z() + offZ));
				BlockPos posDown = toBlockPos(new Vec3(entity.x() + offX, offY - 1, entity.z() + offZ));
				BlockState state = entity.getLevel().getBlockState(pos);
				BlockState stateDown = entity.getLevel().getBlockState(posDown);
				
				if(Block.isFaceFull(stateDown.getCollisionShape(entity.getLevel(), posDown), Direction.UP) && !Block.isFaceFull(state.getCollisionShape(entity.getLevel(), pos), Direction.UP)) {
					man.setPos(entity.x() + offX, offY, entity.z() + offZ);
					break;
				}
			}
			entity.getLevel().addFreshEntity(man);
		}
		
		entity.getLevel().playSound(null, toBlockPos(entity.getPos()), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 0.5f, 1);
		if(entity.getLevel() instanceof ServerLevel sLevel) {
			sLevel.setDayTime(18000);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.END_ROD, ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.END_GATE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
