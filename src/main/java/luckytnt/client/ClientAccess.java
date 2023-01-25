package luckytnt.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class ClientAccess {
	
	public static void setEntityStringTag(String name, String tag, int id) {
		Minecraft minecraft = Minecraft.getInstance();
		Entity ent = minecraft.level.getEntity(id);
		if(ent != null) {
			ent.getPersistentData().putString(name, tag);
		}
	}
	
	public static void setEntityIntTag(String name, int tag, int id) {
		Minecraft minecraft = Minecraft.getInstance();
		Entity ent = minecraft.level.getEntity(id);
		if(ent != null) {
			ent.getPersistentData().putInt(name, tag);
		}
	}
}
