package nepjr.gbcore.recipes;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
public class GBRecipe 
{
	public static void init()
	{
		GBRecipeMaps.BURNER_GENERATOR.recipeBuilder()
				.input(OrePrefix.gem, Materials.Charcoal)
				.EUt(8)
				.duration(10)
				.buildAndRegister();
	}
}
