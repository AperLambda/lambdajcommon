/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.config;

import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.aperlambda.lambdacommon.LambdaConstants.GSON_PRETTY;
import static org.aperlambda.lambdacommon.LambdaConstants.JSON_PARSER;

/**
 * Represents a JSON configuration stored in a file.
 *
 * @author LambdAurora
 * @version 1.4.0
 * @since 1.3.0
 */
public class JsonConfig extends FileConfig
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
		try
		{
			config = JSON_PARSER.parse(Files.asCharSource(file, Charset.defaultCharset()).read()).getAsJsonObject();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save()
	{
		if (!file.exists())
			file.getParentFile().mkdirs();

		try
		{
			Files.asCharSink(file, Charset.defaultCharset()).write(GSON_PRETTY.toJson(config));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(String key, Object value)
	{
		if (key.contains("."))
		{
			var path = key.split("\\.");
			// Starts at root.
			var currentObject = config;

			for (int i = 0; i < path.length - 1; i++)
			{
				var currentKey = path[i];

				// Add objects to achieve the path.
				if (!currentObject.has(currentKey))
					currentObject.add(currentKey, new JsonObject());

				currentObject = currentObject.getAsJsonObject(currentKey);
			}

			currentObject.add(path[path.length - 1], GSON_PRETTY.toJsonTree(value));
		}
		else
			config.add(key, GSON_PRETTY.toJsonTree(value));

		if (autoSave)
			save();
	}

	@Override
	public <T> T at(String path, T def, Class<T> type)
	{
		return jsonAt(config, path, def, type);
	}

	@Override
	public <T> T get(String key, T def, Class<T> type)
	{
		return jsonGet(config, key, def, type);
	}

	static <T> T jsonGet(JsonObject config, String key, T def, Class<T> type)
	{
		T value = GSON_PRETTY.fromJson(config.get(key), type);
		return value == null ? def : value;
	}

	static <T> T jsonAt(JsonObject config, String path, T def, Class<T> type)
	{
		if (path.contains("."))
		{
			try
			{
				var parts = path.split("\\.");
				// Starts at root.
				JsonElement currentElement = config;

				for (int i = 0; i < parts.length - 1; i++)
				{
					currentElement = currentElement.getAsJsonObject().get(parts[i]);

					// Cannot go further...
					if (currentElement == null)
						return def;
					else if (!currentElement.isJsonObject())
						throw new IllegalArgumentException("Field '" + parts[i] + "' isn't an object!");
				}

				T value = GSON_PRETTY.fromJson(currentElement.getAsJsonObject().get(parts[parts.length - 1]), type);
				return value == null ? def : value;
			}
			catch (JsonParseException e)
			{
				return def;
			}
		}
		else
			return jsonGet(config, path, def, type);
	}

	/**
	 * Gets the root JSON Object of the configuration.
	 *
	 * @return The root JSON Object of the config.
	 */
	public JsonObject getConfig()
	{
		return config;
	}
}