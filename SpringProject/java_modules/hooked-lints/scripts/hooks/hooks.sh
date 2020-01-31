#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../common/lib.sh

help() {
	echo "Usage: $(basename $0) <hook-name> [--all]"
	echo
	echo "  <hook-name> The name of the hook to test"
	echo "  --all Run the hooks on all files in the repository (as oposed of just the staged files for commit)"
	echo
	exit 0

}

[ "$#" = 0 ] && help

# Specified hook
hook="${1-x}"
shift

# change to git root
cd $(getRepoRootPath)

# locate hook path
HOOKSDIR="$(dirname $(getScriptGitPath))/$hook"

# Test if hook exists
if [ ! -d "$HOOKSDIR" ]; then
	log INFO No hooks dir for $hook
	exit 0
fi

if [ "${1-x}" = "--all" ]; then
	readarray -t FILES <<<"$(git ls-files)"
	shift
else
	readarray -t FILES <<<"$(git diff --cached --name-status | awk '$1 != "D" { print ($3 == "")?$2:$3 }')"
fi

[ "$#" != 0 ] && help

[[ -z "${FILES[@]}" ]] && exit 0

FAILED=0
for hook in $(find "$HOOKSDIR" -type f | sort); do
	chmod u+rx "$hook"
	if ! echo -n | $hook "${FILES[@]}" 2>&1; then
		log ERROR
		log ERROR "Hook failed: $hook"
		log ERROR
		if [ "${1-x}" != "all" ]; then
			exit 1
		fi
		FAILED=$((FAILED + 1))
	fi
done
if [ "$FAILED" != "0" ]; then
	error "$FAILED hook(s) failed."
fi

exit 0
