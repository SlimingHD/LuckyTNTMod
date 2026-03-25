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
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

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
			placeGoldBlocks(server, entity.getPosition(1), entity.getPosition(1).add(0, -1, 0), entity);
			placeGoldBlocks(server, entity.getPosition(1).add(0, entity.getEyeHeight(), 0), entity.getPosition(1).add(0, entity.getEyeHeight(), 0).add(entity.getViewVector(1).scale(5)), entity);
			
			makeItemsGolden(entity, InteractionHand.MAIN_HAND);
			makeItemsGolden(entity, InteractionHand.OFF_HAND);
			
			replaceArmor(entity, EquipmentSlot.HEAD, Items.GOLDEN_HELMET);
			replaceArmor(entity, EquipmentSlot.CHEST, Items.GOLDEN_CHESTPLATE);
			replaceArmor(entity, EquipmentSlot.LEGS, Items.GOLDEN_LEGGINGS);
			replaceArmor(entity, EquipmentSlot.FEET, Items.GOLDEN_BOOTS);
		}
	}
	
	private static void placeGoldBlocks(ServerLevel level, Vec3 raytraceFrom, Vec3 raytraceTo, LivingEntity ent) {
		BlockHitResult result = level.clip(new ClipContext(raytraceFrom, raytraceTo, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, ent));
		if (result != null && result.getType() != Type.MISS) {
			BlockState state = level.getBlockState(result.getBlockPos());
			if (state.getExplosionResistance(level, result.getBlockPos(), ImprovedExplosion.dummyExplosion(level)) < 100 && !state.isAir()) {
				level.setBlock(result.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
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
	
	private static void replaceArmor(LivingEntity ent, EquipmentSlot slot, Item item) {
		if (ent.getItemBySlot(slot) != ItemStack.EMPTY && ent.getItemBySlot(slot).getItem() != item) {
			ent.setItemSlot(slot, new ItemStack(item));
		}
	}
}
