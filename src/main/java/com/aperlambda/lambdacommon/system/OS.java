/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.system;

import com.aperlambda.lambdacommon.utils.Nameable;

public enum OS implements Nameable
{
    WINDOWS("Windows", "win"),
    OSX("OSX", "mac"),
    LINUX("Linux", "linux"),
    SOLARIS("Solaris", "solaris", "sunos"),
    FREEBSD("FreeBSD"),
    OPENBSD("OpenBSD"),
    NETBSD("NetBSD"),
    UNKNOWN("Unknown", "unknown");

    private String   prettyName;
    private String[] designations;

    OS(String prettyName, String... designations) {
        this.prettyName = prettyName;
        this.designations = designations;
    }

    public static OS getCurrentPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        for (OS os : values())
        {
            for (String oStr : os.designations)
            {
                if (osName.contains(oStr))
                    return os;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String getName() {
        return prettyName;
    }
}