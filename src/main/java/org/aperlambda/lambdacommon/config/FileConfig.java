/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.config;

import java.io.File;

/**
 * Represents a configuration stored in a file.
 *
 * @param <C> The configuration object type.
 * @author LambdAurora
 * @version 1.4.10
 * @since 1.3.0
 */
public abstract class FileConfig<C> implements Config<C>
{
    protected File    file;
    protected boolean auto_save = false;

    public FileConfig()
    {
    }

    public FileConfig(File file)
    {
        in(file);

        if (file != null && file.exists())
            load();
    }

    /**
     * Defines the file of the config.
     *
     * @param file The config file.
     * @return The current instance.
     */
    public FileConfig in(File file)
    {
        this.file = file;
        return this;
    }

    /**
     * Gets the file of the config.
     *
     * @return The file of the config.
     */
    public File get_file()
    {
        return file;
    }

    /**
     * Checks whether the configuration saves automatically after a value set.
     *
     * @return True if the config saves automatically else false.
     */
    public boolean has_auto_save()
    {
        return auto_save;
    }

    /**
     * Sets whether the configuration saves automatically after a value set.
     *
     * @param auto_save True if the config saves automatically else false.
     */
    public void set_auto_save(boolean auto_save)
    {
        this.auto_save = auto_save;
    }

    public abstract void load();

    public abstract void save();

    @Override
    public boolean is_virtual()
    {
        return false;
    }
}
