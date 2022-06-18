package io.github.darkkronicle.betterblockoutline.config;

import io.github.darkkronicle.betterblockoutline.colors.BlinkColorModifier;
import io.github.darkkronicle.betterblockoutline.colors.ColorModifierType;
import io.github.darkkronicle.betterblockoutline.interfaces.IColorModifier;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConfigColorModifier<T extends IColorModifier> extends BasicOption<T> implements Comparable<ConfigColorModifier<?>> {

    @Getter
    private final BooleanOption active = new BooleanOption("active",
            "betterblockoutline.colormodifier.active", "betterblockoutline.colormodifier.info.active", true);
    @Getter
    private ColorModifierType type;
    @Getter
    private T colorModifier;
    private String id;

    public ConfigColorModifier(ColorModifierType type) {
        super("colorConfig", type.getDisplayKey(), type.getInfoKey(), null);
        this.type = type;
        this.colorModifier = (T) this.type.getColorModifier().get();
        this.setValue(this.colorModifier);
        this.setDefaultValue(this.getValue());
        // Generate random string to make it hard to confuse
        this.id = UUID.randomUUID().toString().substring(0, 7) + "-" + this.type.getSaveKey();
    }

    public List<Option<?>> getOptions() {
        List<Option<?>> configs = new ArrayList<>();
        configs.add(active);
        configs.addAll(colorModifier.getOptions());
        return configs;
    }

    public List<String> getHoverLines() {
        return colorModifier.getHoverLines();
    }

    public String getName() {
        return this.id;
    }

    @Override
    public void save(ConfigObject config) {
        ConfigObject nest = config.createNew();
        for (Option<?> option : getOptions()) {
            option.save(nest);
        }
        nest.set("type", type.getSaveKey());
        config.set(key, nest);
    }

    @Override
    public void load(ConfigObject config) {
        Optional<ConfigObject> nest = config.getOptional(key);
        if (nest.isEmpty()) {
            return;
        }
        ConfigObject obj = nest.get();
        for (Option<?> option : getOptions()) {
            option.load(obj);
        }
    }

    @Override
    public int compareTo(@NotNull ConfigColorModifier o) {
        return getColorModifier().compareTo(o.getColorModifier());
    }

}
