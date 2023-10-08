package luckytnt.effects;

import net.minecraft.network.chat.Component;
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

	public MidasTouchEffect(MobEffectCategory category, int id) {
		super(category, id);		
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
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		Level level = entity.level();
		if(!level.isClientSide) {

			BlockHitResult result = level.clip(new ClipContext(entity.getPosition(1), entity.getPosition(1).add(0, -1, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if(result != null) {
				BlockState state = level.getBlockState(result.getBlockPos());
				if(state.getBlock().getExplosionResistance() < 100 && !state.isAir()) {
					level.setBlock(result.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
				}
			}
			
			result = level.clip(new ClipContext(entity.getPosition(1).add(0, entity.getEyeHeight(), 0), entity.getPosition(1).add(0, entity.getEyeHeight(), 0).add(entity.getViewVector(1).scale(5)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if(result != null) {
				BlockState state = level.getBlockState(result.getBlockPos());
				if(state.getBlock().getExplosionResistance() < 100 && !state.isAir()) {
					level.setBlock(result.getBlockPos(), Blocks.GOLD_BLOCK.defaultBlockState(), 3);
				}
			}
			if(entity.getMainHandItem() != ItemStack.EMPTY) {
				Item item = entity.getMainHandItem().getItem();
				if(item instanceof SwordItem && item != Items.GOLDEN_SWORD) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));
				}
				else if(item instanceof ShovelItem && item != Items.GOLDEN_SHOVEL) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SHOVEL));
				}
				else if(item instanceof PickaxeItem && item != Items.GOLDEN_PICKAXE) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_PICKAXE));
				}
				else if(item instanceof AxeItem && item != Items.GOLDEN_AXE) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_AXE));
				}
				else if(item instanceof HoeItem && item != Items.GOLDEN_HOE) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_HOE));
				}
				else if(item == Items.APPLE) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_APPLE, entity.getMainHandItem().getCount()));					
				}
				else if(item == Items.CARROT) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_CARROT, entity.getMainHandItem().getCount()));		
				}
				else if(item == Items.MELON_SLICE) {
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GLISTERING_MELON_SLICE, entity.getMainHandItem().getCount()));					
				}
				else if(item instanceof BlockItem && item != Items.GOLD_BLOCK){
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLD_BLOCK, entity.getMainHandItem().getCount()));										
				}
				else if(!(item instanceof BlockItem) && !(item instanceof TieredItem) && item != Items.GOLDEN_APPLE && item != Items.GOLDEN_CARROT && item != Items.GLISTERING_MELON_SLICE){
					entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLD_INGOT, entity.getMainHandItem().getCount()));										
				}
			}
			if(entity.getOffhandItem() != ItemStack.EMPTY) {
				Item item = entity.getOffhandItem().getItem();
				if(item instanceof SwordItem && item != Items.GOLDEN_SWORD) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_SWORD));
				}
				else if(item instanceof ShovelItem && item != Items.GOLDEN_SHOVEL) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_SHOVEL));
				}
				else if(item instanceof PickaxeItem && item != Items.GOLDEN_PICKAXE) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_PICKAXE));
				}
				else if(item instanceof AxeItem && item != Items.GOLDEN_AXE) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_AXE));
				}
				else if(item instanceof HoeItem && item != Items.GOLDEN_HOE) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_HOE));
				}
				else if(item == Items.APPLE) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_APPLE, entity.getOffhandItem().getCount()));					
				}
				else if(item == Items.CARROT) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLDEN_CARROT, entity.getOffhandItem().getCount()));		
				}
				else if(item == Items.MELON_SLICE) {
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GLISTERING_MELON_SLICE, entity.getOffhandItem().getCount()));					
				}
				else if(item instanceof BlockItem && item != Items.GOLD_BLOCK){
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLD_BLOCK, entity.getOffhandItem().getCount()));										
				}
				else if(!(item instanceof BlockItem) && !(item instanceof TieredItem) && item != Items.GOLDEN_APPLE && item != Items.GOLDEN_CARROT && item != Items.GLISTERING_MELON_SLICE){
					entity.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.GOLD_INGOT, entity.getOffhandItem().getCount()));										
				}
			}
			if(entity.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_HELMET) {
				entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
			}
			if(entity.getItemBySlot(EquipmentSlot.CHEST) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_CHESTPLATE) {
				entity.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
			}
			if(entity.getItemBySlot(EquipmentSlot.LEGS) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_LEGGINGS) {
				entity.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
			}
			if(entity.getItemBySlot(EquipmentSlot.FEET) != ItemStack.EMPTY && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() != Items.GOLDEN_BOOTS) {
				entity.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
			}
		}
	}
}
