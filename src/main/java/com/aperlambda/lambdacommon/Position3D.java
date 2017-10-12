/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class Position3D extends Position2D
{
    private int z;

    public Position3D(int x, int y, int z)
    {
        super(x, y);
        this.z = z;
    }

    public int getZ()
    {
        return z;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    @Override
    public int getDimension()
    {
        return 3;
    }

    @NotNull
    @Override
    public JsonObject toJson()
    {
        JsonObject json = super.toJson();
        json.addProperty("z", z);
        return json;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Position3D that = (Position3D) o;

        return z == that.z;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + z;
        return result;
    }
}