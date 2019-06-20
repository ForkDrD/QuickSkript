package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Hides/Shows player info when pinging the server
 *
 * @since 0.1.0
 */
public class PsiPlayerInfoEffect extends PsiElement<Void> {

    /**
     * If true, the information will be shown, otherwise the information will be hidden
     */
    protected boolean show;

    /**
     * Creates a new element with the given line number
     *
     * @param show whether the information should be shown or hidden, see {@link #show}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiPlayerInfoEffect(boolean show, int lineNumber) {
        super(lineNumber);

        this.show = show;
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiPlayerInfoEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiPlayerInfoEffect> {

        /**
         * The pattern for matching effects that should show the information
         */
        @NotNull
        private final Pattern showPattern = Pattern.compile(
            "(?:show|reveal)(?: all)? player(?: related)? info(?:rmation)?(?: (?:in|to|on|from)(?: the)? server list)?"
        );

        /**
         * The pattern for matching effects that should hide the information
         */
        @NotNull
        private final Pattern hidePattern = Pattern
            .compile("hide(?: all)? player(?: related)? info(?:rmation)?(?: (?:in|to|on|from)(?: the)? server list)?");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiPlayerInfoEffect tryParse(@NotNull String text, int lineNumber) {
            var showMatcher = showPattern.matcher(text);

            if (showMatcher.matches()) {
                return create(true, lineNumber);
            }

            var hideMatcher = hidePattern.matcher(text);

            if (hideMatcher.matches()) {
                return create(false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param show true to show info, false to hide it
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiPlayerInfoEffect create(boolean show, int lineNumber) {
            return new PsiPlayerInfoEffect(show, lineNumber);
        }
    }
}