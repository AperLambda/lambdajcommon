/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

/**
 * Represents a cancellable object.
 */
public interface Cancellable
{
    /**
     * Checks whether cancelled.
     * @return True if cancelled, else false.
     */
    boolean is_cancelled();

    /**
     * Sets whether cancelled or not.
     * @param cancelled True if cancelled, else false.
     */
    void set_cancelled(boolean cancelled);
}
