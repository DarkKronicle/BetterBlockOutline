package io.github.darkkronicle.betterblockoutline.config;

import fi.dy.masa.malilib.config.IConfigBase;

public class SaveableConfig<T extends IConfigBase> {

    public final T config;
    public final String key;

    private SaveableConfig(String key, T config) {
        this.key = key;
        this.config = config;
    }

    public static <C extends IConfigBase> SaveableConfig<C> fromConfig(String key, C config) {
        return new SaveableConfig<>(key, config);
    }

}
