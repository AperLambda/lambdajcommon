package com.aperlambda.lambdacommon;

import com.aperlambda.lambdacommon.utils.Serializable;

public interface Position extends Serializable
{
    /**
     * Gets the dimension of the position.
     * Example: 2 for 2D, 3 for 3D, etc...
     * @return The dimension of the position.
     */
    int getDimension();
}