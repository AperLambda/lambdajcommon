package com.aperlambda.lambdacommon.utils.function;

@FunctionalInterface
public interface ReturnAs<I, O>
{
    /**
     * Transforms the input to the output.
     *
     * @param input Input to transform.
     * @return The result.
     */
    O from(I input);
}