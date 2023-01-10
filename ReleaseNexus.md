# Markdowner Release Document

## Delivery to Sonatype Repository

### Making a release via Gradle

```shell
gradle clean build test
./gradlew publishMavenPublicationToOSSRHRepository
```

The rest manually in [Nexus Sonatype](https://oss.sonatype.org/).

This file is mandatory before a release:

`gradle.properties`:

```properties
ossrhUsername=<ossrhUsername>
ossrhPassword=<ossrhPassword>
signing.keyId=<signing.keyId>
signing.password=<signing.password>
signing.secretKeyRingFile=/Users/<user>/.gnupg/secring.gpg
```

### Generate keys

Generate the signing fields(if not already available. check with `gpg -K` first!):

```shell
gpg --full-generate-key
gpg -K
gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg
gpg --keyserver keyserver.ubuntu.com --send-keys <KEY>
```

- `gpg --full-generate-key` - Key generation
- `gpg -K` - Lists keys. The `KeyId` is the last 8 Digits of the GPG key
- `gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg` - Exports the keys to `secring.gpg`
- `gpg --keyserver keyserver.ubuntu.com --send-keys <KEY>` - Exports the key so that Sonatype can verify the release


### Load Keys

```bash
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys <your GPG key>
gpg --list-keys
export GPG_TTY=$(tty)
```

### Release via Maven

```shell
mvn clean deploy -Prelease
mvn nexus-staging:release -Prelease
```

### Repository ~/.m2/settings.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd'
          xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
    <profiles>
        <profile>
            <id>ossrh</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.passphrase>{{password}}</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>ossrh</activeProfile>
    </activeProfiles>
    <servers>
        <server>
            <id>ossrh</id>
            <username>{{username}}</username>
            <password>{{password}}</password>
        </server>
    </servers>
</settings>
```