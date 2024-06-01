package nepjr.gbcore.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBasic extends Block
{

	public BlockBasic(Material materialIn, String name, SoundType sound) 
	{
		super(materialIn);
		this.setSoundType(sound);
		this.setRegistryName(name);
		this.setTranslationKey(name);
	}

}
