/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.config.json;

import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.aperlambda.lambdacommon.config.FileConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.aperlambda.lambdacommon.LambdaConstants.GSON_PRETTY;
import static org.aperlambda.lambdacommon.LambdaConstants.JSON_PARSER;

/**
 * Represents a JSON configuration stored in a file.
 *
 * @author LambdAurora
 * @version 1.6.0
 * @since 1.3.0
 */
public class JsonConfig extends FileConfig<JsonObject> implements BaseJsonConfig
{
    private JsonObject config;

    public JsonConfig()
    {
        super();
    }

    public JsonConfig(File file)
    {
        super(file);
    }

    @Override
    public void load()
    {
        try {
            config = JSON_PARSER.parse(Files.asCharSource(file, Charset.defaultCharset()).read()).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save()
    {
        if (!file.exists())
            if (!file.getParentFile().mkdirs())
                throw new RuntimeException(new IOException("Cannot create the parent directory of the json configuration file."));

        try {
            Files.asCharSink(file, Charset.defaultCharset()).write(GSON_PRETTY.toJson(config));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void set(String key, Object value)
    {
        if (key.contains(".")) {
            String[] path = key.split("\\.");
            // Starts at root.
            JsonObject current_object = config;

            for (int i = 0; i < path.length - 1; i++) {
                String current_key = path[i];

                // Add objects to achieve the path.
                if (!current_object.has(current_key))
                    current_object.add(current_key, new JsonObject());

                current_object = current_object.getAsJsonObject(current_key);
            }

            current_object.add(path[path.length - 1], GSON_PRETTY.toJsonTree(value));
        } else
            config.add(key, GSON_PRETTY.toJsonTree(value));

        if (auto_save)
            save();
    }

    @Override
    public <T> T at(String path, T def, Class<T> type)
    {
        return json_at(config, path, def, type);
    }

    @Override
    public <T> T get(String key, T def, Class<T> type)
    {
        return json_get(config, key, def, type);
    }

    static <T> T json_get(JsonObject config, String key, T def, Class<T> type)
    {
        T value = GSON_PRETTY.fromJson(config.get(key), type);
        return value == null ? def : value;
    }

    static <T> T json_at(JsonObject config, String path, T def, Class<T> type)
    {
        if (path.contains(".")) {
            try {
                String[] parts = path.split("\\.");
                // Starts at root.
                JsonElement current_element = config;

                for (int i = 0; i < parts.length - 1; i++) {
                    current_element = current_element.getAsJsonObject().get(parts[i]);

                    // Cannot go further...
                    if (current_element == null)
                        return def;
                    else if (!current_element.isJsonObject())
                        throw new IllegalArgumentException("Field '" + parts[i] + "' isn't an object!");
                }

                T value = GSON_PRETTY.fromJson(current_element.getAsJsonObject().get(parts[parts.length - 1]), type);
                return value == null ? def : value;
            } catch (JsonParseException e) {
                return def;
            }
        } else
            return json_get(config, path, def, type);
    }

    /**
     * Gets the root JSON Object of the configuration.
     *
     * @return The root JSON Object of the config.
     */
    @Override
    public JsonObject get_config()
    {
        return config;
    }
}
