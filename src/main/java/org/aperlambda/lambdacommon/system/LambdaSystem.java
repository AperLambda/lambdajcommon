/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.system;

import java.io.File;

public final class LambdaSystem
{
    private static final File userDir = new File(getUserDirectoryStr());

    private LambdaSystem() {}

    public static OS getOS()
    {
        return OS.getCurrentPlatform();
    }

    public static String getUserDirectoryStr()
    {
        return System.getProperty("user.home");
    }

    public static File getUserDirectory()
    {
        return userDir;
    }
}