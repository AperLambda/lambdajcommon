/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
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
import org.aperlambda.lambdacommon.utils.Pair;

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
	private static final GsonBuilder BASE_GSON   = new GsonBuilder()
			.registerTypeHierarchyAdapter(ResourceName.class, new ResourceName.ResourceNameJsonSerializer())
			.registerTypeHierarchyAdapter(Pair.class, new Pair.JsonPairSerializer())
			.disableHtmlEscaping();
	public static final  Gson        GSON        = BASE_GSON.create();
	public static final  Gson        GSON_PRETTY = BASE_GSON.setPrettyPrinting().create();
	public static final  JsonParser  JSON_PARSER = new JsonParser();
}