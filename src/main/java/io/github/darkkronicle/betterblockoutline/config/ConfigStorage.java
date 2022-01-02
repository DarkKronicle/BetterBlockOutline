package io.github.darkkronicle.betterblockoutline.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.BetterBlockOutline;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.betterblockoutline.config.hotkeys.Hotkeys;
import io.github.darkkronicle.betterblockoutline.blockinfo.info3d.DirectionArrow;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo2dRenderer;
import io.github.darkkronicle.betterblockoutline.renderers.BlockInfo3dRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigStorage implements IConfigHandler {

    public static final String CONFIG_FILE_NAME = BetterBlockOutline.MOD_ID + ".json";
    public static final int CONFIG_VERSION = 1;

    public static final Map<String, List<ConfigColorModifier>> COLOR_MODS = new HashMap<>();

    public static class General {

        private static String translate(String string) {
            return "betterblockoutline.config.general." + string;
        }

        public final static String NAME = "general";

        public final static SaveableConfig<ConfigBoolean> ACTIVE = SaveableConfig.fromConfig("active",
                new ConfigBoolean(translate("active"), true, translate("info.active")));

        public final static SaveableConfig<ConfigBoolean> SEE_THROUGH = SaveableConfig.fromConfig("seeThrough",
                new ConfigBoolean(translate("seethrough"), false, translate("info.seethrough")));

        public final static SaveableConfig<ConfigColor> OUTLINE_COLOR = SaveableConfig.fromConfig("outlineColor",
                new ConfigColor(translate("outlinecolor"), "#FF000000", translate("info.outlinecolor")));

        public final static SaveableConfig<ConfigColor> FILL_COLOR = SaveableConfig.fromConfig("fillColor",
                new ConfigColor(translate("fillcolor"), "#00000000", translate("info.fillcolor")));

        public final static SaveableConfig<ConfigOptionList> OUTLINE_TYPE = SaveableConfig.fromConfig("outlineType",
                new ConfigOptionList(translate("outlinetype"), OutlineType.LINE, translate("info.outlinetype")));

        public final static SaveableConfig<ConfigDouble> OUTLINE_WIDTH = SaveableConfig.fromConfig("outlineWidth",
                new ConfigDouble(translate("outlinewidth"), 1, 0.1, 30, translate("info.outlinewidth")));

        public final static SaveableConfig<ConfigBoolean> CUBE_OUTLINE = SaveableConfig.fromConfig("cubeOutline",
                new ConfigBoolean(translate("cubeoutline"), false, translate("info.cubeoutline")));

        public final static SaveableConfig<ConfigOptionList> CONNECT_TYPE = SaveableConfig.fromConfig("connectType",
                new ConfigOptionList(translate("connecttype"), ConnectType.SEAMLESS, translate("info.connecttype")));

        public static final ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(ACTIVE, SEE_THROUGH, OUTLINE_COLOR, FILL_COLOR, OUTLINE_TYPE, OUTLINE_WIDTH, CUBE_OUTLINE, CONNECT_TYPE);

    }

    public static class BlockInfo2d {

        private static String translate(String string) {
            return StringUtils.translate("betterblockoutline.config.blockinfo2d." + string);
        }

        public final static String NAME = "info";

        public final static SaveableConfig<ConfigBoolean> ACTIVE = SaveableConfig.fromConfig("active",
                new ConfigBoolean(translate("active"), false, translate("info.active")));

        public final static SaveableConfig<ConfigDouble> TEXT_SIZE = SaveableConfig.fromConfig("textSize",
                new ConfigDouble(translate("textsize"), 0.02, 0.001, 0.5, translate("info.textsize")));

        public final static SaveableConfig<ConfigDouble> LINE_HEIGHT = SaveableConfig.fromConfig("lineHeight",
                new ConfigDouble(translate("lineheight"), 10, 3, 30, translate("info.lineheight")));

        public final static SaveableConfig<ConfigColor> TEXT_COLOR = SaveableConfig.fromConfig("textColor",
                new ConfigColor(translate("textcolor"), "#FFFFFFFF",  translate("info.textcolor")));

        public final static SaveableConfig<ConfigColor> BACKGROUND_COLOR = SaveableConfig.fromConfig("backgroundColor",
                new ConfigColor(translate("backgroundcolor"), "#20000000",  translate("info.backgroundcolor")));

        public static final ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(ACTIVE, TEXT_SIZE, LINE_HEIGHT, TEXT_COLOR, BACKGROUND_COLOR);

    }

    public static class BlockInfo3d {

        private static String translate(String string) {
            return StringUtils.translate("betterblockoutline.config.blockinfo3d." + string);
        }

        public final static String NAME = "blockinfo3d";

        public final static SaveableConfig<ConfigBoolean> ACTIVE = SaveableConfig.fromConfig("active",
                new ConfigBoolean(translate("active"), false, translate("info.active")));

        public final static SaveableConfig<ConfigDouble> LINE_WIDTH = SaveableConfig.fromConfig("lineWidth",
                new ConfigDouble(translate("linewidth"), 2, 0.1, 30, translate("info.linewidth")));

        public final static SaveableConfig<ConfigColor> LINE_COLOR = SaveableConfig.fromConfig("lineColor",
                new ConfigColor(translate("linecolor"), "#FFAFAFAF", translate("info.linecolor")));

        public static final ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(ACTIVE, LINE_WIDTH, LINE_COLOR);

    }

    public static class BlockInfoDirectionArrow {

        private static String translate(String string) {
            return StringUtils.translate("betterblockoutline.config.directionarrow." + string);
        }

        public final static String NAME = "directionarrow";

        public final static SaveableConfig<ConfigOptionList> ARROW_TYPE = SaveableConfig.fromConfig("arrowType",
                new ConfigOptionList(translate("arrowtype"), DirectionArrow.ArrowType.LINE_ARROW, translate("info.arrowtype")));

        public final static SaveableConfig<ConfigHotkey> CYCLE_ARROW = SaveableConfig.fromConfig("cycleArrow",
                new ConfigHotkey(translate("cyclearrow"), "", KeybindSettings.MODIFIER_INGAME, translate("info.cyclearrow")));

        public final static SaveableConfig<ConfigBoolean> LOGICAL_DIRECTION = SaveableConfig.fromConfig("logicalDirection",
                new ConfigBoolean(translate("logicaldirection"), false, translate("info.logicaldirection")));


        public static final ImmutableList<SaveableConfig<? extends IConfigBase>> OPTIONS = ImmutableList.of(ARROW_TYPE, CYCLE_ARROW, LOGICAL_DIRECTION);

    }



    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveFromFile();
    }

    public static void loadFromFile() {
        File configFile = FileUtils.getConfigDirectory().toPath().resolve(BetterBlockOutline.MOD_ID).resolve(CONFIG_FILE_NAME).toFile();

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                readOptions(root, General.NAME, General.OPTIONS);
                readOptions(root, BlockInfo2d.NAME, BlockInfo2d.OPTIONS);
                readOptions(root, BlockInfo2d.NAME, BlockInfo2dRenderer.getInstance().getActiveConfigs());
                readOptions(root, BlockInfo3d.NAME, BlockInfo3d.OPTIONS);
                readOptions(root, BlockInfo3d.NAME, BlockInfo3dRenderer.getInstance().getActiveConfigs());
                readOptions(root, BlockInfoDirectionArrow.NAME, BlockInfoDirectionArrow.OPTIONS);
                readOptions(root, Hotkeys.NAME, Hotkeys.OPTIONS);
                readOptions(root, Hotkeys.NAME, BlockInfo2dRenderer.getInstance().getHotkeyConfigs());
                readOptions(root, Hotkeys.NAME, BlockInfo3dRenderer.getInstance().getHotkeyConfigs());

                COLOR_MODS.clear();
                JsonElement colorModsEl = root.get("color_modifiers");
                if (colorModsEl != null && colorModsEl.isJsonObject()) {
                    JsonObject colorMods = colorModsEl.getAsJsonObject();
                    for (Map.Entry<String, JsonElement> value : colorMods.entrySet()) {
                        if (!value.getValue().isJsonArray()) {
                            continue;
                        }
                        for (JsonElement el : value.getValue().getAsJsonArray()) {
                            if (!el.isJsonObject()) {
                                continue;
                            }
                            JsonObject obj = el.getAsJsonObject();
                            if (!obj.has("type")) {
                                continue;
                            }
                            ColorModifierType type = ColorModifierType.CHROMA.fromString(obj.get("type").getAsString());
                            ConfigColorModifier mod = new ConfigColorModifier(type);
                            mod.load(el);
                            addColorMod(value.getKey(), mod);
                        }
                    }
                }

                int version = JsonUtils.getIntegerOrDefault(root, "configVersion", 0);
            }
        }
    }

    public static void saveFromFile() {
        File dir = FileUtils.getConfigDirectory().toPath().resolve(BetterBlockOutline.MOD_ID).toFile();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            writeOptions(root, General.NAME, General.OPTIONS);
            writeOptions(root, BlockInfo2d.NAME, BlockInfo2d.OPTIONS);
            writeOptions(root, BlockInfo2d.NAME, BlockInfo2dRenderer.getInstance().getActiveConfigs());
            writeOptions(root, BlockInfo3d.NAME, BlockInfo3d.OPTIONS);
            writeOptions(root, BlockInfo3d.NAME, BlockInfo3dRenderer.getInstance().getActiveConfigs());
            writeOptions(root, BlockInfoDirectionArrow.NAME, BlockInfoDirectionArrow.OPTIONS);
            writeOptions(root, Hotkeys.NAME, Hotkeys.OPTIONS);
            writeOptions(root, Hotkeys.NAME, BlockInfo2dRenderer.getInstance().getHotkeyConfigs());
            writeOptions(root, Hotkeys.NAME, BlockInfo3dRenderer.getInstance().getHotkeyConfigs());


            JsonObject colorMods = new JsonObject();

            for (Map.Entry<String, List<ConfigColorModifier>> mods : COLOR_MODS.entrySet()) {
                JsonArray modParent = new JsonArray();
                for (ConfigColorModifier mod : mods.getValue()) {
                    JsonObject modValue = mod.save();
                    modValue.addProperty("type", mod.getType().getStringValue());
                    modParent.add(modValue);
                }
                colorMods.add(mods.getKey(), modParent);
            }

            root.add("color_modifiers", colorMods);

            root.add("config_version", new JsonPrimitive(CONFIG_VERSION));
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    public static void readOptions(JsonObject root, String category, List<SaveableConfig<?>> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, false);

        if (obj != null) {
            for (SaveableConfig<?> conf : options) {
                IConfigBase option = conf.config;
                if (obj.has(conf.key)) {
                    option.setValueFromJsonElement(obj.get(conf.key));
                }
            }
        }
    }

    public static void writeOptions(
            JsonObject root, String category, List<SaveableConfig<?>> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, true);

        for (SaveableConfig<?> option : options) {
            obj.add(option.key, option.config.getAsJsonElement());
        }
    }

    public static void addColorMod(String key, ConfigColorModifier modifier) {
        List<ConfigColorModifier> mods = getColorMods(key);
        mods.add(modifier);
        Collections.sort(mods);
    }

    public static List<ConfigColorModifier> getColorMods(String key) {
        List<ConfigColorModifier> mods = COLOR_MODS.get(key);
        if (mods != null) {
            return mods;
        }
        COLOR_MODS.put(key, new ArrayList<>());
        return getColorMods(key);
    }

}
