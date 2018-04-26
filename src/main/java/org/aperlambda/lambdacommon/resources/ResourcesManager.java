/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.resources;

import org.aperlambda.lambdacommon.utils.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * A resources manager which can save resources.
 */
public class ResourcesManager
{
	private static final ResourcesManager DEFAULT_RESOURCES_MANAGER = new ResourcesManager();

	/**
	 * Gets the default implementation of the {@code ResourcesManager}.
	 *
	 * @return The default implementation.
	 */
	public static @NotNull ResourcesManager getDefaultResourcesManager()
	{
		return DEFAULT_RESOURCES_MANAGER;
	}

	/**
	 * Saves a resource to a file.
	 *
	 * @param path    The path of the resource.
	 * @param dest    The destination folder of the resource.
	 * @param replace True if replace the existing file, else false.
	 * @return True if success, else false.
	 */
	public boolean saveResource(@NotNull URL path, @NotNull File dest, boolean replace)
	{
		if (path.getPath() == null)
			return false;

		InputStream is = getResource(path);

		if (is == null)
			return false;

		return saveResource(is, path.getPath(), dest, replace);
	}

	/**
	 * Saves the resource to a file.
	 *
	 * @param input   The input stream of the resource.
	 * @param path    The path of the resource.
	 * @param dest    The destination folder of the resource.
	 * @param replace True if replace existing file, else false.
	 * @return True if success, else false.
	 */
	public boolean saveResource(@NotNull InputStream input, @NotNull String path, @NotNull File dest, boolean replace)
	{
		var outFile = new File(dest, path);
		var parentDir = outFile.getParentFile();
		if (!parentDir.exists())
			parentDir.mkdirs();

		if (!outFile.exists() || replace)
		{
			try
			{
				Files.copy(input, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (IOException e)
			{
				return false;
			}
		}
		return true;
	}

	public boolean saveResourceFromJar(@NotNull String path, @NotNull File dest, boolean replace)
	{
		path = path.replace('\\', '/');
		InputStream is = getResourceFromJar(path);

		if (is == null)
			return false;

		return saveResource(is, path, dest, replace);
	}

	public @Nullable InputStream getResource(@NotNull URL url)
	{
		try
		{
			var connection = url.openConnection();
			connection.setUseCaches(false);
			return connection.getInputStream();
		}
		catch (IOException e)
		{
			return null;
		}
	}

	public @Nullable InputStream getResourceFromJar(@NotNull String file)
	{
		var url = getResourceURLFromJar(file);
		if (url.isPresent())
			return getResource(url.get());
		else
			return null;
	}

	public @NotNull Optional<URL> getResourceURLFromJar(@NotNull String file)
	{
		return Optional.ofNullable(getClass().getClassLoader().getResource(file));
	}
}