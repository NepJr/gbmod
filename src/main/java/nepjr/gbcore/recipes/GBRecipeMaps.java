package nepjr.gbcore.recipes;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.FuelRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

public class GBRecipeMaps 
{
	//@ZenProperty
    //public static final RecipeMap<SimpleRecipeBuilder> BURNER_GENERATOR = new RecipeMap<>("burner_generator", 1, 0, 0, 0,
    //        new SimpleRecipeBuilder(), false)
    //                .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, MoveType.HORIZONTAL)
    //                .setSound(GTSoundEvents.FURNACE);
	
	public static final RecipeMap<FuelRecipeBuilder> BURNER_GENERATOR = new RecipeMap<>(
            "burner_generator", 1, 0, 0, 0, new FuelRecipeBuilder(), false)
                    .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
                    .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, MoveType.HORIZONTAL)
                    .setSound(GTSoundEvents.FURNACE)
                    .allowEmptyOutput();
}
