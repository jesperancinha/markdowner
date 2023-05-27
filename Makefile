b: build
build:
	mvn clean install
test:
	mvn test
local:
	mkdir -p bin
coverage-old:
	mvn clean jacoco:prepare-agent install package jacoco:report omni-coveragereporter:report
coverage:
	mvn clean jacoco:prepare-agent install jacoco:report
report:
	mvn omni-coveragereporter:report
no-test:
	mvn clean install -DskipTests
release:
	export GPG_TTY=$(tty)
	mvn clean deploy -Prelease
	mvn nexus-staging:release -Prelease
