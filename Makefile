all: help
SHELL=/bin/bash
.SECONDARY:
.DELETE_ON_ERROR:

RED=\033[1;31m
GREEN=\033[1;32m
YELLOW=\033[1;33m
BLUE=\033[1;34m
RESET=\033[0m

TAG = pagexml2tei
SHADOW_JAR=./pagexml2tei/build/libs/pagexml2tei-1.0-SNAPSHOT-all.jar
NEWER_SOURCE_FILES=$(shell find pagexml2tei/src/main -newer $(SHADOW_JAR) -type f)

.make:
	mkdir -p .make

~/bin/:
	mkdir -p ~/bin

~/bin/pagexml2tei: ~/bin/ ./pagexml2tei/src/main/bash/pagexml2tei.sh ~/libs/pagexml2tei.jar
	cp -a ./pagexml2tei/src/main/bash/pagexml2tei.sh ~/bin/pagexml2tei
	@touch $@

~/libs:
	mkdir -p ~/libs

~/libs/pagexml2tei.jar: $(SHADOW_JAR) ~/libs
	cp $(SHADOW_JAR) ~/libs/pagexml2tei.jar

$(SHADOW_JAR): pagexml2tei/build.gradle.kts settings.gradle.kts $(NEWER_SOURCE_FILES)
	./gradlew shadowJar
	@echo
	@touch $@

.PHONY: shadow-jar
shadow-jar:
	@make $(SHADOW_JAR)

.PHONY: test
test:
	./gradlew test

.PHONY: clean
clean:
	./gradlew clean

.PHONY: install
install: ~/bin/pagexml2tei

.PHONY: help
help:
	@echo -e "make-tools for $(GREEN)$(TAG)$(RESET)"
	@echo
	@echo -e "Please use \`$(YELLOW)make <target>$(RESET)', where $(YELLOW)<target>$(RESET) is one of:"
	@echo -e "  $(BLUE)clean$(RESET)         - to clear the build files"
	@echo -e "  $(BLUE)test$(RESET)         - to test the projepagexml2tei"
	@echo -e "  $(BLUE)shadow-jar$(RESET)    - to build the shadow jar build/libs/elabpagexml2teil.jar"
	@echo -e "  $(BLUE)install$(RESET)       - to install the shadow jar in ~/libs and batch script 'pagexml2tei' in ~/bin/"
