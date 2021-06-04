package org.jesperancinha.parser.markdowner.badges.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.badges.model.Badge;
import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgePattern;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSetting;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSettingGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgeType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class BadgeParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BADGE_REGEX =
            "(\\[!\\[%s]\\(http[s]*:\\/\\/%s[sa-zA-Z0-9\\/\\.\\]\\?\\=\\-\\&\\%%\\;\\_\\#\\:\\@\\\"\\ ]*\\)]\\((http[s]*:\\/\\/)*[a-zA-Z0-9\\/\\.\\]\\=\\?\\-\\&\\%%\\;\\_\\#\\:\\@\\\"\\ ]*\\))";
    private static final Pattern NOT_ACCEPTED_REGEX =
            Pattern.compile("color=(?!(informational)).");
    public static final Map<String, BadgeType> badgeTypes = parseBadgeTypes();
    private static Map<String, BadgeType> parseBadgeTypes() {
        try {
            return Arrays.stream(objectMapper.readValue(BadgeParser.class.getResourceAsStream("/jeorg.badges.types.json")
                    , BadgeType[].class))
                    .collect(Collectors.toMap(BadgeType::getType, badgeType -> badgeType));
        } catch (IOException e) {
            log.error("Error!", e);
            System.exit(1);
        }
        return null;
    }

    public static final Map<BadgeType, BadgeSettingGroup> badgeSettingGroups = parseSettings();

    public static Map<BadgeType, BadgeGroup> parse(final String readmeText) {
        return badgeSettingGroups
                .values()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(BadgeSettingGroup::getBadgeType, badgeSettingGroup -> {
                            final Map<Pattern, Badge> allBadges = badgeSettingGroup
                                    .getBadgeSettingList()
                                    .stream()
                                    .filter(Objects::nonNull)
                                    .collect(HashMap::new, (map, badgeSetting) -> {
                                        final Matcher matcher = badgeSetting.getPattern().matcher(readmeText);
                                        if (matcher.find()) {
                                            final String badgeText = matcher.group(0);
                                            final Matcher matcher1 = NOT_ACCEPTED_REGEX.matcher(badgeText);
                                            if (matcher1.find()) {
                                                map.put(badgeSetting.getPattern(), null);
                                            } else {
                                                map.put(badgeSetting.getPattern(), Badge.builder()
                                                        .badgeText(badgeText)
                                                        .title(badgeSetting.getTitle())
                                                        .build());
                                            }
                                        } else {
                                            map.put(badgeSetting.getPattern(), null);
                                        }
                                    }, HashMap::putAll);


                            return BadgeGroup
                                    .builder()
                                    .badgeType(badgeSettingGroup.getBadgeType())
                                    .badgeHashMap(allBadges)
                                    .build();
                        }
                ));
    }


    static Map<BadgeType, BadgeSettingGroup> parseSettings() {
        return  badgeTypes.values().stream()
                .collect(Collectors.toMap(badgeType -> badgeType, badgeType -> {
                    try {
                        final var badgeSettingList = Arrays.stream(objectMapper.readValue(
                                BadgeParser.class.getResourceAsStream("/".concat(badgeType.getBadgeFile())),
                                BadgeSetting[].class)).collect(Collectors.toList());
                        return BadgeSettingGroup
                                .builder()
                                .badgeType(badgeType)
                                .badgeSettingList(
                                        badgeSettingList
                                                .stream().map(badgeSetting ->
                                                BadgePattern.builder()
                                                        .title(badgeSetting.getTitle())
                                                        .pattern(Pattern.compile(
                                                                String.format(BADGE_REGEX,
                                                                        badgeSetting.getBadge(),
                                                                        badgeSetting.getCodePrefix()
                                                                                .replace(".", "\\.")
                                                                                .replace("/", "\\/")
                                                                )))
                                                        .build())
                                                .collect(Collectors.toList())
                                )
                                .build();
                    } catch (IOException e) {
                        log.error("Error", e);
                    }
                    return null;
                }));
    }
}
