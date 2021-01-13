package io.github.aluria.common.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Lore implements Cloneable {

  private String[] content;

  public static Lore of(String... content) {
    return new Lore(content);
  }

  private Lore(String[] content) {
    this.content = content;
  }

  public Lore addLines(String... lines) {
    content = ArrayUtils.addAll(content, lines);
    return this;
  }

  public Lore transform(Function<String, String> transformer) {
    for (int i = 0; i < content.length; i++) {
      content[i] = transformer.apply(content[i]);
    }
    return this;
  }

  public Lore colorize() {
    return transform(this::colorize);
  }

  public Lore replace(String placeHolder, String valueString) {
    return transform(line -> line.replace(placeHolder, valueString));
  }

  public Lore space(int startSpace, int endSpace) {
    String startSpaceLabel = Strings.repeat(" ", startSpace);
    String endSpaceLabel = Strings.repeat(" ", endSpace);

    return transform(line -> startSpaceLabel + line + endSpaceLabel);
  }

  public Lore replace(String placeHolder, Object object) {
    return replace(placeHolder, String.valueOf(object));
  }

  public List<String> toList() {
    return Arrays.asList(content);
  }

  public String[] toArray() {
    return content;
  }

  protected String colorize(String input) {
    return ChatColor.translateAlternateColorCodes('&', input);
  }

  @Override
  public Lore clone() {
    return new Lore(content);
  }
}
