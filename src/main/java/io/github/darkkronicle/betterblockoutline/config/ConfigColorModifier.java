package io.github.darkkronicle.betterblockoutline.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.betterblockoutline.interfaces.IJsonSaveable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ConfigColorModifier implements IJsonSaveable, Comparable<ConfigColorModifier> {

    @Getter
    private final SaveableConfig<ConfigBoolean> active = SaveableConfig.fromConfig("active",
            new ConfigBoolean("betterblockoutline.colormodifier.active", true, "betterblockoutline.colormodifier.info.active"));
    @Getter
    private ColorModifierType type;
    @Getter
    private IColorModifier colorModifier;
    private String id;

    public ConfigColorModifier(ColorModifierType type) {
        this.type = type;
        this.colorModifier = this.type.getColorModifier().get();
        // Generate random string to make it hard to confuse
        this.id = UUID.randomUUID().toString().substring(0, 7) + "-" + this.type.getStringValue();
    }

    public List<SaveableConfig<? extends IConfigBase>> getSaveableConfigs() {
        List<SaveableConfig<? extends IConfigBase>> configs = new ArrayList<>();
        configs.add(active);
        configs.addAll(colorModifier.getSaveableConfigs());
        return configs;
    }

    public List<IConfigBase> getOptions() {
        List<IConfigBase> configs = new ArrayList<>();
        for (SaveableConfig<? extends IConfigBase> config : getSaveableConfigs()) {
            configs.add(config.config);
        }
        return configs;
    }

    public List<String> getHoverLines() {
        return colorModifier.getHoverLines();
    }

    public String getName() {
        return this.id;
    }

    @Override
    public JsonObject save() {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", this.id);
        for (SaveableConfig<?> config : getSaveableConfigs()) {
            obj.add(config.key, config.config.getAsJsonElement());
        }
        return obj;
    }

    @Override
    public void load(JsonElement element) {
        if (!element.isJsonObject()) {
            return;
        }
        JsonObject obj = element.getAsJsonObject();
        if (obj.has("name")) {
            this.id = obj.get("name").getAsString();
        }
        for (SaveableConfig<?> config : getSaveableConfigs()) {
            if (obj.has(config.key)) {
                config.config.setValueFromJsonElement(obj.get(config.key));
            }
        }
    }

    @Override
    public int compareTo(@NotNull ConfigColorModifier o) {
        return getColorModifier().compareTo(o.getColorModifier());
    }

}
