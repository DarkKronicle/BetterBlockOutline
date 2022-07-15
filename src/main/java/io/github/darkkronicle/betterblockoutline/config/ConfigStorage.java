package io.github.darkkronicle.betterblockoutline.config;

import com.google.common.collect.ImmutableList;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierContext;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.betterblockoutline.config.gui.colormods.ColorModifierConfig;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.Hotkeys;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.DirectionArrow;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo2dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo3dRenderer;
import io.github.darkkronicle.darkkore.config.ModConfig;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.options.*;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FileUtil;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;

import java.io.File;
import java.util.*;

public class ConfigStorage extends ModConfig {

    public static final String CONFIG_FILE_NAME = BetterBlockOutline.MOD_ID + ".json";

    @Getter
    private final Map<String, List<ConfigColorModifier<?>>> colorModifications = new HashMap<>();

    private final static ConfigStorage INSTANCE = new ConfigStorage();
    private final static General GENERAL_INSTANCE = new General();
    private final static BlockInfo2d BLOCKINFO2D_INSTANCE = new BlockInfo2d();
    private final static BlockInfo3d BLOCKINFO3D_INSTANCE = new BlockInfo3d();
    private final static BlockInfoDirectionArrow BLOCKINFOARROW_INSTANCE = new BlockInfoDirectionArrow();

    private ConfigStorage() {}

    public static ConfigStorage getInstance() {
        return INSTANCE;
    }

    public static General getGeneral() {
        return GENERAL_INSTANCE;
    }

    public static BlockInfo2d getBlockInfo2d() {
        return BLOCKINFO2D_INSTANCE;
    }

    public static BlockInfo3d getBlockInfo3d() {
        return BLOCKINFO3D_INSTANCE;
    }

    public static BlockInfoDirectionArrow getBlockInfoArrow() {
        return BLOCKINFOARROW_INSTANCE;
    }

    @Override
    public File getFile() {
        return new File(new File(FileUtil.getConfigDirectory(), "betterblockoutline"), CONFIG_FILE_NAME);
    }

    public <T extends IColorModifier> void deleteColorMod(ConfigColorModifier<T> option) {
        for (Map.Entry<String, List<ConfigColorModifier<?>>> entry : colorModifications.entrySet()) {
            if (entry.getValue().remove(option)) {
                return;
            }
        }
    }

    public static class General {

        private static String translate(String string) {
            return "betterblockoutline.config.general." + string;
        }

        public final static String NAME = "general";

        private General() {}

        @Getter
        private final BooleanOption active = new BooleanOption("active",
                translate("active"), translate("info.active"), true);

        @Getter
        private final BooleanOption alwaysShow = new BooleanOption("alwaysShow",
                translate("alwaysshow"), translate("info.alwaysshow"), false);

        @Getter
        private final BooleanOption seeThrough = new BooleanOption("seeThrough",
                translate("seethrough"), translate("info.seethrough"), false);

        @Getter
        private final ColorOption outlineColor = new ColorOption("outlineColor",
                translate("outlinecolor"),  translate("info.outlinecolor"), new Color(0, 0, 0, 255));

        @Getter
        private final ColorOption fillColor = new ColorOption("fillColor",
                translate("fillcolor"), translate("info.fillcolor"), new Color(0, 0, 0, 0));

        @Getter
        private final ListOption<OutlineType> outlineType = new ListOption<>("outlineType",
                translate("outlinetype"), translate("info.outlinetype"), OutlineType.LINE);

        @Getter
        private final DoubleOption outlineWidth = new DoubleOption("outlineWidth",
                translate("outlinewidth"), translate("info.outlinewidth"), 1, 0.1, 30);

        @Getter
        private final BooleanOption cubeOutline = new BooleanOption("cubeOutline",
                translate("cubeoutline"),  translate("info.cubeoutline"), false);

        @Getter
        private final ListOption<ConnectType> connectType = new ListOption<>("connectType",
                translate("connecttype"), translate("info.connecttype"), ConnectType.SEAMLESS);

        @Getter
        private final ImmutableList<Option<?>> options = ImmutableList.of(
                active, alwaysShow, seeThrough, outlineColor, fillColor, outlineType, outlineWidth, cubeOutline, connectType
        );

    }

    public static class BlockInfo2d {

        private static String translate(String string) {
            return "betterblockoutline.config.blockinfo2d." + string;
        }

        private BlockInfo2d() {}

        public final static String NAME = "info";

        @Getter
        private final BooleanOption active = new BooleanOption("active",
                translate("active"), translate("info.active"), false);

        @Getter
        private final DoubleOption textSize = new DoubleOption("textSize",
                translate("textsize"), translate("info.textsize"), 0.02, 0.001, 0.5);

        @Getter
        private final DoubleOption lineHeight = new DoubleOption("lineHeight",
                translate("lineheight"), translate("info.lineheight"), 10, 3, 30);

