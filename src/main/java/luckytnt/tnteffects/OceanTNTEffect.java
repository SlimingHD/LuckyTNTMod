package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class OceanTNTEffect extends PrimedTNTEffect {
	private final int radius;
	private final int radiusY;
	private final int squidCound;
	private Supplier<RegistryObject<LTNTBlock>> block;

	public OceanTNTEffect(Supplier<RegistryObject<LTNTBlock>> block, int radius, int radiusY, int squidCount) {
		this.radius = radius;
		this.radiusY = radiusY;
		this.squidCound = squidCount;
		this.block = block;
	}
	
	public OceanTNTEffect(int radius, int radiusY, int squidCount) {
		this.radius = radius;
		this.radiusY = radiusY;
		this.squidCound = squidCount;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(entity.getLevel());
		ExplosionHelper.doCylindricalExplosion(entity.getLevel(), entity.getPos(), radius, radiusY, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(pos.getY() <= entity.getPos().y) {
					if((!state.isFaceSturdy(level, pos, Direction.UP) && state.getExplosionResistance(level, pos, dummyExplosion) < 100) || state.getExplosionResistance(level, pos, dummyExplosion) < 4) {
						state.onBlockExploded(level, pos, dummyExplosion);
						level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
					}
				}
			}
		});
		
		for(int i = 0; i < squidCound; i++) {
			Squid squid = new Squid(EntityType.SQUID, entity.getLevel());
			squid.setPos(entity.x() + (Math.random() * radius * 2 - radius), entity.y(), entity.z() + (Math.random() * radius * 2 - radius));
			entity.getLevel().addFreshEntity(squid);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.SPLASH, ent.x(), ent.y() + 0.7f, ent.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return block.get().get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
