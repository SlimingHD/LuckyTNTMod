package luckytnt.util;

import java.util.List;

import luckytntlib.util.IExplosiveEntity;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementHelper {

	public static void grantAdvancement(IExplosiveEntity entity, ResourceLocation key) {
		if (entity.getLevel().isClientSide()) {
			return;
		}
		
		ServerLevel server = (ServerLevel)entity.getLevel();
		List<ServerPlayer> targets = entity.owner() instanceof ServerPlayer player ? List.of(player) : server.getPlayers(p -> p.distanceToSqr(entity.getPos()) <= 10000d);
		Advancement advancement = server.getServer().getAdvancements().getAdvancement(key);
		
		for (ServerPlayer target : targets) {
			AdvancementProgress progress = target.getAdvancements().getOrStartProgress(advancement);
			if (!progress.isDone()) {
				for (String criterion : progress.getRemainingCriteria()) {
					target.getAdvancements().award(advancement, criterion);
				}
			}
		}
	}
}
