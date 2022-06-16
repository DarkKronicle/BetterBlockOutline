package io.github.darkkronicle.betterblockoutline.blockinfo.info2d;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SignText extends AbstractBlockInfo2d {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public SignText() {
        super(Order.SPECIFIC, "signtext", "betterblockoutline.blockinfo2d.signtext", "betterblockoutline.blockinfo2d.info.signtext");
    }

    @Override
    public boolean shouldRender(AbstractConnectedBlock block) {
        return block.getBlock().getState().getBlock() instanceof AbstractSignBlock;
    }

    @Override
    public Optional<List<String>> getLines(AbstractConnectedBlock block) {
        BlockEntity entity = client.world.getBlockEntity(block.getBlock().getPos());
        if (!(entity instanceof SignBlockEntity sign)) {
            return Optional.empty();
        }
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Text line = sign.getTextOnRow(i, true);
            String string = line.getString();
            if (line.getContent() != LiteralTextContent.EMPTY && string.length() > 0) {
                lines.add(string);
            }
        }
        if (lines.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(lines);
    }
}
