package luckytnt.tnteffects;

import org.joml.Vector3f;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec2;

public class ExtinctionEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		if(ent.getLevel() instanceof ServerLevel sLevel) {
			try {
				sLevel.getServer().getCommands().getDispatcher().execute("kill @e", new CommandSourceStack(CommandSource.NULL, ent.getPos(), Vec2.ZERO, sLevel, 4, "", Component.literal(""), ((Entity)ent).getServer(), ent.owner()));
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1), ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.EXTINCTION.get();
	}
}
