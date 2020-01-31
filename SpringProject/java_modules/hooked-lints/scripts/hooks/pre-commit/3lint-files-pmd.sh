#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

pmdcaller() {
	$HOOKED_LINTS_DIR/scripts/tools/get-java-modules.sh
	[ ! -f pmd_override_rules.xml ] && $HOOKED_LINTS_DIR/scripts/tools/create-pmd-override-rules.sh

	java_modules/pmd-bin-6.8.0/bin/run.sh pmd -f csv -R "pmd_override_rules.xml" -d "$(joinArray , "$@")"
}
runscriptfiles "\.java\$" pmdcaller
