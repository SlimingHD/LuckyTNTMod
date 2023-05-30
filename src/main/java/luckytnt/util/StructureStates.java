package luckytnt.util;

import net.minecraft.util.StringRepresentable;

public enum StructureStates implements StringRepresentable{

	PILLAGER_OUTPOST("pillager_outpost"),
	MANSION("mansion"),
	JUNGLE_PYRAMID("jungle_pyramid"),
	DESERT_PYRAMID("desert_pyramid"),
	STRONGHOLD("stronghold"),
	MONUMENT("monument"),
	FORTRESS("fortress"),
	END_CITY("end_city"),
	BASTION("bastion"),
	VILLAGE_PLAINS("village_plains"),
	VILLAGE_DESERT("village_desert"),
	VILLAGE_SAVANNA("village_savanna"),
	VILLAGE_SNOWY("village_snowy"),
	VILLAGE_TAIGA("village_taiga");
	
	private final String name;
	
	private StructureStates(String name) {
		this.name = name;
	}
	
	public String getSerializedName() {
		return name;
	}
}
