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
 * @version 1.6.1
 * @since 1.4.6
 */
public interface ResourceNameable extends Nameable
{
    /**
     * Gets the resource name of the object.
     *
     * @return The resource name of the object.
     */
    @NotNull ResourceName get_resource_name();

    @Override
    default @NotNull String get_name()
    {
        return get_resource_name().get_name();
    }
}
