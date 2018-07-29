package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the natural logarithm of the given number
 *
 * @since 0.1.0
 */
public class PsiNaturalLogarithmFunction implements PsiElement<Double> {

    /**
     * The parameter for calculating the given number
     */
    private PsiElement<Number> parameter;

    /**
     * Creates a new naturla logarithm function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiNaturalLogarithmFunction(PsiElement<Number> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double execute() {
        return Math.log(parameter.execute().doubleValue());
    }

    /**
     * A factory for creating natural logarithm functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiNaturalLogarithmFunction> {

        /**
         * The pattern for matching cosine expressions
         */
        private static final Pattern PATTERN = Pattern.compile("ln\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiNaturalLogarithmFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<Number> element = (PsiElement<Number>) PsiElementFactory.parseText(expression, Number.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiNaturalLogarithmFunction(element);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiNaturalLogarithmFunction.class, Double.class);
    }
}