        @Getter
        private final ColorOption textColor = new ColorOption("textColor",
                translate("textcolor"), translate("info.textcolor"), new Color(255, 255, 255, 255));

        @Getter
        private final ColorOption backgroundColor = new ColorOption("backgroundColor",
                translate("backgroundcolor"),  translate("info.backgroundcolor"), new Color(0, 0, 0, 32));

        @Getter
        private final ImmutableList<Option<?>> options = ImmutableList.of(active, textSize, lineHeight, textColor, backgroundColor);

    }

    public static class BlockInfo3d {

        private BlockInfo3d() {}

        private static String translate(String string) {
            return "betterblockoutline.config.blockinfo3d." + string;
        }

        public final static String NAME = "blockinfo3d";

        @Getter
        private final BooleanOption active = new BooleanOption("active",
                translate("active"), translate("info.active"), false);

        @Getter
        private final DoubleOption lineWidth = new DoubleOption("lineWidth",
                translate("linewidth"), translate("info.linewidth") , 2, 0.1, 30);

        @Getter
        private final ColorOption lineColor = new ColorOption("lineColor",
                translate("linecolor"), translate("info.linecolor"), new Color(175, 175, 175, 255));

        @Getter
        private final ImmutableList<Option<?>> options = ImmutableList.of(active, lineWidth, lineColor);

    }

    public static class BlockInfoDirectionArrow {

        private BlockInfoDirectionArrow() {}

        private static String translate(String string) {
            return "betterblockoutline.config.directionarrow." + string;
        }

        public final static String NAME = "directionarrow";

        @Getter
        private final ListOption<DirectionArrow.ArrowType> arrowType = new ListOption<>("arrowType",
                translate("arrowtype"), translate("info.arrowtype"), DirectionArrow.ArrowType.LINE_ARROW);

        @Getter
        private final HotkeySettingsOption cycleArrow = new HotkeySettingsOption("cycleArrow",
                translate("cyclearrow"), translate("info.cyclearrow"), new HotkeySettings(false, false, false, List.of(), PlayerContextCheck.getDefault()));

        @Getter
        private final BooleanOption logicalDirection = new BooleanOption("logicalDirection",
               translate("logicaldirection"), translate("info.logicaldirection"), false);


        @Getter
        private final ImmutableList<Option<?>> options = ImmutableList.of(arrowType, cycleArrow, logicalDirection);

    }

    private OptionSection generalOptions;

    private OptionSection blockInfo2dOptions;
    private OptionSection blockInfo3dOptions;
    private OptionSection arrowOptions;
    private OptionSection hotkeys;
    private OptionSection colorMods;

    @Override
    public List<Option<?>> getOptions() {
        updateSections();
        return List.of(generalOptions, blockInfo2dOptions, blockInfo3dOptions, arrowOptions, hotkeys);
    }

    public List<OptionSection> getCategories() {
        updateSections();
        List<Option<?>> render2dHotkeys = new ArrayList<>(BlockInfo2dRenderer.getInstance().getHotkeyConfigs());
        List<Option<?>> render3dHotkeys = new ArrayList<>(BlockInfo3dRenderer.getInstance().getHotkeyConfigs());
        OptionSection hotkeys = new OptionSection("hotkeys", "betterblockoutline.config.tab.hotkeys", "betterblockoutline.config.tab.info.hotkeys", List.of(
                new OptionSection("general", "betterblockoutline.config.tab.hotkeys.general", "betterblockoutline.config.tab.hotkeys.info.general",
                        Hotkeys.getInstance().getOptions()
                ),
                new OptionSection("blockinfo2d", "betterblockoutline.config.tab.hotkeys.blockinfo2d", "betterblockoutline.config.tab.hotkeys.info.blockinfo2d",
                        render2dHotkeys
                ),
                new OptionSection("blockinfo3d", "betterblockoutline.config.tab.hotkeys.blockinfo3d", "betterblockoutline.config.tab.hotkeys.info.blockinfo3d",
                        render3dHotkeys
                )
        ));
        OptionSection blockInfo = new OptionSection("blockinfo", "betterblockoutline.config.tab.blockinfo", "betterblockoutline.config.tab.info.blockinfo", List.of(
                blockInfo2dOptions,
                new OptionSection("blockinfo3d", "betterblockoutline.config.tab.blockinfo.blockinfo3d", "betterblockoutline.config.tab.blockinfo.info.blockinfo3d", List.of(
                        new OptionSection("general", "betterblockoutline.config.tab.blockinfo.blockinfo3d.general", "betterblockoutline.config.tab.blockinfo.blockinfo3d.info.general", blockInfo3dOptions.getOptions()),
                        arrowOptions
                ))
        ));
        return List.of(generalOptions, colorMods, blockInfo, hotkeys);
    }

