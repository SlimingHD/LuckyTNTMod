package luckytnt.registry.bootstrap;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;

public class LuckyTNTAdvancements implements AdvancementGenerator {

	@Override
	public void generate(Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
		Advancement root = Advancement.Builder.advancement()
		.display(display(Blocks.TNT, "root", new ResourceLocation("textures/block/tnt_side.png"), FrameType.TASK, false, false, false))
		.addCriterion("aquire_tnt", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.TNT))
		.save(saver, AdvancementKeys.ROOT, existingFileHelper);
		
		Advancement realEstateRookie = Advancement.Builder.advancement()
		.parent(root)
		.display(display(BlockRegistry.WOOD_HOUSE_TNT.get(), "real_estate_rookie", FrameType.TASK))
		.addCriterion("explode_any_house_tnt", new ImpossibleTrigger.TriggerInstance())
		.save(saver, AdvancementKeys.REAL_ESTATE_ROOKIE, existingFileHelper);
		
		Advancement realEstateAgent = Advancement.Builder.advancement()
		.parent(realEstateRookie)
		.display(display(BlockRegistry.MANKINDS_MARK.get(), "real_estate_agent", FrameType.TASK))
		.addCriterion("explode_mankinds_mark", new ImpossibleTrigger.TriggerInstance())
		.save(saver, AdvancementKeys.REAL_ESTATE_AGENT, existingFileHelper);
		
		Advancement.Builder.advancement()
		.parent(realEstateAgent)
		.display(display(BlockRegistry.MANSION.get(), "real_estate_master", FrameType.GOAL))
		.addCriterion("explode_mansion", new ImpossibleTrigger.TriggerInstance())
		.save(saver, AdvancementKeys.REAL_ESTATE_MASTER, existingFileHelper);
	}
	
	private static DisplayInfo display(ItemLike item, String name, FrameType frame) {
		return display(item, name, frame, true, true);
	}
	
	private static DisplayInfo display(ItemLike item, String name, FrameType frame, boolean showToast, boolean announceChat) {
		return display(item, name, frame, showToast, announceChat, false);
	}
	
	private static DisplayInfo display(ItemLike item, String name, FrameType frame, boolean showToast, boolean announceChat, boolean hide) {
		return display(item, name, null, frame, showToast, announceChat, hide);
	}
	
	private static DisplayInfo display(ItemLike item, String name, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceChat, boolean hide) {
		return new DisplayInfo(new ItemStack(item), Component.translatable("advancement.luckytntmod." + name + ".title"), Component.translatable("advancement.luckytntmod." + name + ".description"), background, frame, showToast, announceChat, hide);
	}
}
