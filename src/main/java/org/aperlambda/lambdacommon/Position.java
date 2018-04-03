/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon;

import org.aperlambda.lambdacommon.documents.Element;
import org.aperlambda.lambdacommon.utils.Serializable;

public interface Position extends Serializable, Element
{
	/**
	 * Gets the dimension of the position.
	 * Example: 2 for 2D, 3 for 3D, etc...
	 *
	 * @return The dimension of the position.
	 */
	int getDimension();
}