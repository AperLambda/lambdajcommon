/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
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
 * @author LambdAurora
 * @version 1.3.0
 * @since 1.3.0
 */
public abstract class FileConfig implements Config
{
    protected File    file;
    protected boolean autoSave = false;

    public FileConfig()
    {}

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
    public File getFile()
    {
        return file;
    }

    /**
     * Checks whether the configuration saves automatically after a value set.
     *
     * @return True if the config saves automatically else false.
     */
    public boolean hasAutoSave()
    {
        return autoSave;
    }

    /**
     * Sets whether the configuration saves automatically after a value set.
     *
     * @param autoSave True if the config saves automatically else false.
     */
    public void setAutoSave(boolean autoSave)
    {
        this.autoSave = autoSave;
    }

    public abstract void load();

    public abstract void save();

    @Override
    public boolean isVirtual()
    {
        return false;
    }
}