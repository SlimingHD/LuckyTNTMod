package luckytnt.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuckyTNTTabs {

	public static CreativeModeTab NORMAL_TNT;
	public static CreativeModeTab GOD_TNT;
	public static CreativeModeTab DOOMSDAY_TNT;
	public static CreativeModeTab DYNAMITE;
	public static CreativeModeTab MINECART;
	public static CreativeModeTab OTHER;
	
	public static void load() {
		NORMAL_TNT = new CreativeModeTab("normal_tnt") {
			
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(BlockRegistry.METEOR_TNT.get());
			}
			

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
		};
		GOD_TNT = new CreativeModeTab("god_tnt") {
			
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(BlockRegistry.GLOBAL_DISASTER.get());
			}
			

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
		};
		DOOMSDAY_TNT = new CreativeModeTab("doomsday_tnt") {
			
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(BlockRegistry.CHUNK_TNT.get());
			}
			

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
		};
		DYNAMITE = new CreativeModeTab("dynamite") {
			
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ItemRegistry.DYNAMITE.get());
			}
			

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
		};
		MINECART = new CreativeModeTab("minecart") {
			
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ItemRegistry.TNT_X5_MINECART.get());
			}
			

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
		};
		OTHER = new CreativeModeTab("other") {
			
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ItemRegistry.BLUE_CANDY.get());
			}
			

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
		};
	}
}
