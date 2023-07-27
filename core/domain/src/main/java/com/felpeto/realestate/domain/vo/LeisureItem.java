package com.felpeto.realestate.domain.vo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum LeisureItem {
  SWIMMING_POOL("swimming pool"),
  GYM_FITNESS_CENTER("gym & fitness center"),
  TENNIS_COURT("tennis court"),
  BASKETBALL_COURT("basketball court"),
  PLAYGROUND("playground"),
  BARBECUE_AREA("barbecue area"),
  LOUNGE_AREAS("lounge areas"),
  YOGA_STUDIO("yoga studio"),
  GAME_ROOM("game room"),
  MOVIE_THEATER("movie theater"),
  LIBRARY_READING_ROOM("library reading room"),
  SAUNA_STEAM_ROOM("sauna & steam room"),
  GREEN_SPACES_PARKS("green spaces & parks"),
  MULTI_PURPOSE_HALL("multi-purpose hall"),
  DOG_PARK_PET_PLAY_AREA("dog park & pet play area"),
  INDOOR_PLAYROOM("indoor playroom"),
  JACUZZI_HOT_TUB("jacuzzi hot tub"),
  JOGGING_WALKING_TRAILS("jogging & walking trails"),
  MINI_GOLF("mini golf"),
  VOLLEYBALL_COURT("volleyball court"),
  BILLIARDS_ROOM("billiards room"),
  PING_PONG_TABLE_TENNIS("ping pong table"),
  COMMUNITY_GARDEN("community garden"),
  ART_CRAFT_ROOM("art & craft room"),
  KARAOKE_ROOM("karaoke room"),
  GARDEN("garden"),
  PATIO("patio"),
  OUTDOOR_SEATING_AREA("outdoor seating area"),
  GREENHOUSE("greenhouse"),
  HOME_THEATER("home theater"),
  FOOSBALL_TABLE("foosball table"),
  SWING_SET("swing set"),
  TRAMPOLINE("trampoline"),
  HOME_BAR("home bar"),
  MUSIC_ROOM("music room"),
  HOBBY_WORKSHOP("hobby workshop"),
  PET_AREA("pet area");

  private static final Map<String, LeisureItem> ITEM_BY_DESCRIPTION = new HashMap<>();

  static {
    Arrays.stream(values())
        .forEach(item -> ITEM_BY_DESCRIPTION.put(item.getDescription().toLowerCase(), item));
  }

  private final String description;

  LeisureItem(final String description) {
    this.description = description;
  }

  public static LeisureItem of(final String description) {
    if (StringUtils.isBlank(description)) {
      throw new IllegalArgumentException("description is mandatory");
    }
    if (!ITEM_BY_DESCRIPTION.containsKey(description.toLowerCase())) {
      throw new IllegalArgumentException("description not found: " + description);
    }
    return ITEM_BY_DESCRIPTION.get(description.toLowerCase());
  }
}
