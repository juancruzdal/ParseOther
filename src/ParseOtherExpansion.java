
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

      OfflinePlayer target = null;
      String input = strings[0];

      if (unsafe) {
          String resolved = PlaceholderAPI.setPlaceholders(p, "%" + input + "%");
          input = (resolved == null || resolved.contains("%") || resolved.isEmpty()) ? input : resolved;
      }

      // Intentar como UUID primero
      try {
          UUID id = UUID.fromString(input);
          OfflinePlayer found = Bukkit.getOfflinePlayer(id);
          if (found.hasPlayedBefore()) {
              target = found;
          }
      } catch (IllegalArgumentException e) {
          for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
              if (op.getName() != null && op.getName().equalsIgnoreCase(input)) {
                  target = op;
                  break;
              }
          }
      }
      if (target == null || !target.hasPlayedBefore()) {
          return "";
      }

      String placeholder = PlaceholderAPI.setPlaceholders(target, "%" + strings[1] + "%");
      if (placeholder.startsWith("%") && placeholder.endsWith("%")) {
          return "";
      }

      return placeholder;
  }


}
