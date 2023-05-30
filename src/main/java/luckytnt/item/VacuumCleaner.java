package luckytnt.item;

import java.util.List;

import javax.annotation.Nullable;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.LuckyTNTTabs;
import luckytnt.registry.SoundRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class VacuumCleaner extends Item{

	public int soundCooldown = 0;
	
	public VacuumCleaner() {
		super(new Item.Properties().tab(LuckyTNTTabs.OTHER).stacksTo(1).durability(1000));
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		super.appendHoverText(stack, level, components, flag);
		components.add(Component.translatable("item.vacuum_cleaner.info"));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		onUsingTick(player.getItemInHand(hand), player, player.getItemInHand(hand).getCount());
		if(!player.getItemInHand(hand).getOrCreateTag().getBoolean("using")) {
			soundCooldown = 42;
			player.getItemInHand(hand).getOrCreateTag().putBoolean("using", true);
		}
		else if(player.getItemInHand(hand).getOrCreateTag().getBoolean("using")) {
			player.getItemInHand(hand).getOrCreateTag().putBoolean("using", false);
		}
		if(player.getItemInHand(hand).getOrCreateTag().getBoolean("using"))
			level.playSound(null, player, SoundRegistry.VACUUM_CLEANER_START.get(), SoundSource.MASTER, 2, 1);
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, player.getItemInHand(hand));
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int count, boolean inHand) {		
		if(stack.getOrCreateTag().getBoolean("using") && inHand) {
			if(!level.isClientSide)
				soundCooldown--;
			if(soundCooldown == 0) {
				level.playSound(null, entity, SoundRegistry.VACUUM_CLEANER.get(), SoundSource.MASTER, 2, 1);
				if(!level.isClientSide)
					soundCooldown = 22;
			}
			if(entity instanceof Player player) {
				if(!player.isCreative()) {
					stack.setDamageValue(stack.getDamageValue() + 1);
					if(stack.getDamageValue() > 1960)
						stack.shrink(1);
				}
				LExplosiveProjectile shot = EntityRegistry.VACUUM_SHOT.get().create(level);
				shot.setPos(player.getPosition(1f).add(0, player.getEyeHeight(), 0));
				shot.shoot(player.getViewVector(1).x, player.getViewVector(1).y, player.getViewVector(1).z, 4, 0);
				shot.pickup = Pickup.DISALLOWED;
				level.addFreshEntity(shot);
			}
		}
		else
			stack.getOrCreateTag().putBoolean("using", false);
	}
}
