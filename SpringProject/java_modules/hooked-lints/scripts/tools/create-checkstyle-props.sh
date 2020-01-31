#!/bin/bash

. $(cd "$(dirname "$0")" && pwd -P)/../common/lib.sh

JAVACONFPATH=$(dirname $(getScriptGitPath))/../../conf/java

createfile() {
	EXISTINGFILE=$(getRepoRootPath)/$1

	echo "checkstyle.suppressions.file=$JAVACONFPATH/checkstyle_supressions.xml" >|"$EXISTINGFILE.hooked-lints"

	if [ ! -f "$EXISTINGFILE" ]; then
		mv "$EXISTINGFILE.hooked-lints" "$EXISTINGFILE"
	elif diff "$EXISTINGFILE" "$EXISTINGFILE.hooked-lints" >/dev/null; then
		rm "$EXISTINGFILE.hooked-lints"
	fi
}

createfile "checkstyle.properties"
