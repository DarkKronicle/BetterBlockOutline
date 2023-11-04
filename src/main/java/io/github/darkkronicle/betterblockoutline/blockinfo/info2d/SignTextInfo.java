package io.github.darkkronicle.betterblockoutline.blockinfo.info2d;

import io.github.darkkronicle.betterblockoutline.connectedblocks.AbstractConnectedBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SignTextInfo extends AbstractBlockInfo2d {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public SignTextInfo() {
        super(Order.SPECIFIC, "signtextinfo", "betterblockoutline.blockinfo2d.signtextinfo", "betterblockoutline.blockinfo2d.info.signtextinfo");
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
        SignText signText;
        if (sign.isPlayerFacingFront(MinecraftClient.getInstance().player)) {
            signText = sign.getFrontText();
        } else {
            signText = sign.getBackText();
        }
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Text line = signText.getMessage(i, true);
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
