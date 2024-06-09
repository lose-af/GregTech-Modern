package com.gregtechceu.gtceu.api.gui.editor;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.Platform;
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.editor.Icons;
import com.lowdragmc.lowdraglib.gui.editor.annotation.LDLRegister;
import com.lowdragmc.lowdraglib.gui.editor.configurator.IConfigurableWidget;
import com.lowdragmc.lowdraglib.gui.editor.data.Resources;
import com.lowdragmc.lowdraglib.gui.editor.data.UIProject;
import com.lowdragmc.lowdraglib.gui.editor.ui.Editor;
import com.lowdragmc.lowdraglib.gui.editor.ui.tool.WidgetToolBox;
import com.lowdragmc.lowdraglib.gui.texture.*;
import com.lowdragmc.lowdraglib.gui.util.TreeBuilder;
import com.lowdragmc.lowdraglib.gui.widget.TabButton;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KilaBash
 * @date 2023/3/29
 * @implNote RecipeTypeUIProject
 */
@LDLRegister(name = "rtui", group = "editor.gtceu")
public class RecipeTypeUIProject extends UIProject {

    @Nullable
    @Getter
    @Setter
    protected GTRecipeType recipeType;

    private RecipeTypeUIProject() {
        this(null, null);
    }

    public RecipeTypeUIProject(Resources resources, WidgetGroup root) {
        super(resources, root);
    }

    public RecipeTypeUIProject(CompoundTag tag) {
        super(tag);
    }

    @Override
    public RecipeTypeUIProject newEmptyProject() {
        return new RecipeTypeUIProject(Resources.defaultResource(), new WidgetGroup(30, 30, 200, 200));
    }

    @Override
    public UIProject loadProject(Path file) {
        try {
            var tag = NbtIo.read(file);
            if (tag != null) {
                return new RecipeTypeUIProject(tag);
            }
        } catch (IOException ignored) {}
        return null;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var tag = super.serializeNBT(provider);
        if (recipeType != null) {
            tag.putString("recipe_type", recipeType.registryName.toString());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag, HolderLookup.Provider provider) {
        super.deserializeNBT(tag, provider);
        if (tag.contains("recipe_type")) {
            recipeType = GTRegistries.RECIPE_TYPES.get(ResourceLocation.parse(tag.getString("recipe_type")));
        }
    }

    @Override
    public void onLoad(Editor editor) {
        editor.getResourcePanel().loadResource(getResources(), false);
        editor.getTabPages().addTab(new TabButton(50, 16, 60, 14).setTexture(
                new GuiTextureGroup(ColorPattern.T_GREEN.rectTexture().setBottomRadius(10).transform(0, 0.4f),
                        new TextTexture("Main")),
                new GuiTextureGroup(ColorPattern.T_RED.rectTexture().setBottomRadius(10).transform(0, 0.4f),
                        new TextTexture("Main"))),
                new UIMainPanel(editor, root, recipeType == null ? null : recipeType.getTranslationKey()));
        for (WidgetToolBox.Default tab : WidgetToolBox.Default.TABS) {
            editor.getToolPanel().addNewToolBox("ldlib.gui.editor.group." + tab.groupName, tab.icon,
                    tab.createToolBox());
        }
    }

    @Override
    public void attachMenu(Editor editor, String name, TreeBuilder.Menu menu) {
        if (name.equals("file")) {
            if (recipeType == null) {
                menu.remove("ldlib.gui.editor.menu.save");
            } else {
                menu.remove("ldlib.gui.editor.menu.save");
                menu.leaf(Icons.SAVE, "ldlib.gui.editor.menu.save", () -> {
                    var path = new File(LDLib.getLDLibDir(),
                            "assets/%s/ui/recipe_type".formatted(recipeType.registryName.getNamespace()));
                    path.mkdirs();
                    saveProject(Path.of(recipeType.registryName.getPath(), ".", this.getRegisterUI().name()));
                    recipeType.getRecipeUI().reloadCustomUI();
                });
            }
        } else if (name.equals("template_tab")) {
            Map<String, List<GTRecipeType>> categories = new LinkedHashMap<>();
            for (GTRecipeType recipeType : GTRegistries.RECIPE_TYPES) {
                categories.computeIfAbsent(recipeType.group, group -> new ArrayList<>()).add(recipeType);
            }
            categories.forEach((groupName, recipeTypes) -> menu.branch(groupName, m -> {
                for (GTRecipeType recipeType : recipeTypes) {
                    IGuiTexture icon;
                    if (recipeType.getIconSupplier() != null) {
                        icon = new ItemStackTexture(recipeType.getIconSupplier().get());
                    } else {
                        icon = new ItemStackTexture(Items.BARRIER);
                    }
                    m.leaf(icon, recipeType.getTranslationKey(), () -> {
                        root.clearAllWidgets();
                        if (recipeType.getRecipeUI().hasCustomUI()) {
                            var nbt = recipeType.getRecipeUI().getCustomUI();
                            IConfigurableWidget.deserializeNBT(root, nbt.getCompound("root"),
                                    Resources.fromNBT(nbt.getCompound("resources")), false,
                                    Platform.getFrozenRegistry());
                        } else {
                            var widget = recipeType.getRecipeUI().createEditableUITemplate(false, false)
                                    .createDefault();
                            root.setSize(widget.getSize());
                            for (Widget children : widget.widgets) {
                                root.addWidget(children);
                            }
                        }
                        setRecipeType(recipeType);
                    });
                }
            }));
        }
    }
}
