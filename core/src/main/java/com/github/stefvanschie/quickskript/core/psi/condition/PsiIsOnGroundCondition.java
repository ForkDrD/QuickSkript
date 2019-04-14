package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks whether an entity is on the ground. This cannot be pre computed, since entities can land or come off of the
 * ground during game play.
 *
 * @since 0.1.0
 */
public class PsiIsOnGroundCondition extends PsiElement<Boolean> {

    /**
     * The entity to check whether they are on the ground
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * False if the result of this execution needs to be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to check whether they are on the ground
     * @param positive false if the result of this execution needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsOnGroundCondition(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
        this.positive = positive;
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiIsOnGroundCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsOnGroundCondition> {

        /**
         * A pattern for matching positive {@link PsiIsOnGroundCondition}s
         */
        @NotNull
        private final Pattern positivePattern = Pattern.compile("([\\s\\S]+) (?:is|are) on (?:the )?ground");

        /**
         * A pattern for matching negative {@link PsiIsOnGroundCondition}s
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("([\\s\\S]+) (?:isn't|is not|aren't|are not) on (?:the )?ground");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsOnGroundCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> entity = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);

                return create(entity, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                PsiElement<?> entity = SkriptLoader.get().forceParseElement(negativeMatcher.group(1), lineNumber);

                return create(entity, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param entity the entity to check whether they are on the ground
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiIsOnGroundCondition create(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
            return new PsiIsOnGroundCondition(entity, positive, lineNumber);
        }
    }
}