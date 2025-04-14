
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class ParseOtherExpansion extends PlaceholderExpansion {
  
  @Override
  public String getAuthor() {
    return "cj89898";
  }
  
  @Override
  public String getIdentifier() {
    return "parseother";
  }
  
  @Override
  public String getVersion() {
    return "2.0.0";
  }
  
  @SuppressWarnings("deprecation")
  @Override
  public String onRequest(OfflinePlayer p, String s) {
    boolean unsafe = false;
    if (s.startsWith("unsafe_")) {
      s = s.substring(7);
      unsafe = true;
    }

    String[] strings = s.split("(?<!\\\\)\\}_", 2);
    strings[0] = strings[0].substring(1);
    strings[0] = strings[0].replaceAll("\\\\}_", "}_");
    strings[1] = strings[1].substring(1, strings[1].length() - 1);

    OfflinePlayer player;

    if (unsafe) {
      String user = PlaceholderAPI.setPlaceholders(p, ("%" + strings[0] + "%"));

      if (user == null || user.isEmpty() || user.contains("%")) {
        return "";
      }

      try {
        UUID id = UUID.fromString(user);
        player = Bukkit.getOfflinePlayer(id);
        if (player.getName() == null)
          player = Bukkit.getOfflinePlayer(user);
      } catch (IllegalArgumentException e) {
        if (user.trim().isEmpty()) {
          return "";
        }
        player = Bukkit.getOfflinePlayer(user);
      }

    } else {
      String user = strings[0];
      try {
        UUID id = UUID.fromString(user);
        player = Bukkit.getOfflinePlayer(id);
        if (player.getName() == null)
          player = Bukkit.getOfflinePlayer(user);
      } catch (IllegalArgumentException e) {
        player = Bukkit.getOfflinePlayer(user);
      }
    }

    String placeholder = PlaceholderAPI.setPlaceholders(player, "%" + strings[1] + "%");

    if (placeholder.startsWith("%") && placeholder.endsWith("%")) {
      return "";
    }

    return placeholder;
  }

}
