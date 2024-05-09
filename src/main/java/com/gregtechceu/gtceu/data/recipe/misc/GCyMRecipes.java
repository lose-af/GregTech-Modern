package com.gregtechceu.gtceu.data.recipe.misc;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.AlloyBlastProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GCyMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_TEMPERED_GLASS;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MIXER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CustomTags.*;

public class GCyMRecipes {

    private GCyMRecipes() {}

    public static void init(RecipeOutput provider) {
        registerManualRecipes(provider);
        registerMachineRecipes(provider);
    }

    private static void registerManualRecipes(RecipeOutput provider) {
        registerPartsRecipes(provider);
        registerMultiblockControllerRecipes(provider);

        // Recipes for graphene that don't get autogenerated
        GTRecipeTypes.EXTRUDER_RECIPES.recipeBuilder("graphene_to_foil")
                .inputItems(dust, Graphene)
                .notConsumable(GTItems.SHAPE_EXTRUDER_FOIL)
                .outputItems(foil, Graphene, 4)
                .duration((int) Graphene.getMass())
                .EUt(24)
                .save(provider);
    }

    private static void registerMultiblockControllerRecipes(RecipeOutput provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_macerator", LARGE_MACERATION_TOWER.asStack(), "PCP", "BXB", "MKM", 'C', CustomTags.IV_CIRCUITS, 'P', new UnificationEntry(plate, TungstenCarbide), 'B', ELECTRIC_PISTON_IV.asStack(), 'M', ELECTRIC_MOTOR_IV.asStack(), 'X', MACERATOR[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_arc_smelter", LARGE_ARC_SMELTER.asStack(), "KDK", "CXC", "PPP", 'C', CustomTags.IV_CIRCUITS, 'P', new UnificationEntry(plate, TantalumCarbide), 'X', GTMachines.ARC_FURNACE[IV].asStack(), 'D',new UnificationEntry(dust, Graphite) ,'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_ore_washer", LARGE_CHEMICAL_BATH.asStack(), "PGP", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'G',CASING_TEMPERED_GLASS.asStack() ,'P', ELECTRIC_PUMP_IV.asStack(), 'M', CONVEYOR_MODULE_IV.asStack(), 'X', ORE_WASHER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_sifter", LARGE_SIFTING_FUNNEL.asStack(), "PCP", "EXE", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', new UnificationEntry(plate, HSLASteel), 'E', ELECTRIC_PISTON_IV.asStack(), 'X', SIFTER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_engraver", LARGE_ENGRAVING_LASER.asStack(), "ICI", "EXE", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', new UnificationEntry(plateDouble, TantalumCarbide),'I',EMITTER_IV.asStack() ,'E', ELECTRIC_PISTON_IV.asStack(), 'X', LASER_ENGRAVER[IV].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_packer", LARGE_PACKER.asStack(), "RCR", "PXP", "KPK", 'C', CustomTags.EV_CIRCUITS, 'P', new UnificationEntry(plate, HSLASteel),'R',ROBOT_ARM_HV.asStack() ,'K', CONVEYOR_MODULE_HV.asStack(), 'X', PACKER[HV].asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_mixer", LARGE_MIXER.asStack(), "FCF", "RXR", "MKM", 'C', CustomTags.IV_CIRCUITS, 'F', ChemicalHelper.get(pipeNormalFluid, Polybenzimidazole),'R',ChemicalHelper.get(rotor, Osmiridium) ,'M', ELECTRIC_MOTOR_IV.asStack(), 'X', MIXER[IV].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_centrifuge", LARGE_CENTRIFUGE.asStack(), "SFS", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'F', ChemicalHelper.get(pipeHugeFluid, StainlessSteel),'S',ChemicalHelper.get(spring, MolybdenumDisilicide) ,'M', ELECTRIC_MOTOR_IV.asStack(), 'X', CENTRIFUGE[IV].asStack(), 'K', new UnificationEntry(TagPrefix.cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_assembler", LARGE_ASSEMBLER.asStack(), "RKR", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'R', ROBOT_ARM_IV.asStack() ,'M', CONVEYOR_MODULE_IV.asStack(), 'X', ASSEMBLER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_circuit_assembler", LARGE_CIRCUIT_ASSEMBLER.asStack(), "RKR", "CXC", "MKM", 'C', CustomTags.IV_CIRCUITS, 'R', ROBOT_ARM_IV.asStack() ,'M', CONVEYOR_MODULE_IV.asStack(), 'X', CIRCUIT_ASSEMBLER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_electrolyzer", LARGE_ELECTROLYZER.asStack(), "PCP", "WXW", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', new UnificationEntry(plate, BlackSteel) ,'W', new UnificationEntry(wireGtQuadruple, Osmium), 'X', ELECTROLYZER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_electromagnet", LARGE_ELECTROMAGNET.asStack(), "PWP", "CXC", "PKP", 'C', CustomTags.IV_CIRCUITS, 'P', new UnificationEntry(plate, BlueSteel) ,'W', new UnificationEntry(wireGtQuadruple, Osmium), 'X', ELECTROMAGNETIC_SEPARATOR[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "blast_alloy_smelter", BLAST_ALLOY_SMELTER.asStack(), "TCT", "WXW", "TCT", 'C', CustomTags.EV_CIRCUITS, 'T', new UnificationEntry(plate, TantalumCarbide) ,'W', new UnificationEntry(wireGtQuadruple, Osmium), 'X', ALLOY_SMELTER[EV].asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, true, "mega_blast_furnace", MEGA_BLAST_FURNACE.asStack(),"PCP", "FSF", "DWD", 'C', ZPM_CIRCUITS,'S', ELECTRIC_BLAST_FURNACE.asStack(), 'F', FIELD_GENERATOR_ZPM.asStack(), 'P', new UnificationEntry(spring, Naquadah), 'D', new UnificationEntry(plateDense, NaquadahAlloy), 'W', new UnificationEntry(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "mega_vacuum_freezer", MEGA_VACUUM_FREEZER.asStack(),  "PCP", "FSF", "DWD", 'C', ZPM_CIRCUITS, 'S', VACUUM_FREEZER.asStack(), 'F', FIELD_GENERATOR_ZPM.asStack(), 'P', new UnificationEntry(pipeNormalFluid, NiobiumTitanium), 'D', new UnificationEntry(plateDense, RhodiumPlatedPalladium), 'W', new UnificationEntry(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_autoclave", LARGE_AUTOCLAVE.asStack(),  "PCP", "PAP", "BKB", 'C', CustomTags.IV_CIRCUITS, 'A', AUTOCLAVE[IV].asStack(), 'P', new UnificationEntry(plateDouble, HSLASteel), 'B', ELECTRIC_PUMP_IV.asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_material_press", LARGE_MATERIAL_PRESS.asStack(),  "PKP", "BZG", "FKH", 'Z', CustomTags.IV_CIRCUITS, 'B', BENDER[IV].asStack(), 'P', ELECTRIC_PISTON_IV.asStack(), 'G', COMPRESSOR[IV].asStack(), 'F', FORMING_PRESS[IV].asStack(), 'H', FORGE_HAMMER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_brewer", LARGE_BREWER.asStack(),  "SZS", "FBH", "EKE", 'Z', CustomTags.IV_CIRCUITS, 'S', new UnificationEntry(spring, MolybdenumDisilicide), 'F', FERMENTER[IV].asStack(), 'E', ELECTRIC_PUMP_IV.asStack(), 'B', BREWERY[IV].asStack(), 'H', FLUID_HEATER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_cutter", LARGE_CUTTER.asStack(),  "SMS", "CZL", "EKE", 'Z', CustomTags.IV_CIRCUITS, 'L', LATHE[IV].asStack(), 'E', ELECTRIC_MOTOR_IV.asStack(), 'C', CUTTER[IV].asStack(), 'M', CONVEYOR_MODULE_IV.asStack(), 'S', new UnificationEntry(toolHeadBuzzSaw, TungstenCarbide), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_distillery", LARGE_DISTILLERY.asStack(),  "PZP", "EDE", "PZP", 'Z', CustomTags.IV_CIRCUITS, 'D', DISTILLATION_TOWER.asStack(), 'E', ELECTRIC_PUMP_IV.asStack(), 'P', ChemicalHelper.get(pipeLargeFluid, Iridium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_extractor", LARGE_EXTRACTOR.asStack(),  "PTP", "EZC", "BKB", 'Z', CustomTags.IV_CIRCUITS, 'B', ELECTRIC_PISTON_IV.asStack(), 'P', ELECTRIC_PUMP_IV.asStack(), 'E', EXTRACTOR[IV].asStack(), 'C', CANNER[IV].asStack(), 'T', CASING_TEMPERED_GLASS.asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_extruder", LARGE_EXTRUDER.asStack(),  "PZP", "SES", "PKP", 'Z', CustomTags.IV_CIRCUITS, 'E', EXTRUDER[IV].asStack(), 'P', ELECTRIC_PISTON_IV.asStack(), 'S', new UnificationEntry(spring, MolybdenumDisilicide), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_solidifier", LARGE_SOLIDIFIER.asStack(),  "PZP", "ESE", "PKP", 'Z', CustomTags.IV_CIRCUITS, 'S', FLUID_SOLIDIFIER[IV].asStack(), 'E', ELECTRIC_PUMP_IV.asStack(), 'P', ChemicalHelper.get(pipeNormalFluid, Polyethylene), 'K', new UnificationEntry(cableGtSingle, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "large_wiremill", LARGE_WIREMILL.asStack(),  "PZP", "SWS", "MKM", 'Z', CustomTags.IV_CIRCUITS, 'W', WIREMILL[IV].asStack(), 'P', new UnificationEntry(plate, HSLASteel), 'S', new UnificationEntry(spring, HSLASteel), 'M', ELECTRIC_MOTOR_IV.asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));
    }

    private static void registerPartsRecipes(RecipeOutput provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, "crushing_wheels", CRUSHING_WHEELS.asStack(2), "TTT", "UCU","UMU", 'T', new UnificationEntry(gearSmall,TungstenCarbide), 'U', ChemicalHelper.get(gear, Ultimet), 'C', CASING_SECURE_MACERATION.asStack(), 'M', ELECTRIC_MOTOR_IV.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "slicing_blades", SLICING_BLADES.asStack(2), "PPP", "UCU","UMU", 'P', new UnificationEntry(plate,TungstenCarbide), 'U', ChemicalHelper.get(gear, Ultimet), 'C', CASING_SHOCK_PROOF.asStack(), 'M', ELECTRIC_MOTOR_IV.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, "electrolytic_cell", ELECTROLYTIC_CELL.asStack(2), "WWW", "WCW","ZKZ", 'W', new UnificationEntry(wireGtDouble,Platinum), 'Z', CustomTags.IV_CIRCUITS, 'C', CASING_NONCONDUCTING.asStack(), 'K', ChemicalHelper.get(cableGtSingle,Tungsten));
        VanillaRecipeHelper.addShapedRecipe(provider, "heat_vent", HEAT_VENT.asStack(2), "PDP", "RLR","PDP", 'P', new UnificationEntry(plate,TantalumCarbide), 'D', ChemicalHelper.get(plateDouble,MolybdenumDisilicide), 'R', ChemicalHelper.get(rotor,Titanium), 'L', ChemicalHelper.get(rodLong,MolybdenumDisilicide));
        VanillaRecipeHelper.addShapedRecipe(provider, "parallel_hatch_mk1", PARALLEL_HATCH[IV].asStack(1), "SZE", "ZHZ","CZC", 'S', SENSOR_IV.asStack(), 'E', EMITTER_IV.asStack(), 'Z', LuV_CIRCUITS, 'H', HULL[IV].asStack(), 'C', new UnificationEntry(cableGtDouble, Platinum));
        VanillaRecipeHelper.addShapedRecipe(provider, "parallel_hatch_mk2", PARALLEL_HATCH[LuV].asStack(1), "SZE", "ZHZ","CZC", 'S', SENSOR_LuV.asStack(), 'E', EMITTER_LuV.asStack(), 'Z', ZPM_CIRCUITS, 'H', HULL[LuV].asStack(), 'C', new UnificationEntry(cableGtDouble, NiobiumTitanium));
        VanillaRecipeHelper.addShapedRecipe(provider, "parallel_hatch_mk3", PARALLEL_HATCH[ZPM].asStack(1), "SZE", "ZHZ","CZC", 'S', SENSOR_ZPM.asStack(), 'E', EMITTER_ZPM.asStack(), 'Z', UV_CIRCUITS, 'H', HULL[ZPM].asStack(), 'C', new UnificationEntry(cableGtDouble, VanadiumGallium));
        VanillaRecipeHelper.addShapedRecipe(provider, "parallel_hatch_mk4", PARALLEL_HATCH[UV].asStack(1), "SZE", "ZHZ","CZC", 'S', SENSOR_UV.asStack(), 'E', EMITTER_UV.asStack(), 'Z', UHV_CIRCUITS, 'H', HULL[UV].asStack(), 'C', new UnificationEntry(cableGtDouble, YttriumBariumCuprate));
    }

    private static void registerMachineRecipes(RecipeOutput provider) {
        registerAssemblerRecipes(provider);
        registerMixerRecipes(provider);
        registerBlastAlloyRecipes(provider);
    }

    private static void registerAssemblerRecipes(RecipeOutput provider){
        ASSEMBLER_RECIPES.recipeBuilder("crushing_wheels")
                .inputItems(ChemicalHelper.get(gearSmall, TungstenCarbide, 2))
                .inputItems(ChemicalHelper.get(gear, Ultimet, 3))
                .inputItems(CASING_SECURE_MACERATION.asStack())
                .inputItems(ELECTRIC_MOTOR_IV.asStack())
                .outputItems(CRUSHING_WHEELS.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("slicing_blades")
                .inputItems(ChemicalHelper.get(plate, TungstenCarbide, 2))
                .inputItems(ChemicalHelper.get(gear, Ultimet, 3))
                .inputItems(CASING_SHOCK_PROOF.asStack())
                .inputItems(ELECTRIC_MOTOR_IV.asStack())
                .outputItems(SLICING_BLADES.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("electrolytic_cell")
                .inputItems(ChemicalHelper.get(wireGtDouble, Platinum, 4))
                .inputItems(ChemicalHelper.get(cableGtSingle, Tungsten, 1))
                .inputItems(CASING_NONCONDUCTING.asStack())
                .inputItems(CustomTags.IV_CIRCUITS)
                .outputItems(ELECTROLYTIC_CELL.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("mds_coil_block")
                .inputItems(ChemicalHelper.get(ring, MolybdenumDisilicide, 32))
                .inputItems(ChemicalHelper.get(foil, Graphene, 16))
                .inputFluids(HSLASteel.getFluid(144))
                .outputItems(MOLYBDENUM_DISILICIDE_COIL_BLOCK.asStack(1))
                .duration(500).EUt(1920)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("heat_vent")
                .inputItems(ChemicalHelper.get(plate, TantalumCarbide, 3))
                .inputItems(ChemicalHelper.get(plateDouble, MolybdenumDisilicide, 2))
                .inputItems(ChemicalHelper.get(rotor, Titanium, 1))
                .inputItems(ChemicalHelper.get(rodLong, MolybdenumDisilicide, 1))
                .outputItems(HEAT_VENT.asStack(2))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_hsla_nonconducting")
                .inputItems(plate, HSLASteel, 6).inputItems(frameGt, HSLASteel).circuitMeta(6)
                .outputItems(CASING_NONCONDUCTING.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_incoloy_vibration_safe")
                .inputItems(plate, IncoloyMA956, 6).inputItems(frameGt, IncoloyMA956).circuitMeta(6)
                .outputItems(CASING_VIBRATION_SAFE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .EUt(16).duration(50)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_watertight")
                .inputItems(plate, WatertightSteel, 6).inputItems(frameGt, WatertightSteel).circuitMeta(6)
                .outputItems(CASING_WATERTIGHT.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_secure_maceration")
                .inputItems(plate, Zeron100, 6).inputItems(frameGt, Titanium).circuitMeta(6)
                .outputItems(CASING_SECURE_MACERATION.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .EUt(16).duration(50)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_high_temperature_smelting")
                .inputItems(plate, TitaniumCarbide, 4).inputItems(plate, HSLASteel, 2)
                .inputItems(frameGt, TungstenCarbide).circuitMeta(6)
                .outputItems(CASING_HIGH_TEMPERATURE_SMELTING.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_reaction_safe_mixing")
                .inputItems(TagPrefix.plate, GTMaterials.HastelloyX, 6)
                .inputItems(TagPrefix.frameGt, GTMaterials.MaragingSteel300).circuitMeta(6)
                .outputItems(CASING_REACTION_SAFE.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_laser_safe_engraving")
                .inputItems(plate, TitaniumTungstenCarbide, 6).inputItems(frameGt, Titanium).circuitMeta(6)
                .outputItems(CASING_LASER_SAFE_ENGRAVING.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_large_scale_assembling")
                .inputItems(plate, Stellite100, 6).inputItems(frameGt, Tungsten).circuitMeta(6)
                .outputItems(CASING_LARGE_SCALE_ASSEMBLING.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_shock_proof")
                .inputItems(plate, HastelloyC276, 6).inputItems(frameGt, HastelloyC276).circuitMeta(6)
                .outputItems(CASING_SHOCK_PROOF.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_corrosion_proof")
                .inputItems(plate, CobaltBrass, 6).inputItems(frameGt, HSLASteel).circuitMeta(6)
                .outputItems(CASING_CORROSION_PROOF.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("casing_stress_proof")
                .inputItems(plate, MaragingSteel300, 6).inputItems(frameGt, StainlessSteel).circuitMeta(6)
                .outputItems(CASING_STRESS_PROOF.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .duration(50).EUt(16)
                .save(provider);
    }

    private static void registerMixerRecipes(RecipeOutput provider){
        MIXER_RECIPES.recipeBuilder("tantalum_carbide")
                .inputItems(dust, Tantalum)
                .inputItems(dust, Carbon)
                .outputItems(dust, TantalumCarbide, 2)
                .duration(150).EUt(VA[IV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("hsla_steel")
                .inputItems(dust, Invar, 2)
                .inputItems(dust, Vanadium)
                .inputItems(dust, Titanium)
                .inputItems(dust, Molybdenum)
                .outputItems(dust, HSLASteel, 5)
                .duration(140).EUt(VA[HV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("molybdenum_disilicide")
                .inputItems(dust, Molybdenum)
                .inputItems(dust, Silicon, 2)
                .outputItems(dust, MolybdenumDisilicide, 3)
                .duration(180).EUt(VA[EV])
                .save(provider);

        MIXER_RECIPES.recipeBuilder("titanium_carbide")
                .inputItems(dust, Titanium)
                .inputItems(dust, Carbon)
                .outputItems(dust, TitaniumCarbide, 2)
                .duration(160).EUt(VA[EV])
                .save(provider);
    }

    private static void registerFormulaic(RecipeOutput provider) {
        registerBinaryAlloy(GTMaterials.Copper, 3, GTMaterials.Tin, 1,
                GTMaterials.Bronze, 4, 400, provider);
        registerBinaryAlloy(GTMaterials.Copper, 3, GTMaterials.Zinc, 1,
                GTMaterials.Brass, 4, 400, provider);
        registerBinaryAlloy(GTMaterials.Copper, 1, GTMaterials.Nickel, 1,
                GTMaterials.Cupronickel, 2, 200, provider);
        registerBinaryAlloy(GTMaterials.Copper, 1, GTMaterials.Redstone, 4,
                GTMaterials.RedAlloy, 1, 100, provider);

        registerBinaryAlloy(GTMaterials.Iron, 1, GTMaterials.Tin, 1,
                GTMaterials.TinAlloy, 2, 100, provider);
        registerBinaryAlloy(GTMaterials.Iron, 2, GTMaterials.Nickel, 1,
                GTMaterials.Invar, 3, 300, provider);
        registerBinaryAlloy(GTMaterials.Lead, 4, GTMaterials.Antimony, 1,
                GTMaterials.BatteryAlloy, 5, 250, provider);
        registerBinaryAlloy(GTMaterials.Gold, 1, GTMaterials.Silver, 1,
                GTMaterials.Electrum, 2, 200, provider);
        registerBinaryAlloy(GTMaterials.Magnesium, 1, GTMaterials.Aluminium, 2,
                GTMaterials.Magnalium, 3, 150, provider);
        registerBinaryAlloy(GTMaterials.Silver, 1, GTMaterials.Electrotine, 4,
                GTMaterials.BlueAlloy, 1, 100, provider);
        registerBinaryAlloy(GTMaterials.Glass, 7, GTMaterials.Boron, 1,
                GTMaterials.BorosilicateGlass, 8, 200, provider);

        registerTrinaryAlloy(GTMaterials.Brass, 7, GTMaterials.Aluminium, 1,
                GTMaterials.Cobalt, 1, GTMaterials.CobaltBrass, 9, 900, provider);
        registerTrinaryAlloy(GTMaterials.Tin, 6, GTMaterials.Lead, 3,
                GTMaterials.Antimony, 1, GTMaterials.SolderingAlloy, 10, 200, provider);
        registerTrinaryAlloy(GTMaterials.Copper, 6, GTMaterials.Tin, 2,
                GTMaterials.Lead, 1, GTMaterials.Potin, 9, 400, provider);
    }

    private static void registerManual(RecipeOutput provider) {
        // NZF
        GCyMRecipeTypes.ALLOY_BLAST_RECIPES.recipeBuilder("nickel_zinc_ferrite")
                .inputItems(TagPrefix.dust, GTMaterials.Nickel)
                .inputItems(TagPrefix.dust, GTMaterials.Zinc)
                .inputItems(TagPrefix.dust, GTMaterials.Iron, 4)
                .circuitMeta(6)
                .inputFluids(GTMaterials.Oxygen.getFluid(8000))
                .outputFluids(GTMaterials.NickelZincFerrite.getFluid(GTValues.L * 6))
                .duration(2400 * 3 / 4)
                .EUt(GTValues.VA[GTValues.MV])
                .blastFurnaceTemp(1500)
                .save(provider);
    }

    private static void registerBinaryAlloy(@NotNull Material input1, int input1Amount,
                                            @NotNull Material input2, int input2Amount,
                                            @NotNull Material output, int outputAmount,
                                            int duration,
                                            RecipeOutput provider) {
        GCyMRecipeTypes.ALLOY_BLAST_RECIPES.recipeBuilder(output.getName())
                .inputItems(TagPrefix.dust, input1, input1Amount)
                .inputItems(TagPrefix.dust, input2, input2Amount)
                .circuitMeta(input1Amount + input2Amount)
                .outputFluids(output.getFluid(GTValues.L * outputAmount))
                .duration(duration * 3 / 4)
                .EUt(16)
                .blastFurnaceTemp(FluidHelper.getTemperature(output.getFluid(1)))
                .save(provider);
    }

    @SuppressWarnings("SameParameterValue")
    private static void registerTrinaryAlloy(@NotNull Material input1, int input1Amount,
                                             @NotNull Material input2, int input2Amount,
                                             @NotNull Material input3, int input3Amount,
                                             @NotNull Material output, int outputAmount,
                                             int duration,
                                             RecipeOutput provider) {
        GCyMRecipeTypes.ALLOY_BLAST_RECIPES.recipeBuilder(output.getName())
                .inputItems(TagPrefix.dust, input1, input1Amount)
                .inputItems(TagPrefix.dust, input2, input2Amount)
                .inputItems(TagPrefix.dust, input3, input3Amount)
                .circuitMeta(input1Amount + input2Amount + input3Amount)
                .outputFluids(output.getFluid(GTValues.L * outputAmount))
                .duration(duration * 3 / 4)
                .EUt(16)
                .blastFurnaceTemp(FluidHelper.getTemperature(output.getFluid(1)))
                .save(provider);
    }

    private static void registerBlastAlloyRecipes(RecipeOutput provider) {
        registerFormulaic(provider);
        registerManual(provider);
        ingot.executeHandler(provider, PropertyKey.ALLOY_BLAST, GCyMRecipes::generateAlloyBlastRecipes);
    }

    /**
     * Generates alloy blast recipes for a material
     *
     * @param material the material to generate for
     * @param property the blast property of the material
     */
    public static void generateAlloyBlastRecipes(@Nullable TagPrefix unused, @Nonnull Material material,
                                                 @Nonnull AlloyBlastProperty property,
                                                 @Nonnull RecipeOutput provider) {
        if (material.hasProperty(PropertyKey.BLAST)) {
            property.getRecipeProducer().produce(material, material.getProperty(PropertyKey.BLAST), provider);
        }
    }
}
