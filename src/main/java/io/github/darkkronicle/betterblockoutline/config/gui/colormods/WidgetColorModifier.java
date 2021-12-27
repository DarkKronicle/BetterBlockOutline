package io.github.darkkronicle.betterblockoutline.config.gui.colormods;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.darkkronicle.betterblockoutline.config.gui.ColorModifierEditor;
import io.github.darkkronicle.betterblockoutline.config.ConfigColorModifier;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;
import java.util.function.Consumer;

public class WidgetColorModifier extends WidgetListEntryBase<ConfigColorModifier> {

    private final boolean odd;
    private final List<String> hoverLines;
    private final ConfigColorModifier configColorModifier;
    private final WidgetListColorModifiers parent;

    private int buttonStartX;

    public WidgetColorModifier(int x, int y, int width, int height, boolean odd, ConfigColorModifier entry, int listIndex, WidgetListColorModifiers parent) {
        super(x, y, width, height, entry, listIndex);
        this.odd = odd;
        this.hoverLines = entry.getHoverLines();
        this.configColorModifier = entry;
        this.parent = parent;

        int pos = x + width - 2;

        pos -= addButton(pos, y, ButtonListener.Type.CONFIGURE, null);

        buttonStartX = pos;

    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrices) {
        RenderUtils.color(1f, 1f, 1f, 1f);

        // Draw a lighter background for the hovered and the selected entry
        if (selected || this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, new Color4f(1, 1, 1, .8f).intValue);
        } else if (this.odd) {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, new Color4f(1, 1, 1, .6f).intValue);
        } else {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, new Color4f(1, 1, 1, .4f).intValue);
        }
        String name = configColorModifier.getName();
        this.drawString(this.x + 4, this.y + 7, -1, name, matrices);

        RenderUtils.color(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        super.render(mouseX, mouseY, selected, matrices);
        RenderUtils.disableDiffuseLighting();
    }

    @Override
    public void postRenderHovered(
            int mouseX, int mouseY, boolean selected, MatrixStack matrixStack) {
        super.postRenderHovered(mouseX, mouseY, selected, matrixStack);

        if (mouseX >= this.x
                && mouseX < this.buttonStartX
                && mouseY >= this.y
                && mouseY <= this.y + this.height) {
            RenderUtils.drawHoverText(mouseX, mouseY, this.hoverLines, matrixStack);
        }
    }

    protected int addButton(int x, int y, ButtonListener.Type type, Consumer<ConfigColorModifier> remove) {
        ButtonGeneric button = new ButtonGeneric(x, y, -1, true, type.getDisplayName());
        this.addButton(button, new ButtonListener(type, this, remove));

        return button.getWidth() + 1;
    }

    private static class ButtonListener implements IButtonActionListener {

        private final Type type;
        private final WidgetColorModifier parent;
        private final Consumer<ConfigColorModifier> remove;

        public ButtonListener(Type type, WidgetColorModifier parent) {
            this(type, parent, null);
        }

        public ButtonListener(Type type, WidgetColorModifier parent, Consumer<ConfigColorModifier> remove) {
            this.parent = parent;
            this.type = type;
            this.remove = remove;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            if (type == Type.REMOVE) {
                if (remove != null) {
                    remove.accept(parent.entry);
                }
                parent.parent.refreshEntries();
            } else if (type == Type.ACTIVE) {
                this.parent.entry.getActive().config.setBooleanValue(!this.parent.entry.getActive().config.getBooleanValue());
                parent.parent.refreshEntries();
            } else if (type == Type.CONFIGURE) {
                GuiBase.openGui(new ColorModifierEditor(parent.entry, parent.parent));
            }
        }

        public enum Type {
            CONFIGURE("configure"),
            REMOVE("remove"),
            ACTIVE("active");

            private final String translate;

            Type(String name) {
                this.translate = translate(name);
            }

            private static String translate(String key) {
                return "betterblockoutline.config." + key;
            }

            public String getDisplayName() {
                return StringUtils.translate(translate);
            }
        }
    }

}
