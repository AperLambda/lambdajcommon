/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.config;

import com.google.gson.JsonSyntaxException;
import org.aperlambda.lambdacommon.LambdaConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Represents a properties configuration.
 *
 * @author lambdaurora
 * @version 1.6.0
 * @since 1.4.10
 */
public class PropertiesConfig extends FileConfig<Properties>
{
    private String     comments   = "";
    private Properties properties = new Properties();

    public PropertiesConfig()
    {
        super();
    }

    public PropertiesConfig(File file)
    {
        super(file);
    }

    @Override
    public void load()
    {
        try {
            properties.load(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save()
    {
        try {
            properties.store(Files.newOutputStream(file.toPath()), comments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key, T def, Class<T> type)
    {
        Object value = properties.getOrDefault(key, def);
        if (value.equals(def))
            return def;
        if (value instanceof String)
            try {
                return LambdaConstants.GSON.fromJson(LambdaConstants.JSON_PARSER.parse((String) value), type);
            } catch (JsonSyntaxException e) {
                return def;
            }
        else if (value instanceof Boolean)
            return def instanceof Boolean ? (T) value : def;
        return def;
    }

    @Override
    public void set(String key, Object value)
    {
        if (value instanceof Boolean)
            properties.put(key, value);
        else
            properties.setProperty(key, LambdaConstants.GSON.toJson(value));

        if (auto_save)
            save();
    }

    @Override
    public <T> T at(String path, T def, Class<T> type)
    {
        return get(path, def, type);
    }

    /**
     * Gets the comments of the configuration.
     *
     * @return The comments.
     */
    public String get_comments()
    {
        return comments;
    }

    /**
     * Sets the comments of the configuration.
     *
     * @param comments The comments of the configuration.
     */
    public void set_comments(String comments)
    {
        this.comments = comments;
    }

    @Override
    public Properties get_config()
    {
        return properties;
    }
}
