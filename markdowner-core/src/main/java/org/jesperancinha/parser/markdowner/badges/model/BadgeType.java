package org.jesperancinha.parser.markdowner.badges.model;

public enum BadgeType {
    INFO("jeorg.badges.info.json", "Info.md"),
    BUILD("jeorg.badges.build.json", "Build.md"),
    QUALITY("jeorg.badges.quality.json", "Quality.md"),
    COVERAGE("jeorg.badges.coverage.json", "Coverage.md"),
    CONTENT("jeorg.badges.content.json", "Content.md");

    private final String badgeFile;

    private final String destinationFile;

    BadgeType(String badgeFile, String destinationFile) {
        this.badgeFile = badgeFile;
        this.destinationFile = destinationFile;
    }

    public String getBadgeFile() {
        return badgeFile;
    }

    public String getDestinationFile() {
        return destinationFile;
    }
}
