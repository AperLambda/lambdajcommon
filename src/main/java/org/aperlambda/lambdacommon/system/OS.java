/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.system;

import org.aperlambda.lambdacommon.utils.Nameable;
import org.jetbrains.annotations.NotNull;

/**
 * Lists all the operating systems which can be identified by {@code lambdajcommon}
 */
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

    private String   pretty_name;
    private String[] designations;

    OS(String pretty_name, String... designations)
    {
        this.pretty_name = pretty_name;
        this.designations = designations;
    }

    public static OS get_current_platform()
    {
        String os_name = System.getProperty("os.name").toLowerCase();
        for (OS os : values()) {
            for (String o_str : os.designations) {
                if (os_name.contains(o_str))
                    return os;
            }
        }
        return UNKNOWN;
    }

    @NotNull
    @Override
    public String get_name()
    {
        return this.pretty_name;
    }
}
