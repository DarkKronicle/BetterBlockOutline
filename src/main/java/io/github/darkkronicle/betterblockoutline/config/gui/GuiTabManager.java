package io.github.darkkronicle.betterblockoutline.config.gui;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.config.SaveableConfig;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GuiTabManager {

    private final static GuiTabManager INSTANCE = new GuiTabManager();

    @Getter
    private final List<TabSupplier> tabs = new ArrayList<>();

    public static GuiTabManager getInstance() {
        return INSTANCE;
    }

    private GuiTabManager() {}

    public void add(TabSupplier supplier) {
        tabs.add(supplier);
    }

    public TabSupplier get(String name) {
        for (TabSupplier child : tabs) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    private static String getKey(String string) {
        return "betterblockoutline.config.tab." + string;
    }

    public static TabSupplier wrapSaveableOptions(String name, Supplier<List<SaveableConfig<? extends IConfigBase>>> supplier) {
        Supplier<List<IConfigBase>> configSupplier = () -> {
            List<IConfigBase> config = new ArrayList<>();
            List<SaveableConfig<? extends IConfigBase>> options = supplier.get();
            for (SaveableConfig<? extends IConfigBase> s : options) {
                config.add(s.config);
            }
            return config;
        };
        return wrapOptions(name, configSupplier);
    }

    public static TabSupplier wrapSaveableOptions(String name, List<SaveableConfig<? extends IConfigBase>> options) {
        List<IConfigBase> config = new ArrayList<>();
        for (SaveableConfig<? extends IConfigBase> s : options) {
            config.add(s.config);
        }
        return wrapOptions(name, config);
    }

    public static TabSupplier wrapOptions(String name, List<IConfigBase> configs) {
        return wrapOptions(name, () -> configs);
    }

    public static TabSupplier wrapOptions(String name, Supplier<List<IConfigBase>> options) {
        return new TabSupplier(name, getKey(name)) {
            @Override
            public List<IConfigBase> getOptions() {
                return options.get();
            }
        };
    }

    public static TabSupplier wrapScreen(String name, Function<Screen, Screen> screenSupplier) {
        return new TabSupplier(name, getKey(name)) {
            @Override
            public Screen getScreen(Screen parent) {
                return screenSupplier.apply(parent);
            }
        };
    }

    public static TabSupplier children(String name, TabSupplier... children) {
        TabSupplier tab = new TabSupplier(name, getKey(name)) {
            @Override
            public String getName() {
                return super.getName();
            }
        };
        for (TabSupplier child : children) {
            tab.addChild(child);
        }
        return tab;
    }

}
