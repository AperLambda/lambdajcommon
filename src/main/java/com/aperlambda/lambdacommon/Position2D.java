package com.aperlambda.lambdacommon;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class Position2D implements Position
{
    private int x;
    private int y;

    public Position2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public int getDimension()
    {
        return 2;
    }

    @NotNull
    @Override
    public JsonObject toJson()
    {
        JsonObject json = new JsonObject();
        json.addProperty("dimension", getDimension());
        json.addProperty("x", x);
        json.addProperty("y", y);
        return json;
    }
}