package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The fake amount of maximum players from a ping event. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiFakeMaxPlayersExpression extends PsiElement<Integer> implements Addable, Deletable, Removable,
    Resettable, Settable {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFakeMaxPlayersExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void delete(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void reset(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A class for creating {@link PsiFakeMaxPlayersExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiFakeMaxPlayersExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] (fake|shown|displayed) max[imum] player[s] [count|amount|number|size]",
            "[the] [fake|shown|displayed] max[imum] (count|amount|number|size) of players"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiFakeMaxPlayersExpression parse(int lineNumber) {
            return create(lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFakeMaxPlayersExpression create(int lineNumber) {
            return new PsiFakeMaxPlayersExpression(lineNumber);
        }
    }
}
