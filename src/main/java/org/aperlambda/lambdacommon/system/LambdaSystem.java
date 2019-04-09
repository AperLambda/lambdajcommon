/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
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
    private static final File userDir = new File(get_user_dir_str());

    private LambdaSystem()
    {}

    public static OS get_os()
    {
        return OS.get_current_platform();
    }

    public static String get_user_dir_str()
    {
        return System.getProperty("user.home");
    }

    public static File get_user_dir()
    {
        return userDir;
    }
}