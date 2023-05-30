package luckytnt;

import java.util.HashMap;
import java.util.function.BiFunction;

import luckytnt.client.gui.ConfigScreen;
import luckytnt.config.LuckyTNTConfigs;
import luckytnt.network.PacketHandler;
import luckytnt.registry.LuckyTNTTabs;
import luckytnt.registry.SoundRegistry;
import luckytntlib.registry.RegistryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(LuckyTNTMod.MODID)
public class LuckyTNTMod
{
    public static final String MODID = "luckytntmod";
    public static final DeferredRegister<Block> blockRegistry = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> blockEntityRegistry = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<Item> itemRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<EntityType<?>> entityRegistry = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final DeferredRegister<MobEffect> effectRegistry = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
    public static final DeferredRegister<Feature<?>> featureRegistry = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
    @SuppressWarnings("serial")
	private static final HashMap<String, CreativeModeTab> tabs = new HashMap<>(){{
    	put("n", LuckyTNTTabs.NORMAL_TNT);
    	put("g", LuckyTNTTabs.GOD_TNT);
    	put("dy", LuckyTNTTabs.DYNAMITE);
    	put("d", LuckyTNTTabs.DOOMSDAY_TNT);
    	put("m", LuckyTNTTabs.MINECART);
    }};
    public static final RegistryHelper RH = new RegistryHelper(blockRegistry, itemRegistry, entityRegistry, tabs);
    
    public LuckyTNTMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	LuckyTNTTabs.load();
        SoundRegistry.SOUNDS.register(bus);
    	entityRegistry.register(bus);
    	blockEntityRegistry.register(bus);
    	blockRegistry.register(bus);
    	itemRegistry.register(bus);
    	effectRegistry.register(bus);
    	featureRegistry.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        LuckyTNTConfigs.register();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(new BiFunction<Minecraft, Screen, Screen>() {		
			@Override
			public Screen apply(Minecraft mc, Screen screen) {
				return new ConfigScreen();
			}
		}));
    }
    
    private void setup(final FMLCommonSetupEvent event)
    {
    	PacketHandler.register();
    }
}
