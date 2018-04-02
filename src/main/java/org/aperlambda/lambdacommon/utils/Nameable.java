/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
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
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Nameable
{
	@NotNull
	String getName();
}