package nepjr.gbcore.mte;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.capability.impl.FuelRecipeLogic;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.WorkableTieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.PipelineUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class MTEBurnerGenerator extends WorkableTieredMetaTileEntity implements IActiveOutputSide
{
	private static final int FONT_HEIGHT = 9;
	
	public MTEBurnerGenerator(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer,
			int tier, Function<Integer, Integer> function) {
		super(metaTileEntityId, recipeMap, renderer, tier, GTUtility.defaultTankSizeFunction);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected RecipeLogicEnergy createWorkable(RecipeMap<?> recipeMap) {
        return new FuelRecipeLogic(this, recipeMap, () -> energyContainer);
    }
	
	@Override
    public boolean hasFrontFacing() {
        return true;
    }
	
	@Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (itemInventory.getSlots() > 0) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemInventory);
            }
            return null;
        }
        return super.getCapability(capability, side);
    }
	
	/*
	@Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        renderOverlays(renderState, translation, pipeline);
        Arrays.stream(EnumFacing.values()).filter(f -> f != frontFacing)
        .forEach((f -> Textures.ENERGY_OUT.renderSided(f, renderState, translation, PipelineUtil.color(pipeline, GTValues.VC[getTier()]))));
    }
	*/
	protected void renderOverlays(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        this.renderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), workable.isActive(),
                workable.isWorkingEnabled());
    }
	
	@Override
    protected void reinitializeEnergyContainer() {
        super.reinitializeEnergyContainer();
        ((EnergyContainerHandler) this.energyContainer).setSideOutputCondition(side -> side != getFrontFacing());
    }
	
	@Override
    public boolean isAutoOutputItems() {
        return false;
    }

    @Override
    public boolean isAutoOutputFluids() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideItems() {
        return false;
    }

    @Override
    public boolean isAllowInputFromOutputSideFluids() {
        return false;
    }

    @Override
    protected long getMaxInputOutputAmperage() {
        return 1L;
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    public boolean canVoidRecipeItemOutputs() {
        return !handlesRecipeOutputs;
    }

    @Override
    public boolean canVoidRecipeFluidOutputs() {
        return !handlesRecipeOutputs;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEBurnerGenerator(metaTileEntityId, workable.getRecipeMap(), renderer, getTier(),
                getTankScalingFunction());
    }

	@Override
	protected ModularUI createUI(EntityPlayer entityPlayer) {
		return createGuiTemplate(entityPlayer).build(getHolder(), entityPlayer);
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        String key = this.metaTileEntityId.getPath().split("\\.")[0];
        String mainKey = String.format("gregtech.machine.%s.tooltip", key);
        if (I18n.hasKey(mainKey)) {
            tooltip.add(1, I18n.format(mainKey));
        }
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_out", energyContainer.getOutputVoltage(),
                GTValues.VNF[getTier()]));
        tooltip.add(
                I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
        tooltip.add(
        		I18n.format("gbmod.tooltip.burner.generator"));
    }
	
	protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        RecipeMap<?> workableRecipeMap = workable.getRecipeMap();
        int yOffset = 0;
        if (workableRecipeMap.getMaxInputs() >= 6 || workableRecipeMap.getMaxFluidInputs() >= 6 ||
                workableRecipeMap.getMaxOutputs() >= 6 || workableRecipeMap.getMaxFluidOutputs() >= 6)
            yOffset = FONT_HEIGHT;

        ModularUI.Builder builder;
        if (handlesRecipeOutputs) builder = workableRecipeMap.createUITemplate(workable::getProgressPercent,
                importItems, exportItems, importFluids, exportFluids, yOffset);
        else builder = workableRecipeMap.createUITemplateNoOutputs(workable::getProgressPercent, importItems,
                exportItems, importFluids, exportFluids, yOffset);
        builder.widget(new LabelWidget(6, 6, getMetaFullName()))
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, yOffset);

        return builder;
    }
	
}
