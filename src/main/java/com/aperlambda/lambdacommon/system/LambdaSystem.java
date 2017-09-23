/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.system;

public final class LambdaSystem
{
    private LambdaSystem() {}

    public static OS getOS()
    {
        return OS.getCurrentPlatform();
    }

    public static String getUserDirectoryStr()
    {
        return System.getProperty("user.home");
    }
}