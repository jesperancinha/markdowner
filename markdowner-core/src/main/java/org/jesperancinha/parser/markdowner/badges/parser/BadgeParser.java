package org.jesperancinha.parser.markdowner.badges.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.badges.model.Badge;
import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgePattern;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSetting;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSettingGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgeType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class BadgeParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String BADGE_REGEX =
            "(\\[!\\[%s]\\(http[s]*:\\/\\/%s[a-zA-Z0-9\\/\\.\\]\\?\\=\\-\\&\\%%\\;\\_]*\\)]\\((http[s]*:\\/\\/)*[a-zA-Z0-9\\/\\.\\]\\=\\?\\-\\&\\%%\\;\\_]*\\))";
    private static final List<BadgeSettingGroup> badgeSettingGroups = parseSettings();

    public static Map<BadgeType, BadgeGroup> parse(final InputStream readmeInputStream) {
        final var sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(readmeInputStream))) {
            String s;
            while (null != (s = br.readLine())) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final var readmeText = sb.toString();
        return badgeSettingGroups.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(BadgeSettingGroup::getBadgeType, badgeSettingGroup -> {
                            final List<Badge> allBadges = badgeSettingGroup.getBadgeSettingList()
                                    .stream().map(badgeSetting -> {
                                        final Matcher matcher = badgeSetting.getPattern().matcher(readmeText);
                                        if (matcher.find()) {
                                            final String badgeText = matcher.group(0);
                                            return Badge.builder()
                                                    .badgeText(badgeText)
                                                    .title(badgeSetting.getTitle())
                                                    .build();
                                        }
                                        return null;
                                    })
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
                            return BadgeGroup
                                    .builder()
                                    .badgeType(badgeSettingGroup.getBadgeType())
                                    .badgeList(allBadges)
                                    .build();
                        }
                ));
    }


    static List<BadgeSettingGroup> parseSettings() {
        return Arrays.stream(BadgeType.values())
                .map(badgeType -> {
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
                })
                .collect(Collectors.toList());
    }
}
