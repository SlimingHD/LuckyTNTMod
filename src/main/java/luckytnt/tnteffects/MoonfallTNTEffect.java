package luckytnt.tnteffects;

import java.util.function.Supplier;

import luckytnt.LevelVariables;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;

public class MoonfallTNTEffect extends DropProjectileTNTEffect {

	public MoonfallTNTEffect(Supplier<RegistryObject<EntityType<LExplosiveProjectile>>> projectile) {
		super(projectile);
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			server.setDayTime(18000);
			server.getServer().forceTimeSynchronization();
			LevelVariables variables = LevelVariables.get(server);
			variables.noMoonTime = 400;
			variables.sync(server);
		}
		super.serverExplosion(entity);
	}
}
