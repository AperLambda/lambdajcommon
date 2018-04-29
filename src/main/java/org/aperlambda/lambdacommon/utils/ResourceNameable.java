package org.aperlambda.lambdacommon.utils;

import org.aperlambda.lambdacommon.resources.ResourceName;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an object with a resource name.
 *
 * @version 1.4.6
 * @since 1.4.6
 */
public interface ResourceNameable extends Nameable
{
	/**
	 * Gets the resource name of the object.
	 *
	 * @return The resource name of the object.
	 */
	@NotNull ResourceName getResourceName();

	@Override
	default @NotNull String getName()
	{
		return getResourceName().getName();
	}
}
