package luckytnt.item;

import java.util.List;

import javax.annotation.Nullable;

import luckytnt.registry.EntityRegistry;
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

public class VacuumCleaner extends Item {
	
	public VacuumCleaner() {
		super(new Item.Properties().stacksTo(1).durability(1000));
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		super.appendHoverText(stack, level, components, flag);
		components.add(Component.translatable("item.luckytntmod.vacuum_cleaner.info"));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide()) {
			if (!stack.getOrCreateTag().getBoolean("using")) {
				stack.getOrCreateTag().putInt("soundCooldown", 42);
				stack.getOrCreateTag().putBoolean("using", true);
				level.playSound(null, player, SoundRegistry.VACUUM_CLEANER_START.get(), SoundSource.MASTER, 2, 1);
			} else {
				stack.getOrCreateTag().putBoolean("using", false);
			}
		}
		
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int count, boolean inHand) {		
		if (!level.isClientSide()) {
			if (stack.getOrCreateTag().getBoolean("using") && inHand) {
				stack.getOrCreateTag().putInt("soundCooldown", stack.getOrCreateTag().getInt("soundCooldown") - 1);
				if (stack.getOrCreateTag().getInt("soundCooldown") == 0) {
					level.playSound(null, entity, SoundRegistry.VACUUM_CLEANER.get(), SoundSource.MASTER, 2, 1);
					stack.getOrCreateTag().putInt("soundCooldown", 22);
				}
				if (entity instanceof Player player) {
					if (!player.isCreative()) {
						stack.setDamageValue(stack.getDamageValue() + 1);
						if (stack.getDamageValue() > 1960) {
							stack.shrink(1);
						}
					}
					LExplosiveProjectile shot = EntityRegistry.VACUUM_SHOT.get().create(level);
					shot.setPos(player.getPosition(1f).add(0, player.getEyeHeight(), 0));
					shot.shoot(player.getViewVector(1).x, player.getViewVector(1).y, player.getViewVector(1).z, 4, 0);
					shot.pickup = Pickup.DISALLOWED;
					level.addFreshEntity(shot);
				}
			} else {
				stack.getOrCreateTag().putBoolean("using", false);
			}
		}
	}
}
