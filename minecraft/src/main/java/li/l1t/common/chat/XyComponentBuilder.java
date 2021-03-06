/*
 * MIT License
 *
 * Copyright (c) 2016-2017 Philipp Nowak (Literallie)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package li.l1t.common.chat;

import com.google.common.base.Joiner;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * A drop-in replacement for {@link ComponentBuilder} which adds some additional convenience methods
 * in order to achieve simpler configuration.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2015-08-31
 */
public class XyComponentBuilder extends ComponentBuilder {
    private static final Joiner NEWLINE_JOINER = Joiner.on('\n');

    /**
     * Constructs a new component builder with the same data as given builder.
     *
     * @param original the builder to copy from
     */
    public XyComponentBuilder(ComponentBuilder original) {
        super(original);
    }

    /**
     * Constructs a new component builder with the same data as given builder.
     *
     * @param original the builder to copy from
     * @deprecated unnecessarily specific argument type - prefer {@link #XyComponentBuilder(ComponentBuilder)}.
     */
    @Deprecated
    public XyComponentBuilder(XyComponentBuilder original) {
        super(original);
    }

    /**
     * Constructs a new component builder with some text for the first component.
     *
     * @param text the text for the first component
     */
    public XyComponentBuilder(String text) {
        super(text);
    }

    /**
     * Constructs a new component builder with text and color for the first component.
     *
     * @param text  the text for the first component
     * @param color the color of the first component
     */
    public XyComponentBuilder(String text, ChatColor color) {
        super(text);
        color(color);
    }

    /**
     * Sets the {@link HoverEvent} with type of {@link Action#SHOW_TEXT} and the given message for
     * the current part. Use {@code \n} for newlines.
     *
     * @param legacyText the legacy text to add as tooltip
     * @return this builder for chaining
     */
    public XyComponentBuilder tooltip(String legacyText) {
        event(new HoverEvent(Action.SHOW_TEXT,
                TextComponent.fromLegacyText(legacyText)));
        return this;
    }

    /**
     * Sets the {@link HoverEvent} with type of {@link Action#SHOW_TEXT} and the given lines for the
     * current part. Formatting codes may be used.
     *
     * @param legacyLines the legacy lines to show in the tooltip
     * @return this builder for chaining
     */
    public XyComponentBuilder tooltip(String... legacyLines) {
        return tooltip(NEWLINE_JOINER.join(legacyLines));
    }

