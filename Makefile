b: build
build:
	mvn clean install
test:
	mvn test
local:
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
release:
	export GPG_TTY=$(tty)
	mvn clean deploy -Prelease
	mvn nexus-staging:release -Prelease
