package org.jesperancinha.parser.markdowner.badges.model;

public enum BadgeType {
    INFO("jeorg.badges.info.json"),
    BUILD("jeorg.badges.build.json"),
    QUALITY("jeorg.badges.quality.json"),
    COVERAGE("jeorg.badges.coverage.json"),
    CONTENT("jeorg.badges.content.json");

    private String badgeFile;

    BadgeType(String badgeFile) {
        this.badgeFile = badgeFile;
    }
}
