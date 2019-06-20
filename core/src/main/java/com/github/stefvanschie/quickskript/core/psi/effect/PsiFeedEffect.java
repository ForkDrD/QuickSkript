package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Feeds the player by a specified amount of hunger bars.
 *
 * @since 0.1.0
 */
public class PsiFeedEffect extends PsiElement<Void> {

    /**
     * The player whose hunger should be restored
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * The amount of hunger bars to restore, may be nulll
     */
    @Nullable
    protected PsiElement<?> amount;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to whose hunger should be restored, see {@link #player}
     * @param amount the amount of hunger to restore, or null, see {@link #amount}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFeedEffect(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount,  int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.amount = amount;
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
     * A factory for creating {@link PsiFeedEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiFeedEffect> {

        /**
         * The pattern to match {@link PsiFeedEffect}s
         */
        @NotNull
        private final Pattern pattern =
            Pattern.compile("feed(?: the)? (?<player>.+?)(?: by (?<amount>.+?)(?: beefs?)?)?$");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiFeedEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            var skriptLoader = SkriptLoader.get();

            PsiElement<?> player = skriptLoader.forceParseElement(matcher.group("player"), lineNumber);

            String amountGroup = matcher.group("amount");
            PsiElement<?> amount = amountGroup == null ? null : skriptLoader.forceParseElement(amountGroup, lineNumber);

            return create(player, amount, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to feed
         * @param amount the amount of hunger bars, or null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFeedEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount, int lineNumber) {
            return new PsiFeedEffect(player, amount, lineNumber);
        }
    }
}