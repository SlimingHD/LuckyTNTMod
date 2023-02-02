package luckytnt.tnteffects;

import java.lang.reflect.Field;

import luckytnt.entity.PrimedItemFirework;
import luckytnt.registry.BlockRegistry;
import luckytntlib.item.LDynamiteItem;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.SpectralArrowItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ItemFireworkEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(entity instanceof PrimedItemFirework ent) {
			Item item = ent.item;
			ItemStack stack = ent.stack;
			if(item == null) {
				item = Item.byId(ent.getPersistentData().getInt("itemID"));
			}
			if(item != null) { 
				if(item instanceof BoatItem boatitem) {
					boolean hasChest = false;
					Type type = Type.OAK;
					try {
						Field chest = BoatItem.class.getDeclaredField("hasChest");
						Field boattype = BoatItem.class.getDeclaredField("type");
						chest.setAccessible(true);
						boattype.setAccessible(true);
						hasChest = chest.getBoolean(boatitem);
						type = (Type)boattype.get(boatitem);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					for(int i = 0; i < 300; i++) {
						Boat boat = new Boat(ent.level(), ent.x(), ent.y(), ent.z());
						if(hasChest) {
							boat = new ChestBoat(ent.level(), ent.x(), ent.y(), ent.z());
						}
						boat.setVariant(type);
						boat.setDeltaMovement(Math.random() * 6D - 3D, Math.random() * 6D - 3D, Math.random() * 6D - 3D);
						ent.level().addFreshEntity(boat);
					}
				} else if(item instanceof FireChargeItem) {
					double phi = Math.PI * (3D - Math.sqrt(5D));
					for(int i = 0; i < 300; i++) {
						double y = 1D - ((double)i / (300D - 1D)) * 2D;
						double radius = Math.sqrt(1D - y * y);
					
						double theta = phi * i;
					
						double x = Math.cos(theta) * radius;
						double z = Math.sin(theta) * radius;
						
						LargeFireball fireball = new LargeFireball(EntityType.FIREBALL, ent.level());
						fireball.setPos(ent.x() + x * 15, ent.y() + y * 15, ent.z() + z * 15);
						Vec3 vec = new Vec3(fireball.getX() - ent.x(), fireball.getY() - ent.y(), fireball.getZ() - ent.z()).normalize().scale(0.5D);
						fireball.xPower = vec.x;
						fireball.yPower = vec.y;
						fireball.zPower = vec.z;
						ent.level().addFreshEntity(fireball);
					}
				} else if(item == Items.DRAGON_BREATH) {
					for(int i = 0; i < 300; i++) {
						DragonFireball fireball = new DragonFireball(EntityType.DRAGON_FIREBALL, ent.level());
						fireball.setPos(ent.getPos());
						fireball.xPower = Math.random() - 0.5f;
						fireball.yPower = Math.random() - 0.5f;
						fireball.zPower = Math.random() - 0.5f;
						ent.level().addFreshEntity(fireball);
					}
				} else if(item instanceof ThrowablePotionItem) {
					for(int i = 0; i < 300; i++) {
						ThrownPotion potion = new ThrownPotion(ent.level(), ent.x(), ent.y(), ent.z());
						potion.setItem(stack != null ? stack : new ItemStack(item));
						potion.setDeltaMovement(Math.random() * 3D - 1.5D, Math.random() * 3D - 1.5D, Math.random() * 3D - 1.5D);
						ent.level().addFreshEntity(potion);
					}
				} else if(item instanceof ArrowItem) {
					for(int count = 0; count < 300; count++) {
						if(item instanceof SpectralArrowItem) {
							AbstractArrow arrow = new SpectralArrow(ent.level(), ent.x(), ent.y(), ent.z());
							arrow.setDeltaMovement(Math.random() * 6f - 3f, Math.random() * 6f - 3f, Math.random() * 6f - 3f);
							ent.level().addFreshEntity(arrow);
						} else {
							Arrow arrow = new Arrow(ent.level(), ent.x(), ent.y(), ent.z());
							arrow.setEffectsFromItem(stack == null ? new ItemStack(item) : stack);
							arrow.setDeltaMovement(Math.random() * 6f - 3f, Math.random() * 6f - 3f, Math.random() * 6f - 3f);
							ent.level().addFreshEntity(arrow);
						}
					}
				} else if(item instanceof EggItem) {
					for(int count = 0; count < 300; count++) {
						ThrownEgg egg = new ThrownEgg(ent.level(), ent.x(), ent.y(), ent.z());
						egg.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
						ent.level().addFreshEntity(egg);
					}
				} else if(item instanceof SnowballItem) {
					for(int count = 0; count < 300; count++) {
						Snowball ball = new Snowball(ent.level(), ent.x(), ent.y(), ent.z());
						ball.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
						ent.level().addFreshEntity(ball);
					}
				} else if(item instanceof LDynamiteItem dynamite) {
					for(int count = 0; count < 300; count++) {
						dynamite.shoot(ent.level(), ent.x(), ent.y(), ent.z(), new Vec3(Math.random() * 6D - 3D, Math.random() * 6D - 3D, Math.random() * 6D - 3D), 1f + (float)Math.random(), null);
					}
				} else if(item instanceof FireworkRocketItem) {
					for(int count = 0; count < 300; count++) {
						FireworkRocketEntity rocket = new FireworkRocketEntity(ent.level(), ent.x(), ent.y(), ent.z(), stack == null ? new ItemStack(item) : stack);
						rocket.setDeltaMovement(Math.random() * 0.5f - 0.25f, Math.random() * 0.5f - 0.25f, Math.random() * 0.5f - 0.25f);
						ent.level().addFreshEntity(rocket);
					}
				} else {
					for(int i = 0; i < 300; i++) {
						ItemEntity itement = new ItemEntity(ent.level(), ent.x(), ent.y(), ent.z(), stack == null ? new ItemStack(item) : stack.copy());
						itement.setDeltaMovement(Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f, Math.random() * 3f - 1.5f);
						ent.level().addFreshEntity(itement);
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.level().addParticle(ParticleTypes.FLAME, ent.x(), ent.y(), ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ITEM_FIREWORK.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 40;
	}
}
