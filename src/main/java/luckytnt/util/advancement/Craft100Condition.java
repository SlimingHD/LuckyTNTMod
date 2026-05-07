package luckytnt.util.advancement;

import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.LootConditionTypeRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class Craft100Condition implements LootItemCondition {

	public static final Craft100Condition INSTANCE = new Craft100Condition();
	
	private Craft100Condition() {
	}

	@Override
	public LootItemConditionType getType() {
		return LootConditionTypeRegistry.CRAFT_100;
	}
	
	@Override
	public Set<LootContextParam<?>> getReferencedContextParams() {
		return Set.of(LootContextParams.THIS_ENTITY);
	}
	
	@Override
	public boolean test(LootContext ctx) {
		return ctx.getParam(LootContextParams.THIS_ENTITY) instanceof ServerPlayer player && player.getStats().getValue(Stats.ITEM_CRAFTED.get(BlockRegistry.TNT_X100.get().asItem())) >= 100;
	}

	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<Craft100Condition> {

		@Override
		public void serialize(JsonObject root, Craft100Condition condition, JsonSerializationContext ctx) {
		}

		@Override
		public Craft100Condition deserialize(JsonObject root, JsonDeserializationContext ctx) {
			return INSTANCE;
		}
	}
}
