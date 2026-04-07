package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytnt.LuckyTNTMod;
import luckytntlib.block.LTNTBlock;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.RegistryObject;

public class HouseTNTEffect extends PrimedTNTEffect {

	private final Supplier<RegistryObject<LTNTBlock>> TNT;
	private final String house;
	private final int offX;
	private final int offZ;
	
	public HouseTNTEffect(Supplier<RegistryObject<LTNTBlock>> TNT, String house, int offX, int offZ) {
		this.TNT = TNT;
		this.house = house;
		this.offX = offX;
		this.offZ = offZ;
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			BlockPos pos = toBlockPos(entity.getPos()).offset(offX, 0, offZ);
			StructureTemplate template = server.getStructureManager().getOrCreate(new ResourceLocation(LuckyTNTMod.MODID, house));
			template.placeInWorld(server, pos, pos, new StructurePlaceSettings(), server.getRandom(), 3);
		}
	}
	
	@Override
	public Block getBlock() {
		return TNT.get().get();
	}
}
