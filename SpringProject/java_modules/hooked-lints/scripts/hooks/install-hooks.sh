#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../common/lib.sh

# Get hooks.sh path
HOOKSSCRIPT=$(dirname $(getScriptGitPath))/hooks.sh

# Get git dir (allow this variable to be injected)
[ "${GITHOOKSDIR-x}" = "x" ] && GITHOOKSDIR=$(getGitHooksDir)
[ ! -e "$GITHOOKSDIR" ] && mkdir -p $GITHOOKSDIR
cd $GITHOOKSDIR

if [ "${1-x}" = "--quick" ]; then
	if [ -e "pre-commit" ] && grep -q "$HOOKSSCRIPT" "pre-commit"; then
		exit 0
	fi
	shift
fi

if [ "$#" != 0 ]; then
	echo "Usage: $(basename $0) [--quick]"
	echo
	echo "  --quick Do a quick check before install and skip install if not needed"
	echo
	exit 0
fi

newhook() {
	cat <<-EOF
		#!/bin/sh

		[ ! -e "$HOOKSSCRIPT" ] && exit 0
		chmod u+rx "$HOOKSSCRIPT"
		"$HOOKSSCRIPT" "\${0##*/}" "\$@"
	EOF
}

for hook in applypatch-msg commit-msg post-applypatch post-checkout post-commit post-merge \
	post-receive post-rewrite post-update pre-applypatch pre-auto-gc pre-commit \
	prepare-commit-msg pre-push pre-rebase pre-receive push-to-checkout update; do

	rm -f "$hook"
	newhook >"$hook"
	chmod u+rx "$hook"
done
log INFO hooks created

exit 0
