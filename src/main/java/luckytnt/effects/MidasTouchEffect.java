package luckytnt.effects;

import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MidasTouchEffect extends MobEffect {

	public MidasTouchEffect(MobEffectCategory category, int color) {
		super(category, color);		
	}
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("effect.midas_touch");
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
			ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(server);

			BlockHitResult result = level.clip(new ClipContext(entity.getPosition(1), entity.getPosition(1).add(0, -1, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if (result != null) {
				BlockState state = level.getBlockState(result.getBlockPos());
				if (state.getExplosionResistance(level, result.getBlockPos(), dummy) < 100 && !state.isAir()) {
					level.setBlock(result.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
				}
			}
			
			result = level.clip(new ClipContext(entity.getPosition(1).add(0, entity.getEyeHeight(), 0), entity.getPosition(1).add(0, entity.getEyeHeight(), 0).add(entity.getViewVector(1).scale(5)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if (result != null) {
				BlockState state = level.getBlockState(result.getBlockPos());
				if (state.getExplosionResistance(level, result.getBlockPos(), dummy) < 100 && !state.isAir()) {
					level.setBlock(result.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
				}
			}
			
			makeItemsGolden(entity, InteractionHand.MAIN_HAND);
			makeItemsGolden(entity, InteractionHand.OFF_HAND);
			if (entity.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_HELMET) {
				entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
			}
			if (entity.getItemBySlot(EquipmentSlot.CHEST) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_CHESTPLATE) {
				entity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
			}
			if (entity.getItemBySlot(EquipmentSlot.LEGS) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_LEGGINGS) {
				entity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
			}
			if (entity.getItemBySlot(EquipmentSlot.FEET) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_BOOTS) {
				entity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
			}
		}
	}
	
	private static void makeItemsGolden(LivingEntity ent, InteractionHand hand) {
		ItemStack stack = ent.getItemInHand(hand);
		Item item = stack.getItem();
		
		if (item instanceof SwordItem && item != Items.GOLDEN_SWORD) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_SWORD));
		} else if (item instanceof ShovelItem && item != Items.GOLDEN_SHOVEL) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_SHOVEL));
		} else if (item instanceof PickaxeItem && item != Items.GOLDEN_PICKAXE) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_PICKAXE));
		} else if (item instanceof AxeItem && item != Items.GOLDEN_AXE) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_AXE));
		} else if (item instanceof HoeItem && item != Items.GOLDEN_HOE) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_HOE));
		} else if (item == Items.APPLE) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_APPLE, stack.getCount()));
		} else if (item == Items.CARROT) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLDEN_CARROT, stack.getCount()));
		} else if (item == Items.MELON_SLICE) {
			ent.setItemInHand(hand, new ItemStack(Items.GLISTERING_MELON_SLICE, stack.getCount()));
		} else if (item instanceof BlockItem && item != Items.GOLD_BLOCK) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLD_BLOCK, stack.getCount()));
		} else if (!(item instanceof BlockItem) && !(item instanceof TieredItem) && item != Items.GOLDEN_APPLE && item != Items.GOLDEN_CARROT && item != Items.GLISTERING_MELON_SLICE) {
			ent.setItemInHand(hand, new ItemStack(Items.GOLD_INGOT, stack.getCount()));
		}
	}
}
