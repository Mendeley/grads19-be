#!/usr/bin/env bash

set -euCE

# ANSI escape code colors
RED="$(echo -e "\033[0;31m")"
YELLOW="$(echo -e "\033[0;33m")"
CYAN="$(echo -e "\033[0;36m")"
WHITE="$(echo -e "\033[1;37m")"
RESET="$(echo -e "\033[0m")"

# For compatibility with OSX, create a realpath equivalent
# Note, this does not resolve symlinks
fake_realpath() {
	echo "$(cd "$(dirname "$1")" && pwd -P)/$(basename "$1")"
}

if ! which realpath >/dev/null; then
realpath() {
	fake_realpath "$@"
}
fi

INITIAL_SCRIPT_PATH=$(realpath "$0")

# log INFO/WARN/ERROR
log() {
	local status="$1"
	shift
	local color="$RED"
	[ "INFO" = "$status" ] && color="$CYAN"
	[ "WARN" = "$status" ] && color="$YELLOW"
	echo "$@" | sed "s/^/${color}${status}($(basename $0)):${WHITE} /g" | sed "s/$/${RESET}/g" >&2
}

# Unrecoverable errors
error() {
	log ERROR $@
	exit 1
}

# get the path of the repo we are in
getRepoRootPath() {
	local gitPath="$INITIAL_SCRIPT_PATH"
	while [ ! -e "$gitPath/.git" ]; do
		gitPath=$(dirname "$gitPath")
		[ "$gitPath" != "/" ] || error Not in a git repository
	done
	echo $gitPath
}

# get the current path of this script relative to the root of the repo
getScriptGitPath() {
	# this has to be done in 2 steps as a local declararion with an assigment from a Command Substitution does not return exit status
	local repoRootPath
	repoRootPath=$(getRepoRootPath)

	# Validate absolute path
	echo "$INITIAL_SCRIPT_PATH" | grep -q "^$repoRootPath" || error Script not inside a git repository
	echo "$INITIAL_SCRIPT_PATH" | sed "s#^$repoRootPath/##g"
}

# get the path of git hooks in the current repo
getGitHooksDir() {
	# this has to be done in 2 steps as a local declararion with an assigment from a Command Substitution does not return exit status
	local gitPath
	gitPath=$(getRepoRootPath)
	local gitDir="$gitPath/.git"
	if [ -f "$gitDir" ]; then
		gitDir="$gitPath/$(cat "$gitDir" | sed "s/gitdir: //g")"
		gitDir=$(realpath "$gitDir")
	fi
	echo $gitDir/hooks
}
