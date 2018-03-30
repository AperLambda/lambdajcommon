/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.aperlambda.lambdacommon.resources.ResourceName;

import java.awt.*;

public class LambdaConstants
{
    /*
        Resources
     */
    public static final ResourceName RESOURCE_INVALID   = ResourceName.RESOURCE_INVALID;
    public static final ResourceName RESOURCE_NOT_FOUND = ResourceName.RESOURCE_NOT_FOUND;

    /*
        Colors
     */
    public static final Color BACKGROUND_CRASH_COLOR = new Color(0, 0, 107);

    /*
        JSON
     */
    public static final Gson       GSON        = new GsonBuilder().disableHtmlEscaping().create();
    public static final Gson       GSON_PRETTY = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final JsonParser JSON_PARSER = new JsonParser();
}