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

public class ResourcesManager
{
	private static final ResourcesManager DEFAULT_RESOURCES_MANAGER = new ResourcesManager();

	public static @NotNull ResourcesManager getDefaultResourcesManager()
	{
		return DEFAULT_RESOURCES_MANAGER;
	}

	public boolean saveResource(@NotNull URL path, @NotNull File dest, boolean replace)
	{
		if (path.getPath() == null)
			return false;

		InputStream is = getResource(path);

		if (is == null)
			return false;

		return saveResource(is, path.getPath(), dest, replace);
	}

	public boolean saveResource(@NotNull InputStream input, @NotNull String path, @NotNull File dest, boolean replace)
	{
		File outFile = new File(dest, path);
		File parentDir = outFile.getParentFile();
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
			URLConnection connection = url.openConnection();
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
		Optional<URL> url = getResourceURLFromJar(file);
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