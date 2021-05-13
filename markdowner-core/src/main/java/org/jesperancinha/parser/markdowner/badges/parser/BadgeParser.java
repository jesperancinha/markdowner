package org.jesperancinha.parser.markdowner.badges.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.badges.model.BadgeGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgePattern;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSetting;
import org.jesperancinha.parser.markdowner.badges.model.BadgeSettingGroup;
import org.jesperancinha.parser.markdowner.badges.model.BadgeType;
import org.jesperancinha.parser.markdowner.helper.TemplateParserHelper;
import org.jesperancinha.parser.markdowner.model.Paragraphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class BadgeParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<BadgeGroup> parse(final InputStream readmeInputStream) {
        final var badgeSettingGroups = parseSettings();
        badgeSettingGroups.stream().map(badgeSettingGroup ->
            badgeSettingGroup.getBadgeSettingList()
                    .stream().map(badgeSetting -> {
                      return null;
            })
        );
        try (BufferedReader br = new BufferedReader(new InputStreamReader(readmeInputStream))) {
            String s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                                                                String.format("\\(\\[!\\[%s]\\(http.*%s.*\\)",
                                                                        badgeSetting.getBadge(),
                                                                        badgeSetting.getCodePrefix().replace(".", "\\."))))
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
