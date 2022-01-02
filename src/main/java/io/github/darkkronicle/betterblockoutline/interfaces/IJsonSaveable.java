package io.github.darkkronicle.betterblockoutline.interfaces;

import com.google.gson.JsonElement;

/**
 * An interface that allows updating from {@link JsonElement} and saving into a {@link JsonElement}
 */
public interface IJsonSaveable {

    /**
     * Save's the current state of the object into a {@link JsonElement}
     * @return {@link JsonElement} containing data to store.
     */
    JsonElement save();

    /**
     * Load's the object's data from previously saved data.
     * @param obj Saved {@link JsonElement}
     */
    void load(JsonElement obj);

}
