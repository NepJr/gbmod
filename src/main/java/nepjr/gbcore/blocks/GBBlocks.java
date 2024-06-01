package nepjr.gbcore.blocks;

import nepjr.gbcore.GBTags;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=GBTags.MODID)
public class GBBlocks 
{
	static BlockDust blockDust;
	static BlockBasic enderiumCasing;
	
	public static void init()
	{
		blockDust = new BlockDust("blockDust");
		enderiumCasing = new BlockBasic(Material.IRON, "enderium_casing", SoundType.METAL);
	}
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(blockDust);
		event.getRegistry().registerAll(enderiumCasing);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(new ItemBlock(blockDust).setRegistryName(blockDust.getRegistryName()));
		event.getRegistry().registerAll(new ItemBlock(enderiumCasing).setRegistryName(enderiumCasing.getRegistryName()));
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event)
	{
		registerRender(Item.getItemFromBlock(blockDust));
		registerRender(Item.getItemFromBlock(enderiumCasing));
	}
	
	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
