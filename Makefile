XIC_BUILD=./xic-build
GRADLE_SETUP_FILES=build.gradle settings.gradle gradlew make_jar_executable.sh gradle

RED_COLOR='\033[0;31m'
NO_COLOR='\033[0m'

default: build

.PHONY: build
build:
	$(XIC_BUILD)

zip:
	zip -r xic.zip $(GRADLE_SETUP_FILES) lib Makefile src xic-build