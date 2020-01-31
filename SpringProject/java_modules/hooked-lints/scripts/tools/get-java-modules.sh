#!/bin/bash

. $(cd "$(dirname "$0")" && pwd -P)/../common/lib.sh

PMD_VERSION=6.8.0
CHECKSTYLE_VERSION=8.13

cd $(getRepoRootPath)

mkdir -p java_modules
cd java_modules

if [ ! -f "pmd-bin-$PMD_VERSION.zip" ]; then
	curl -L -o "pmd-bin-$PMD_VERSION.zip" "https://github.com/pmd/pmd/releases/download/pmd_releases%2F$PMD_VERSION/pmd-bin-$PMD_VERSION.zip"
	unzip pmd-bin-$PMD_VERSION.zip
fi

if [ ! -f "checkstyle-$CHECKSTYLE_VERSION-all.jar" ]; then
	curl -L -o "checkstyle-$CHECKSTYLE_VERSION-all.jar" "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-$CHECKSTYLE_VERSION/checkstyle-$CHECKSTYLE_VERSION-all.jar"
fi
