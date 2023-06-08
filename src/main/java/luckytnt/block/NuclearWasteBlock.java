package luckytnt.block;

import java.util.Collections;
import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.EffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class NuclearWasteBlock extends FallingBlock {
	public NuclearWasteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(0, 0, 0, 16, 2, 16);
	}
	
	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return 8;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos posDown = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		if(Block.isFaceFull(level.getBlockState(posDown).getCollisionShape(level, posDown), Direction.UP) || level.getBlockState(posDown).isAir()){
			return true;
		}
		return super.canSurvive(state, level, pos);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.tick(state, level, pos, random);
		if(Math.random() < 0.2f) {
			if(level.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock().getExplosionResistance() < 100) {
				level.setBlock(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), Blocks.AIR.defaultBlockState(), 3);
				level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
				if(Math.random() < 0.05f) {
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
					level.sendParticles(new DustParticleOptions(new Vector3f(1f, 1f, 0.1f), 1), pos.getX(), pos.getY(), pos.getZ(), 40, 0.6f, 0.6f, 0.6f, 0);
				}
				level.sendParticles(new DustParticleOptions(new Vector3f(1f, 1f, 0.1f), 1), pos.getX(), pos.getY() - 1, pos.getZ(), 40, 0.6f, 0.6f, 0.6f, 0);
			}
		}
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		return Collections.singletonList(ItemStack.EMPTY);
	}
		
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
		if(entity instanceof LivingEntity l_Entity) {
			l_Entity.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 4, false, true));
			l_Entity.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 120, 0, false, true));
			l_Entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0, false, true));
		}
		else if(entity instanceof ItemEntity i_Entity) {
			i_Entity.hurt(new Explosion(level, entity, null, null, 0, 0, 0, 0, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY).getDamageSource(), 100);
		}
	}
}
