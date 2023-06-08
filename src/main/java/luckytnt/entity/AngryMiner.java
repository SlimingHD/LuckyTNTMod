package luckytnt.entity;

import luckytnt.registry.ItemRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AngryMiner extends Monster implements RangedAttackMob{
	
	public AngryMiner(EntityType<AngryMiner> type, Level level) {
		super(type, level);
		setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.DYNAMITE.get()));
	}
	
	@Override
	public void registerGoals() {
		super.registerGoals();
		targetSelector.addGoal(0, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false, false));
		targetSelector.addGoal(2, new HurtByTargetGoal(this, Player.class).setAlertOthers(getClass()));
		goalSelector.addGoal(3, new PanicGoal(this, 1.2f));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		goalSelector.addGoal(6, new FloatGoal(this));
		goalSelector.addGoal(0, new RangedAttackGoal(this, 1.25f, 20, 10));
	}
	
	@Override
	public void performRangedAttack(LivingEntity entity, float strength) {
		double xVel = entity.getX() - getX();
		double yVel = entity.getY() + getEyeHeight() - 1.1f;
		double zVel = entity.getZ() - getZ();
		ItemRegistry.DYNAMITE.get().shoot(level(), getX(), getY() + getEyeHeight(), getZ(), new Vec3(xVel, yVel - getY() - getEyeHeight() + Math.sqrt(xVel * xVel + zVel * zVel) * 0.2f, zVel), 2, this);
	}
	
	@Override
	public void dropCustomDeathLoot(DamageSource source, int looting, boolean hit) {
		super.dropCustomDeathLoot(source, looting, hit);
		spawnAtLocation(ItemRegistry.DYNAMITE.get());
	}
		
	@Override
	public boolean removeWhenFarAway(double distance) {
		return false;
	}
	
	public static AttributeSupplier.Builder createAttributes(){
		return Mob.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3f)
			.add(Attributes.MAX_HEALTH, 40);
	}
}
