/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.utils.function;

@FunctionalInterface
public interface ReturnAs<I, O>
{
    /**
     * Transforms the input to the output.
     *
     * @param input Input to transform.
     * @return The result.
     */
    O from(I input);
}