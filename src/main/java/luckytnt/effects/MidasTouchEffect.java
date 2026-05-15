package luckytnt.effects;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MidasTouchEffect extends MobEffect {
	
	private static final Set<Item> EXCLUDED_ITEMS = Util.make(new HashSet<>(), s -> {
		s.add(Items.AIR);
		s.add(Items.GOLD_BLOCK);
		s.add(Items.RAW_GOLD_BLOCK);
		s.add(Items.GOLDEN_AXE);
		s.add(Items.GOLDEN_SWORD);
		s.add(Items.GOLDEN_HOE);
		s.add(Items.GOLDEN_SHOVEL);
		s.add(Items.GOLDEN_PICKAXE);
		s.add(Items.GOLDEN_HELMET);
		s.add(Items.GOLDEN_CHESTPLATE);
		s.add(Items.GOLDEN_LEGGINGS);
		s.add(Items.GOLDEN_BOOTS);
		s.add(Items.GOLDEN_HORSE_ARMOR);
		s.add(Items.GOLDEN_APPLE);
		s.add(Items.ENCHANTED_GOLDEN_APPLE);
		s.add(Items.GOLDEN_CARROT);
		s.add(Items.RAW_GOLD);
		s.add(Items.GOLD_INGOT);
		s.add(Items.GOLD_NUGGET);
		s.add(Items.GLISTERING_MELON_SLICE);
	});
	
	public MidasTouchEffect(MobEffectCategory category, int color) {
		super(category, color);		
	}
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("effect.luckytntmod.midas_touch");
	}
	
	@Override
	public boolean isBeneficial() {
		return false;
	}
	
	@Override
	public boolean isInstantenous() {
		return false;
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		Level level = entity.level();
		if (level instanceof ServerLevel server) {
			placeGoldBlocks(server, entity.getPosition(1), entity.getPosition(1).add(0, -1, 0), entity);
			placeGoldBlocks(server, entity.getPosition(1).add(0, entity.getEyeHeight(), 0), entity.getPosition(1).add(0, entity.getEyeHeight(), 0).add(entity.getViewVector(1).scale(5)), entity);
			
			replaceItem(entity, InteractionHand.MAIN_HAND);
			replaceItem(entity, InteractionHand.OFF_HAND);
			
			replaceArmor(entity, EquipmentSlot.HEAD);
			replaceArmor(entity, EquipmentSlot.CHEST);
			replaceArmor(entity, EquipmentSlot.LEGS);
			replaceArmor(entity, EquipmentSlot.FEET);
		}
	}
	
	private static void placeGoldBlocks(ServerLevel level, Vec3 raytraceFrom, Vec3 raytraceTo, LivingEntity ent) {
		BlockHitResult result = level.clip(new ClipContext(raytraceFrom, raytraceTo, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, ent));
		if (result != null && result.getType() != HitResult.Type.MISS) {
			BlockState state = level.getBlockState(result.getBlockPos());
			if (state.getExplosionResistance(level, result.getBlockPos(), ImprovedExplosion.dummyExplosion(level)) < 100 && !state.isAir()) {
				level.setBlock(result.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
			}
		}
	}
	
	private static void replaceArmor(LivingEntity ent, EquipmentSlot slot) {
		ItemStack stack = ent.getItemBySlot(slot);
		Item replacement = makeItemGolden(stack.getItem());
		if (replacement != null) {
			ItemStack newStack = new ItemStack(replacement, stack.getCount());
			newStack.setTag(stack.getOrCreateTag());
			ent.setItemSlot(slot, newStack);
		}
	}
	
	private static void replaceItem(LivingEntity ent, InteractionHand hand) {
		ItemStack stack = ent.getItemInHand(hand);
		Item replacement = makeItemGolden(stack.getItem());
		if (replacement != null) {
			ItemStack newStack = new ItemStack(replacement, stack.getCount());
			newStack.setTag(stack.getOrCreateTag());
			ent.setItemInHand(hand, newStack);
		}
	}
	
	@Nullable
	private static Item makeItemGolden(Item item) {
		if (EXCLUDED_ITEMS.contains(item)) {
			return null;
		}
		
		if (item instanceof SwordItem) {
			return Items.GOLDEN_SWORD;
		} else if (item instanceof ShovelItem) {
			return Items.GOLDEN_SHOVEL;
		} else if (item instanceof PickaxeItem) {
			return Items.GOLDEN_PICKAXE;
		} else if (item instanceof AxeItem) {
			return Items.GOLDEN_AXE;
		} else if (item instanceof HoeItem) {
			return Items.GOLDEN_HOE;
		} else if (item == Items.APPLE) {
			return Items.GOLDEN_APPLE;
		} else if (item == Items.CARROT) {
			return Items.GOLDEN_CARROT;
		} else if (item == Items.MELON_SLICE) {
			return Items.GLISTERING_MELON_SLICE;
		} else if (item == Items.RAW_COPPER_BLOCK || item == Items.RAW_IRON_BLOCK) {
			return Items.RAW_GOLD_BLOCK;
		} else if (item == Items.RAW_COPPER || item == Items.RAW_IRON) {
			return Items.RAW_GOLD;
		} else if (item == Items.IRON_NUGGET) {
			return Items.GOLD_NUGGET;
		} else if (item instanceof HorseArmorItem) {
			return Items.GOLDEN_HORSE_ARMOR;
		} else if (item instanceof BlockItem) {
			return Items.GOLD_BLOCK;
		} else if (item instanceof ArmorItem armor) {
			if (armor.getType() == ArmorItem.Type.HELMET) {
				return Items.GOLDEN_HELMET;
			} else if (armor.getType() == ArmorItem.Type.CHESTPLATE) {
				return Items.GOLDEN_CHESTPLATE;
			} else if (armor.getType() == ArmorItem.Type.LEGGINGS) {
				return Items.GOLDEN_LEGGINGS;
			}
			return Items.GOLDEN_BOOTS;
		}
		return Items.GOLD_INGOT;
	}
}
