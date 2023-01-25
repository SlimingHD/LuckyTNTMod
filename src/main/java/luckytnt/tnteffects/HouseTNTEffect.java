package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytnt.LuckyTNTMod;
import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.RegistryObject;

public class HouseTNTEffect extends PrimedTNTEffect{

	private final Supplier<RegistryObject<LTNTBlock>> TNT;
	private final String house;
	
	public HouseTNTEffect(Supplier<RegistryObject<LTNTBlock>> TNT, String house) {
		this.TNT = TNT;
		this.house = house;
	}
	
	@Override
	public Block getBlock() {
		return TNT.get().get();
	}

	@SuppressWarnings("resource")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		StructureTemplate template = ((ServerLevel)entity.level()).getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, house));
		if(template != null) {
			template.placeInWorld((ServerLevel)entity.level(), new BlockPos(entity.getPos()).offset(-5, 0, -3), new BlockPos(entity.getPos()).offset(-5, 0, -3), new StructurePlaceSettings(), entity.level().random, 3);
		}
	}
}
