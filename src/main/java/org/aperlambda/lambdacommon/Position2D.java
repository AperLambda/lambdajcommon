/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a position in a 2 dimensions coordinate system.
 *
 * @see Position
 * @see Position3D
 */
public class Position2D implements Position
{
    private int x;
    private int y;

    /**
     * Creates a new position object in 2D.
     *
     * @param x The value of the coordinate X.
     * @param y The value of the coordinate Y.
     * @return A new position in 2D.
     * @see Position2D#Position2D(int, int)
     */
    public static Position2D of(int x, int y)
    {
        return new Position2D(x, y);
    }

    public Position2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int get_x()
    {
        return x;
    }

    public void set_x(int x)
    {
        this.x = x;
    }

    public int get_y()
    {
        return y;
    }

    public void set_y(int y)
    {
        this.y = y;
    }

    @Override
    public int get_dimensions()
    {
        return 2;
    }

    @NotNull
    @Override
    public JsonObject to_json()
    {
        var json = new JsonObject();
        json.addProperty("dimension", get_dimensions());
        json.addProperty("x", x);
        json.addProperty("y", y);
        return json;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position2D other = (Position2D) o;

        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}