    public void updateSections() {
        List<Option<?>> hotkeyOptions = new ArrayList<>(Hotkeys.getInstance().getOptions());
        hotkeyOptions.addAll(BlockInfo2dRenderer.getInstance().getHotkeyConfigs());
        hotkeyOptions.addAll(BlockInfo3dRenderer.getInstance().getHotkeyConfigs());
        hotkeys = new OptionSection(
                Hotkeys.NAME, "betterblockoutline.config.tab.hotkeys", "betterblockoutline.config.tab.info.hotkeys",
                hotkeyOptions
        );
        arrowOptions = new OptionSection(
                BlockInfoDirectionArrow.NAME, "betterblockoutline.config.tab.blockinfo.blockinfo3d.directionarrow", "betterblockoutline.config.tab.blockinfo.blockinfo3d.info.directionarrow",
                getBlockInfoArrow().getOptions()
        );
        List<Option<?>> options3d = new ArrayList<>(getBlockInfo3d().getOptions());
        options3d.addAll(BlockInfo3dRenderer.getInstance().getActiveConfigs());
        blockInfo3dOptions = new OptionSection(
                BlockInfo3d.NAME, "betterblockoutline.config.tab.blockinfo.blockinfo3d", "betterblockoutline.config.tab.blockinfo.info.blockinfo3d",
                options3d
        );
        List<Option<?>> options2d = new ArrayList<>(getBlockInfo2d().getOptions());
        options2d.addAll(BlockInfo2dRenderer.getInstance().getActiveConfigs());
        blockInfo2dOptions = new OptionSection(
                BlockInfo2d.NAME, "betterblockoutline.config.tab.blockinfo.blockinfo2d", "betterblockoutline.config.tab.blockinfo.info.blockinfo2d",
                options2d
        );
        generalOptions = new OptionSection(
                General.NAME, "betterblockoutline.config.tab.general", "betterblockoutline.config.tab.info.general",
                getGeneral().getOptions()
        );

        List<Option<?>> modOptions = new ArrayList<>();
        for (Map.Entry<String, List<ConfigColorModifier<?>>> entry : colorModifications.entrySet()) {
            List<Option<?>> fillModsOptions = new ArrayList<>(entry.getValue());
            fillModsOptions.add(0, new ColorModifierConfig(ColorModifierContext.FILL.fromString(entry.getKey())));
            modOptions.add(new OptionSection(
                    entry.getKey(), "betterblockoutline.config.tab." + entry.getKey(), "betterblockoutline.config.tab.info." + entry.getKey(),
                    fillModsOptions
            ));
        }

        colorMods = new OptionSection(
                "color_modifiers", "betterblockoutline.config.tab.color_mods", "betterblockoutline.config.tab.info.color_mods",
                modOptions
        );
    }

    @Override
    public void save() {
        super.save();
        config.load();
        // Custom save logic for color mods
        ConfigObject modsConfig = config.getConfig().createNew();
        for (Map.Entry<String, List<ConfigColorModifier<?>>> entry : colorModifications.entrySet()) {
            List<ConfigObject> nest = new ArrayList<>();
            for (ConfigColorModifier<?> mod : entry.getValue()) {
                ConfigObject obj = modsConfig.createNew();
                mod.save(obj);
                obj.set("type", mod.getType().getSaveKey());
                nest.add(obj);
            }

            modsConfig.set(entry.getKey(), nest);
        }

        config.getConfig().set("color_modifiers", modsConfig);
        config.save();
        config.close();
    }

    @Override
    public void rawLoad() {
        super.rawLoad();
        ConfigObject obj = config.getConfig();
        Optional<ConfigObject> mods = obj.getOptional("color_modifiers");
        if (mods.isEmpty()) {
            return;
        }
        colorModifications.clear();
        for (ColorModifierType type : ColorModifierType.values()) {
            colorModifications.put(type.getSaveKey(), new ArrayList<>());
        }
        for (Map.Entry<String, Object> mod : mods.get().getValues().entrySet()) {
            List<ConfigObject> m = (List<ConfigObject>) mod.getValue();
            for (ConfigObject o : m) {
                ColorModifierType type = ColorModifierType.CHROMA.fromString(o.get("type"));
                ConfigColorModifier<?> colorMod = new ConfigColorModifier<>(type);
                colorMod.load(o);
                addColorMod(mod.getKey(), colorMod);
            }
        }
    }

    public void addColorMod(String key, ConfigColorModifier<?> modifier) {
        List<ConfigColorModifier<?>> mods = getColorMods(key);
        mods.add(modifier);
        Collections.sort(mods);
    }

    public List<ConfigColorModifier<?>> getColorMods(String key) {
        List<ConfigColorModifier<?>> mods = colorModifications.get(key);
        if (mods != null) {
            return mods;
        }
        colorModifications.put(key, new ArrayList<>());
        return getColorMods(key);
    }

    @Override
    public Screen getScreen() {
        return ConfigScreen.ofSections(getCategories());
    }
}
