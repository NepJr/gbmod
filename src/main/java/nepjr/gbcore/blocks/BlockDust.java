package nepjr.gbcore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDust extends BlockFalling
{

	public BlockDust(String name)
    {
        super(Material.SAND);
        this.setRegistryName(name);
        this.setSoundType(SoundType.SAND);
        this.setTranslationKey(name);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
	
	public void onEndFalling(World worldIn, BlockPos pos, IBlockState fallingState, IBlockState hitState)
    {
        if (hitState.getMaterial().isLiquid())
        {
            worldIn.setBlockState(pos, Blocks.CLAY.getDefaultState());
        }
    }

    protected boolean tryTouchWater(World worldIn, BlockPos pos, IBlockState state)
    {
        boolean flag = false;

        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (enumfacing != EnumFacing.DOWN)
            {
                BlockPos blockpos = pos.offset(enumfacing);

                if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER)
                {
                    flag = true;
                    break;
                }
            }
        }

        if (flag)
        {
            worldIn.setBlockState(pos, Blocks.CLAY.getDefaultState());
        }

        return flag;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!this.tryTouchWater(worldIn, pos, state))
        {
            super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        }
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.tryTouchWater(worldIn, pos, state))
        {
            super.onBlockAdded(worldIn, pos, state);
        }
    }
	
}
