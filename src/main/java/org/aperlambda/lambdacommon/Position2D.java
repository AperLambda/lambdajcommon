/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon;

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
		var json = new JsonObject();
		json.addProperty("dimension", getDimension());
		json.addProperty("x", x);
		json.addProperty("y", y);
		return json;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Position2D that = (Position2D) o;

		return x == that.x && y == that.y;
	}

	@Override
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		return result;
	}
}