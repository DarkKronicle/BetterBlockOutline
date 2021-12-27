package io.github.darkkronicle.betterblockoutline.interfaces;

import com.google.gson.JsonElement;

public interface IJsonSaveable {

    JsonElement save();

    void load(JsonElement obj);

}
