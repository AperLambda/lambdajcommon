/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.utils;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface Serializable<T>
{
    default String serializeToString()
    {
        return serialize().toString();
    }

    @NotNull
    T serialize();

    @NotNull
    JsonObject toJson();
}