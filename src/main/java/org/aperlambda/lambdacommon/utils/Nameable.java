/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a nameable interface.
 *
 * @version 1.6.0
 * @since 1.0.0
 */
public interface Nameable
{
    /**
     * Gets the name of the object.
     *
     * @return The name of the object.
     */
    @NotNull String get_name();
}