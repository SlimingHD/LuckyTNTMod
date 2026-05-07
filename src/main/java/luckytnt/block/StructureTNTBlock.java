package luckytnt.block;

import javax.annotation.Nullable;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.ItemRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytnt.util.StructureState;
import luckytntlib.block.LTNTBlock;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class StructureTNTBlock extends LTNTBlock {

	public static final EnumProperty<StructureState> STRUCTURE = EnumProperty.create("structure", StructureState.class);
	
    public StructureTNTBlock(BlockBehaviour.Properties properties) {
        super(properties, EntityRegistry.STRUCTURE_TNT, true);
    }

    @Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> definition) {
		super.createBlockStateDefinition(definition);
		definition.add(STRUCTURE);
	}
    
    @Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.FLINT_AND_STEEL) {
			onCaughtFire(state, level, pos, result.getDirection(), player);
			level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			if (!player.isCreative()) {
				stack.hurtAndBreak(1, player, event -> event.broadcastBreakEvent(hand));
			}
			player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
			return InteractionResult.sidedSuccess(level.isClientSide());
		} else if (stack.getItem() == ItemRegistry.CONFIGURATION_WAND.get()) {
			AdvancementHelper.grantAdvancementOnePlayer(player, AdvancementKeys.MANUAL_OVERRIDE);
			level.setBlock(pos, state.setValue(STRUCTURE, state.getValue(STRUCTURE).next()), 3);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.FAIL;
	}
    
    @Nullable
	public PrimedLTNT explode(Level level, boolean exploded, double x, double y, double z, @Nullable LivingEntity igniter) throws NullPointerException {
		if (TNT != null) {
			PrimedLTNT tnt = TNT.get().create(level);
			tnt.setFuse(exploded && randomizedFuseUponExploded() ? tnt.getEffect().getDefaultFuse(tnt) / 8 + random.nextInt(Mth.clamp(tnt.getEffect().getDefaultFuse(tnt) / 4, 1, Integer.MAX_VALUE)) : tnt.getEffect().getDefaultFuse(tnt));
			tnt.setPos(x + 0.5f, y, z + 0.5f);
			tnt.setOwner(igniter);
			BlockPos pos = BlockPos.containing(new Vec3(x, y, z));
			BlockState state = level.getBlockState(pos);
			if (state.hasProperty(STRUCTURE)) {
				tnt.getPersistentData().putString("structure", state.getValue(STRUCTURE).getSerializedName());
			}
			level.addFreshEntity(tnt);
			level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.MASTER, 1, 1);
			if (state.getBlock() == this) {
				level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
			return tnt;
		}
		throw new NullPointerException("No TNT entity present. Make sure it is registered before the block is registered");
	}
}
