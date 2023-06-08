package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class SphereTNTEffect extends PrimedTNTEffect{


	private Supplier<RegistryObject<LTNTBlock>> block;
	private final int strength;
	
	public SphereTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int strength) {
		this.block = block;
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), strength, new IForEachBlockExplosionEffect() {
		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) <= 100) {
					state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
				}
			}
		});
	}

	@Override
	public Block getBlock() {
		return block.get().get();
	}
}
