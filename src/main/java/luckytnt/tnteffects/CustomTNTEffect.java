package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.util.CustomTNTConfig;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CustomTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(!ent.getLevel().isClientSide()) {
			CustomTNTConfig config = LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get();
			if(((Entity)ent).getPersistentData().getInt("level") == 0) {
				if(config == CustomTNTConfig.FIREWORK) {
					((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
					if(ent.getTNTFuse() > 40) {
						((Entity)ent).getPersistentData().putInt("fuse", 40);
					}
				}
			}
			if(((Entity)ent).getPersistentData().getInt("level") == 1) {
				config = LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get();
				if(config == CustomTNTConfig.FIREWORK) {
					((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
					if(ent.getTNTFuse() > 40) {
						((Entity)ent).getPersistentData().putInt("fuse", 40);
					}
				}
			}
			if(((Entity)ent).getPersistentData().getInt("level") == 2) {
				config = LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get();
				if(config == CustomTNTConfig.FIREWORK) {
					((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
					if(ent.getTNTFuse() > 40) {
						((Entity)ent).getPersistentData().putInt("fuse", 40);
					}
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		CustomTNTConfig config = LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get();
		if(ent.getPersistentData().getInt("level") == 0) {
			if(config == CustomTNTConfig.NORMAL_EXPLOSION) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue());
				explosion.doEntityExplosion(3f, true);
				explosion.doBlockExplosion(1f, 1.3f, 1f, 1.2f, LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue() > 10 ? true : false, false);
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 3; count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.SPHERICAL_EXPLOSION) {
				ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 5 * LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(), new IForEachBlockExplosionEffect() {
					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				});
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 3; count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.CUBICAL_EXPLOSION) {
				ExplosionHelper.doCubicalExplosion(ent.getLevel(), ent.getPos(), 5 * LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(), new IForEachBlockExplosionEffect() {
					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				});
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 3; count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.EASTER_EGG) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 3 * LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue());
				explosion.doBlockExplosion(1f, 1f, 1f, 3 * LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().floatValue() > 30f ? 1.75f : 1.5f, false, false);
				explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {		
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(Math.random() < 0.66f && !state.isAir()) {
							state.onBlockExploded(level, pos, explosion);
							if(Math.random() < 0.5f) {
								ent.getLevel().setBlockAndUpdate(pos, Blocks.MELON.defaultBlockState());
							}
							else {
								ent.getLevel().setBlockAndUpdate(pos, Blocks.PUMPKIN.defaultBlockState());
							}
						}
					}
				});
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 1 * LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(); count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.FIREWORK) {
				for(int count = 0; count < 15 * LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(); count++) {
					PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
					custom.setPos(ent.getPos());
					custom.setOwner(ent.owner());
					custom.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
					custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
					ent.getLevel().addFreshEntity(custom);
				}
			}
		}
		if(ent.getPersistentData().getInt("level") == 1) {
			config = LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get();
			if(config == CustomTNTConfig.NORMAL_EXPLOSION) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue());
				explosion.doEntityExplosion(3f, true);
				explosion.doBlockExplosion(1f, 1.3f, 1f, 1.2f, LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue() > 10 ? true : false, false);
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 3; count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.SPHERICAL_EXPLOSION) {
				ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 5 * LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(), new IForEachBlockExplosionEffect() {
					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				});
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 3; count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.CUBICAL_EXPLOSION) {
				ExplosionHelper.doCubicalExplosion(ent.getLevel(), ent.getPos(), 5 * LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(), new IForEachBlockExplosionEffect() {
					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				});
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 3; count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.EASTER_EGG) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 3 * LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue());
				explosion.doBlockExplosion(1f, 1f, 1f, 3 * LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().floatValue() > 30f ? 1.75f : 1.5f, false, false);
				explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {		
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(Math.random() < 0.66f && !state.isAir()) {
							state.onBlockExploded(level, pos, explosion);
							if(Math.random() < 0.5f) {
								ent.getLevel().setBlockAndUpdate(pos, Blocks.MELON.defaultBlockState());
							}
							else {
								ent.getLevel().setBlockAndUpdate(pos, Blocks.PUMPKIN.defaultBlockState());
							}
						}
					}
				});
				
				if(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get() != CustomTNTConfig.NO_EXPLOSION) {
					for(int count = 0; count < 1 * LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(); count++) {
						PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
						custom.setPos(ent.getPos());
						custom.setOwner(ent.owner());
						custom.setDeltaMovement(Math.random() * 2f - 1f, Math.random() * 2f, Math.random() * 2f - 1f);
						custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
						ent.getLevel().addFreshEntity(custom);
					}
				}
			}
			if(config == CustomTNTConfig.FIREWORK) {
				for(int count = 0; count < 15 * LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(); count++) {
					PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(ent.getLevel());
					custom.setPos(ent.getPos());
					custom.setOwner(ent.owner());
					custom.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
					custom.getPersistentData().putInt("level", ent.getPersistentData().getInt("level") + 1);
					ent.getLevel().addFreshEntity(custom);
				}
			}
		}
		if(ent.getPersistentData().getInt("level") == 2) {
			config = LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get();
			if(config == CustomTNTConfig.NORMAL_EXPLOSION) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue());
				explosion.doEntityExplosion(3f, true);
				explosion.doBlockExplosion(1f, 1.3f, 1f, 1.2f, LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue() > 10 ? true : false, false);
			}
			if(config == CustomTNTConfig.SPHERICAL_EXPLOSION) {
				ExplosionHelper.doSphericalExplosion(ent.getLevel(), ent.getPos(), 5 * LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue(), new IForEachBlockExplosionEffect() {
					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				});
			}
			if(config == CustomTNTConfig.CUBICAL_EXPLOSION) {
				ExplosionHelper.doCubicalExplosion(ent.getLevel(), ent.getPos(), 5 * LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue(), new IForEachBlockExplosionEffect() {
					
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(!state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) <= 200) {
							state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					}
				});
			}
			if(config == CustomTNTConfig.EASTER_EGG) {
				ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 3 * LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue());
				explosion.doBlockExplosion(1f, 1f, 1f, 3 * LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().floatValue() > 30f ? 1.75f : 1.5f, false, false);
				explosion.doBlockExplosion(new IForEachBlockExplosionEffect() {		
					@Override
					public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
						if(Math.random() < 0.66f && !state.isAir()) {
							state.onBlockExploded(level, pos, explosion);
							if(Math.random() < 0.5f) {
								ent.getLevel().setBlockAndUpdate(pos, Blocks.MELON.defaultBlockState());
							}
							else {
								ent.getLevel().setBlockAndUpdate(pos, Blocks.PUMPKIN.defaultBlockState());
							}
						}
					}
				});
			}
			if(config == CustomTNTConfig.FIREWORK) {
				for(int count = 0; count < 15 * LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue(); count++) {
					PrimedLTNT custom = EntityRegistry.TNT.get().create(ent.getLevel());
					custom.setPos(ent.getPos());
					custom.setOwner(ent.owner());
					custom.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
					ent.getLevel().addFreshEntity(custom);
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 1f, 0f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 1f), 1f), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		if(ent.getLevel().isClientSide()) {
			CustomTNTConfig config = LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get();
			if(ent.getPersistentData().getInt("level") == 0) {
				if(config == CustomTNTConfig.FIREWORK) {
					ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0, 0);
				}
			}
			if(ent.getPersistentData().getInt("level") == 1) {
				config = LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get();
				if(config == CustomTNTConfig.FIREWORK) {
					ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0, 0);
				}
			}
			if(ent.getPersistentData().getInt("level") == 2) {
				config = LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get();
				if(config == CustomTNTConfig.FIREWORK) {
					ent.getLevel().addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0, 0);
				}
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUSTOM_TNT.get();
	}
}
