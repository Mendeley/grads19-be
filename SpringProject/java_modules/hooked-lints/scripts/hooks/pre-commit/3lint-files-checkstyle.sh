#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

checkstylecaller() {
	$HOOKED_LINTS_DIR/scripts/tools/get-java-modules.sh
	[ ! -f checkstyle.properties ] && $HOOKED_LINTS_DIR/scripts/tools/create-checkstyle-props.sh

	java -jar java_modules/checkstyle-8.13-all.jar -p "checkstyle.properties" -c "$HOOKED_LINTS_DIR/conf/java/checkstyle_check.xml" "$@"
}
runscriptfiles "\.java\$" checkstylecaller
