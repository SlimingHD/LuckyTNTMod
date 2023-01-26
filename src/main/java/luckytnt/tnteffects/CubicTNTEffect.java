package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class CubicTNTEffect extends PrimedTNTEffect{
	private final int radius;
	private final Supplier<RegistryObject<LTNTBlock>> block;
	
	public CubicTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int radius) {
		this.block = block;
		this.radius = radius;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doCubicalExplosion(entity.level(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion()) <= 100) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion());
				}
			}
		});
	}
	
	@Override
	public Block getBlock() {
		return block.get().get();
	}
}
