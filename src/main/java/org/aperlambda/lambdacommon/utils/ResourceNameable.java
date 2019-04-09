/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

import org.aperlambda.lambdacommon.resources.ResourceName;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object with a resource name.
 *
 * @version 1.6.0
 * @since 1.4.6
 */
public interface ResourceNameable extends Nameable
{
    /**
     * Gets the resource name of the object.
     *
     * @return The resource name of the object.
     */
    @NotNull ResourceName get_ressource_name();

    @Override
    default @NotNull String get_name()
    {
        return get_ressource_name().get_name();
    }
}
