package luckytnt.event;

import luckytnt.LevelVariables;
import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.network.ClientboundFreezeNBTPacket;
import luckytnt.network.PacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber
public class EntityLivingEvent {

	@SubscribeEvent
	public static void playerLivingTick(TickEvent.PlayerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			Player player = event.player;
			if(player.getPersistentData().getInt("shakeTime") > 0) {
				player.getPersistentData().putInt("shakeTime", player.getPersistentData().getInt("shakeTime") - 1);
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingTick(LivingEvent.LivingTickEvent event) {
		LivingEntity ent = event.getEntity();
		if(ent != null) {
			if(ent.level() instanceof ServerLevel sLevel) {
				if(LevelVariables.get(sLevel).iceAgeTime > 0) {
					if((ent instanceof Player pl && !pl.isCreative()) || !(ent instanceof Player)) {
						if(sLevel.getBrightness(LightLayer.BLOCK, new BlockPos(Mth.floor(ent.getX()), Mth.floor(ent.getY()), Mth.floor(ent.getZ()))) < 11) {
							ent.getPersistentData().putInt("freezeTime", ent.getPersistentData().getInt("freezeTime") + LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get().intValue());
							if(ent instanceof ServerPlayer player)
								PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("freezeTime", ent.getPersistentData().getInt("freezeTime")), PacketDistributor.PLAYER.with(player));
						}
						else if(ent.getPersistentData().getInt("freezeTime") > 0){
							ent.getPersistentData().putInt("freezeTime", (int)Mth.clamp(ent.getPersistentData().getInt("freezeTime") - 0.5f * sLevel.getBrightness(LightLayer.BLOCK, new BlockPos(Mth.floor(ent.getX()), Mth.floor(ent.getY()), Mth.floor(ent.getZ()))), 0, Double.POSITIVE_INFINITY));
							if(ent instanceof ServerPlayer player)
								PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("freezeTime", ent.getPersistentData().getInt("freezeTime")), PacketDistributor.PLAYER.with(player));
						}
					}
					else {
						ent.getPersistentData().putInt("freezeTime", 0);
						if(ent instanceof ServerPlayer player)
							PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("freezeTime", ent.getPersistentData().getInt("freezeTime")), PacketDistributor.PLAYER.with(player));
					}
				} else if(ent.getPersistentData().getInt("freezeTime") > 0) {
					ent.getPersistentData().putInt("freezeTime", (int)Mth.clamp(ent.getPersistentData().getInt("freezeTime") - 10, 0, Double.POSITIVE_INFINITY));
					if(ent instanceof ServerPlayer player)
						PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("freezeTime", ent.getPersistentData().getInt("freezeTime")), PacketDistributor.PLAYER.with(player));
				}
				if(ent.getPersistentData().getInt("freezeTime") >= 600) {
					ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, ent.getPersistentData().getInt("freezeTime") / 600));
					ent.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, ent.getPersistentData().getInt("freezeTime") / 900));
				}
				if(ent.getPersistentData().getInt("freezeTime") >= 1200 && ent.getPersistentData().getInt("freezeTime") % 10 == 0) {
					DamageSources sources = new DamageSources(ent.level().registryAccess());
					ent.hurt(sources.freeze(), 1);
				}
				
				
				if(LevelVariables.get(sLevel).heatDeathTime > 0) {
					if((ent instanceof Player pl && !pl.isCreative()) || !(ent instanceof Player)) {
						if(!sLevel.getBlockState(ent.blockPosition()).is(Blocks.WATER)) {
							ent.getPersistentData().putInt("heatTime", ent.getPersistentData().getInt("heatTime") + LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get().intValue());
							if(ent instanceof ServerPlayer player)
								PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("heatTime", ent.getPersistentData().getInt("heatTime")), PacketDistributor.PLAYER.with(player));
						}
						else if(ent.getPersistentData().getInt("heatTime") > 0){
							ent.getPersistentData().putInt("heatTime", (int)Mth.clamp(ent.getPersistentData().getInt("heatTime") - 20, 0, Double.POSITIVE_INFINITY));
							if(ent instanceof ServerPlayer player)
								PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("heatTime", ent.getPersistentData().getInt("heatTime")), PacketDistributor.PLAYER.with(player));
						}
					}
					else {
						ent.getPersistentData().putInt("heatTime", 0);
						if(ent instanceof ServerPlayer player)
							PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("heatTime", ent.getPersistentData().getInt("heatTime")), PacketDistributor.PLAYER.with(player));
					}
				} else if(ent.getPersistentData().getInt("heatTime") > 0) {
					ent.getPersistentData().putInt("heatTime", (int)Mth.clamp(ent.getPersistentData().getInt("heatTime") - 20, 0, Double.POSITIVE_INFINITY));
					if(ent instanceof ServerPlayer player)
						PacketHandler.CHANNEL.send(new ClientboundFreezeNBTPacket("heatTime", ent.getPersistentData().getInt("heatTime")), PacketDistributor.PLAYER.with(player));
				}
				if(ent.getPersistentData().getInt("heatTime") >= 600) {
					ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
					ent.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
				}
				if(ent.getPersistentData().getInt("heatTime") >= 1200 && ent.getPersistentData().getInt("heatTime") % 10 == 0) {
					ent.setSecondsOnFire(ent.getPersistentData().getInt("heatTime") / 800);
					ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
					ent.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
				}
			}
		}
	}
}
