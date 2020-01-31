#!/bin/bash

. $(cd "$(dirname "$0")" && pwd -P)/../common/lib.sh

CONF_FILE=$(dirname $INITIAL_SCRIPT_PATH)/../../conf/java/pmd_override_rules.xml

createfile() {
	FILENAME=$1
	EXISTINGFILE=$(getRepoRootPath)/$1

	if [ ! -f "$EXISTINGFILE" ]; then
		cp "$CONF_FILE" "$EXISTINGFILE"
	fi
}

createfile "pmd_override_rules.xml"
