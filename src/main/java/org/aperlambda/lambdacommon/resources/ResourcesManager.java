/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.resources;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * A resources manager which can save resources.
 *
 * @version 1.7.0
 */
public class ResourcesManager
{
    private static final ResourcesManager DEFAULT_RESOURCES_MANAGER = new ResourcesManager();

    /**
     * Gets the default implementation of the {@code ResourcesManager}.
     *
     * @return The default implementation.
     */
    public static @NotNull ResourcesManager get_default_resources_manager()
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
    public boolean save_resource(@NotNull URL path, @NotNull File dest, boolean replace)
    {
        if (path.getPath() == null)
            return false;

        InputStream is = get_resource(path);

        if (is == null)
            return false;

        return save_resource(is, path.getPath(), dest, replace);
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
    public boolean save_resource(@NotNull InputStream input, @NotNull String path, @NotNull File dest, boolean replace)
    {
        File out_file = new File(dest, path);
        File parent_dir = out_file.getParentFile();
        if (!parent_dir.exists())
            if (!parent_dir.mkdirs())
                throw new RuntimeException(new IOException("Cannot create the parent directory of the resource to save."));

        if (!out_file.exists() || replace) {
            try {
                Files.copy(input, out_file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public boolean save_resource_from_jar(@NotNull String path, @NotNull File dest, boolean replace)
    {
        path = path.replace('\\', '/');
        InputStream is = get_resource_from_jar(path);

        if (is == null)
            return false;

        return save_resource(is, path, dest, replace);
    }

    public @Nullable InputStream get_resource(@NotNull URL url)
    {
        try {
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public @Nullable InputStream get_resource_from_jar(@NotNull String file)
    {
        Optional<URL> url = get_resource_url_from_jar(file);
        return url.map(this::get_resource).orElse(null);
    }

    public @NotNull Optional<URL> get_resource_url_from_jar(@NotNull String file)
    {
        return Optional.ofNullable(getClass().getClassLoader().getResource(file));
    }
}