    /**
     * Sets the {@link ClickEvent} with type of {@link ClickEvent.Action#RUN_COMMAND} and the given
     * command for the current part.
     *
     * @param command the command to run on click
     * @return this builder for chaining
     */
    public XyComponentBuilder command(String command) {
        event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    /**
     * Sets the {@link ClickEvent} with type of {@link ClickEvent.Action#RUN_COMMAND} and the given
     * command for the current part and sets the {@link HoverEvent} with type of {@link
     * Action#SHOW_TEXT} with an appropriate hint text.
     *
     * @param command the command to run on click
     * @return this builder for chaining
     */
    public XyComponentBuilder hintedCommand(String command) {
        command(command);
        event(new HoverEvent(Action.SHOW_TEXT,
                new XyComponentBuilder("Hier klicken für:\n", ChatColor.YELLOW)
                        .append(command, ChatColor.GRAY)
                        .create()
        ));
        return this;
    }

    /**
     * Sets the {@link ClickEvent} with type of {@link ClickEvent.Action#SUGGEST_COMMAND} and the
     * given command for the current part.
     *
     * @param command the command to suggest in the player's chat box on click
     * @return this builder for chaining
     */
    public XyComponentBuilder suggest(String command) {
        event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        return this;
    }

    /**
     * Sets the {@link ClickEvent} with type of {@link ClickEvent.Action#OPEN_URL} and the given URL
     * for the current part.
     *
     * @param url the URL to open on click
     * @return this builder for chaining
     */
    public XyComponentBuilder openUrl(String url) {
        event(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    /**
     * Sets the {@link ClickEvent} with type of {@link ClickEvent.Action#OPEN_FILE} and the given
     * file for the current part.
     *
     * @param fileName the file name to open on click
     * @return this builder for chaining
     */
    public XyComponentBuilder openFile(String fileName) {
        event(new ClickEvent(ClickEvent.Action.OPEN_FILE, fileName));
        return this;
    }

    /**
     * Appends the text to the builder and makes it the current target for formatting.
     *
     * @param text  the text to append
     * @param color the color to set
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder append(String text, ChatColor color) {
        append(text);
        color(color);
        return this;
    }

    /**
     * Appends the text to the builder and makes it the current target for formatting. You can
     * specify the amount of formatting retained.
     *
     * @param text      the text to append
     * @param color     the color to set
     * @param retention the formatting to retain
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder append(String text, ChatColor color, FormatRetention retention) {
        append(text, retention);
        color(color);
        return this;
    }

    /**
     * Appends the text to the builder and makes it the current target for formatting.
     *
     * @param text       the text to append
     * @param color      the color to set
     * @param formatting the only formatting(s) to retain
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder append(String text, ChatColor color, ChatColor... formatting) {
        append(text, FormatRetention.EVENTS);
        color(color);
        for (ChatColor format : formatting) {
            switch (format) {
                case UNDERLINE:
                    underlined(true);
                    break;
                case STRIKETHROUGH:
                    strikethrough(true);
                    break;
                case ITALIC:
                    italic(true);
                    break;
                case BOLD:
                    bold(true);
                    break;
                case MAGIC:
                    obfuscated(true);
                    break;
                default:
                    throw new IllegalArgumentException("not a formatting code, color? " + format);
            }
        }

        return this;
    }


    /**
     * Appends an object's string value to this builder.
     *
     * @param object the object to append
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder append(Object object) { //separate Object methods for binary compat with String methods implemented earlier
        return append(String.valueOf(object));
    }

    /**
     * Appends an object's string value to the builder and makes it the current target for
     * formatting.
     *
     * @param object the object to append
     * @param color  the color to set
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder append(Object object, ChatColor color) {
        return append(String.valueOf(object), color);
    }

    /**
     * Appends an object's string value  to the builder and makes it the current target for
     * formatting.
     *
     * @param object     the object to append
     * @param color      the color to set
     * @param formatting the only formatting(s) to retain
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder append(Object object, ChatColor color, ChatColor... formatting) {
        return append(String.valueOf(object), color, formatting);
    }

    /**
     * Appends some text to this builder if a condition is met.
     *
     * @param condition true to append, false to ignore
     * @param text      the text to append
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder appendIf(boolean condition, String text) {
        if (condition) {
            append(text);
        }
        return this;
    }

    /**
     * Appends the string value of an object to this builder if a condition is met.
     *
     * @param condition true to append, false to ignore
     * @param object    the object to append
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder appendIf(boolean condition, Object object) {
        if (condition) {
            append(object);
        }
        return this;
    }

    /**
     * Appends some text to this builder if a condition is met.
     *
     * @param condition true to append, false to ignore
     * @param text      the text to append
     * @param color     the color of the text
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder appendIf(boolean condition, String text, ChatColor color) {
        if (condition) {
            append(text, color);
        }
        return this;
    }

    /**
     * Appends the string value of an object to this builder if a condition is met.
     *
     * @param condition true to append, false to ignore
     * @param object    the object to append
     * @param color     the color of the text
     * @return this ComponentBuilder for chaining
     */
    public XyComponentBuilder appendIf(boolean condition, Object object, ChatColor color) {
        if (condition) {
            append(String.valueOf(object), color);
        }
        return this;
    }

    //////////////////////////////////////////////////////////////////////////////////
    // The rest of this class is just passing calls to super while retaining
    // XyComponentBuilder return type
    //////////////////////////////////////////////////////////////////////////////////

    @Override
    public XyComponentBuilder append(String text) {
        return (XyComponentBuilder) super.append(text == null ? "null" : text);
    }

    @Override
    public XyComponentBuilder append(String text, FormatRetention retention) {
        return (XyComponentBuilder) super.append(text == null ? "null" : text, retention);
    }

    @Override
    public XyComponentBuilder color(ChatColor color) {
        return (XyComponentBuilder) super.color(color);
    }

    @Override
    public XyComponentBuilder bold(boolean bold) {
        return (XyComponentBuilder) super.bold(bold);
    }

    @Override
    public XyComponentBuilder italic(boolean italic) {
        return (XyComponentBuilder) super.italic(italic);
    }

    @Override
    public XyComponentBuilder underlined(boolean underlined) {
        return (XyComponentBuilder) super.underlined(underlined);
    }

    @Override
    public XyComponentBuilder strikethrough(boolean strikethrough) {
        return (XyComponentBuilder) super.strikethrough(strikethrough);
    }

    @Override
    public XyComponentBuilder obfuscated(boolean obfuscated) {
        return (XyComponentBuilder) super.obfuscated(obfuscated);
    }

    @Override
    public XyComponentBuilder event(ClickEvent clickEvent) {
        return (XyComponentBuilder) super.event(clickEvent);
    }

    @Override
    public XyComponentBuilder event(HoverEvent hoverEvent) {
        return (XyComponentBuilder) super.event(hoverEvent);
    }

    @Override
    public XyComponentBuilder reset() {
        return (XyComponentBuilder) super.reset();
    }

    @Override
    public XyComponentBuilder retain(FormatRetention retention) {
        return (XyComponentBuilder) super.retain(retention);
    }
}
