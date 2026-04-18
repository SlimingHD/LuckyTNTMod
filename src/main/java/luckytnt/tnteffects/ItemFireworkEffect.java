package luckytnt.tnteffects;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

import luckytnt.registry.BlockRegistry;
import luckytntlib.item.LDynamiteItem;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ItemFireworkEffect extends PrimedTNTEffect {

	private static Field TYPE_FIELD;
	private static Field CHEST_FIELD;
	
	static {
		try {
			for (Field field : BoatItem.class.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getType() == Boat.Type.class) {
					TYPE_FIELD = field;
				}
				if (field.getType() == boolean.class) {
					CHEST_FIELD = field;
				}
			}
		} catch (SecurityException | IllegalArgumentException | InaccessibleObjectException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity instanceof Entity ent) {
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8d, ent.getDeltaMovement().z);
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		ItemStack stack = ItemStack.of(entity.getPersistentData().getCompound("stack"));
		stack.setCount(1);
		Item item = stack.getItem();
		
		if (item instanceof BoatItem boatItem) {
			boolean hasChest = false;
			Type type = Type.OAK;
			try {
				hasChest = CHEST_FIELD.getBoolean(boatItem);
				type = (Type)TYPE_FIELD.get(boatItem);
			} catch (NullPointerException | ExceptionInInitializerError | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < 300; i++) {
				Boat boat;
				if (hasChest) {
					boat = new ChestBoat(level, entity.x(), entity.y(), entity.z());
				} else {
					boat = new Boat(level, entity.x(), entity.y(), entity.z());
				}
				boat.setVariant(type);
				addEntity(boat, random, 3d);
			}
		} else if (stack.is(Items.FIRE_CHARGE)) {
			double phi = Math.PI * (3d - Math.sqrt(5d));
			for (int i = 0; i < 300; i++) {
				double y = 1d - (i / 299d) * 2d;
				double radius = Math.sqrt(1d - y * y);
			
				double theta = phi * i;
			
				double x = Math.cos(theta) * radius;
				double z = Math.sin(theta) * radius;
				
				LargeFireball fireball = new LargeFireball(EntityType.FIREBALL, level);
				fireball.setPos(entity.x() + x * 15d, entity.y() + y * 15d, entity.z() + z * 15d);
				Vec3 vec = new Vec3(x, y, z).normalize().scale(0.5d);
				fireball.xPower = vec.x;
				fireball.yPower = vec.y;
				fireball.zPower = vec.z;
				addEntity(fireball, random, 0d);
			}
		} else if (item == Items.DRAGON_BREATH) {
			for (int i = 0; i < 300; i++) {
				DragonFireball fireball = new DragonFireball(EntityType.DRAGON_FIREBALL, level);
				fireball.setPos(entity.getPos());
				fireball.xPower = random.nextDouble() - 0.5d;
				fireball.yPower = random.nextDouble() - 0.5d;
				fireball.zPower = random.nextDouble() - 0.5d;
				addEntity(fireball, random, 0d);
			}
		} else if (item instanceof ThrowablePotionItem) {
			for (int i = 0; i < 300; i++) {
				ThrownPotion potion = new ThrownPotion(level, entity.x(), entity.y(), entity.z());
				potion.setItem(stack);
				addEntity(potion, random, 1.5d);
			}
		} else if (item instanceof ArrowItem) {
			for (int i = 0; i < 300; i++) {
				if (stack.is(Items.SPECTRAL_ARROW)) {
					addEntity(new SpectralArrow(level, entity.x(), entity.y(), entity.z()), random, 3d);
				} else {
					Arrow arrow = new Arrow(level, entity.x(), entity.y(), entity.z());
					arrow.setEffectsFromItem(stack);
					addEntity(arrow, random, 3d);
				}
			}
		} else if (stack.is(Items.EGG)) {
			for (int i = 0; i < 300; i++) {
				addEntity(new ThrownEgg(level, entity.x(), entity.y(), entity.z()), random, 1.5d);
			}
		} else if (stack.is(Items.SNOWBALL)) {
			for (int count = 0; count < 300; count++) {
				addEntity(new Snowball(level, entity.x(), entity.y(), entity.z()), random, 1.5d);
			}
		} else if (item instanceof LDynamiteItem dynamite) {
			for (int count = 0; count < 300; count++) {
				dynamite.shoot(level, entity.x(), entity.y(), entity.z(), new Vec3(random.nextDouble() * 6d - 3d, random.nextDouble() * 6d - 3d, random.nextDouble() * 6d - 3d), 1f + random.nextFloat(), null);
			}
		} else if (item instanceof FireworkRocketItem) {
			for (int count = 0; count < 300; count++) {
				addEntity(new FireworkRocketEntity(level, stack, entity.x(), entity.y(), entity.z(), true), random, 1d);
			}
		} else {
			for (int i = 0; i < 300; i++) {
				addEntity(new ItemEntity(level, entity.x(), entity.y(), entity.z(), stack.copy()), random, 1.5d);
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.ITEM_FIREWORK.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
	
	private static void addEntity(Entity entity, RandomSource random, double motion) {
		entity.setDeltaMovement(random.nextDouble() * motion * 2d - motion, random.nextDouble() * motion * 2d - motion, random.nextDouble() * motion * 2d - motion);
		entity.level().addFreshEntity(entity);
	}
}
