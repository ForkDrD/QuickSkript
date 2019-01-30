package com.github.stefvanschie.quickskript.core.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates the absolute value of a given number
 *
 * @since 0.1.0
 */
public class PsiAbsoluteValueFunction extends PsiElement<Double> {

    /**
     * The parameter given for this element
     */
    private PsiElement<?> parameter;

    /**
     * Creates the absolute value function
     *
     * @param parameter the parameter for calculating this value
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiAbsoluteValueFunction(PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

        this.parameter = parameter;

        if (this.parameter.isPreComputed()) {
            preComputed = executeImpl(null);
            this.parameter = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Double executeImpl(@Nullable Context context) {
        return Math.abs(parameter.execute(context, Number.class).doubleValue());
    }

    /**
     * The factory for creating {@link PsiAbsoluteValueFunction}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiAbsoluteValueFunction> {

        /**
         * The pattern for matching absolute value functions
         */
        private final Pattern pattern = Pattern.compile("abs\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiAbsoluteValueFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression, lineNumber);

            return create(element, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param element the elment to compute
         * @param lineNumber the lien number
         * @return the function
         * @since 0.1.0
         */
        @NotNull
        protected PsiAbsoluteValueFunction create(PsiElement<?> element, int lineNumber) {
            return new PsiAbsoluteValueFunction(element, lineNumber);
        }
    }
}