/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a serializable interface.
 */
public interface Serializable
{
	default @NotNull String serialize()
	{
		return toJson().toString();
	}

	/**
	 * Serializes the object into a Json object.
	 *
	 * @return The serialized object.
	 */
	@NotNull JsonObject toJson();
}