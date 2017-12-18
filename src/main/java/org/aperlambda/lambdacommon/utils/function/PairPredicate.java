/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils.function;

@FunctionalInterface
public interface PairPredicate<X, Y>
{
    /**
     * Evaluates this predicate on the given argument.
     *
     * @param x The first input argument.
     * @param y The second input argument.
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}.
     */
    boolean test(X x, Y y);

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return A predicate that represents the logical negation of this
     * predicate.
     */
    default PairPredicate<X, Y> negate()
    {
        return (x, y) -> !test(x, y);
    }
}