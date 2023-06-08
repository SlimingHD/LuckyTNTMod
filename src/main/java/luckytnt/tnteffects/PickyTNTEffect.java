package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.entity.LTNTMinecart;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PickyTNTEffect extends PrimedTNTEffect{

	private final int radius;
	
	public PickyTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Block template;
		if(entity instanceof PrimedLTNT || entity instanceof LTNTMinecart) {
			template = entity.getLevel().getBlockState(toBlockPos(entity.getPos()).below()).getBlock();
		}
		else {
			BlockHitResult result = entity.getLevel().clip(new ClipContext(entity.getPos(), entity.getPos().add(((Entity)entity).getDeltaMovement().normalize().scale(0.5f)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)entity));
			if(result != null) {
				template = entity.getLevel().getBlockState(result.getBlockPos()).getBlock();
			}
			else {
				template = Blocks.AIR;
			}
		}
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100 && !state.isAir() && state.getBlock() == template) {
					List<ItemStack> drops = Block.getDrops(state, (ServerLevel)level, pos, level.getBlockEntity(pos));
					for(ItemStack stack : drops) {
						ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
						level.addFreshEntity(item);
					}
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.PICKY_TNT.get();
	}
}
