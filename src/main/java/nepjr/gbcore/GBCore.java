package nepjr.gbcore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nepjr.gbcore.GBTags;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GBTags.MODID, 
	 version = GBTags.VERSION,
	 name = GBTags.MODNAME,
	 acceptedMinecraftVersions = "[1.12.2]",
	 dependencies = "required-after:gregtech@[2.8,);" )
public class GBCore 
{
	public static Logger LOGGER = LogManager.getLogger(GBTags.VERSION);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
}
