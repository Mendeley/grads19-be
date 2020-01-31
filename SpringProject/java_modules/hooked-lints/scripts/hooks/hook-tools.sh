#!/usr/bin/env bash

. $(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd -P)/../common/lib.sh

if [ "$#" = 0 ]; then
	echo "Usage: $(basename $0) <file1> [file2] ... [filen]"
	echo
	echo "  <file1...n> The list of files to be tested"
	echo
	exit 0
fi

FILES=("$@")

# Get absolute path
HOOKED_LINTS_DIR=$(realpath $(dirname "$0")/../../..)

nx() {
	(
		CMD="$1"
		shift
		if [ -e "$HOOKED_LINTS_DIR/node_modules/.bin/$CMD" ]; then
			"$HOOKED_LINTS_DIR/node_modules/.bin/$CMD" "$@"
		elif [ -e node_modules/.bin/$CMD ]; then
			"node_modules/.bin/$CMD" "$@"
		else
			log WARN "Can't execute $CMD, not found either in $HOOKED_LINTS_DIR/node_modules/.bin/$CMD or node_modules/.bin/$CMD"
		fi
	)
}

empty() {
	ERROR="$*"
	BAD=$(cat)
	if [ "$BAD" != "" ]; then
		log ERROR "$ERROR"
		log ERROR "$BAD" | sed "s/^/        /g"
		exit 1
	fi
}

runscript() {
	log INFO "Running hook: $*"
	SCRIPT="$1"
	shift
	"$SCRIPT" "$@"
}

runscriptfiles() {
	FLAGS="-E"
	if [ "$1" = "-v" ]; then
		FLAGS="-vE"
		shift
	fi

	GREP="$1"
	SCRIPT="$2"
	shift 2
	readarray -t GREPPED <<<"$(joinArray $'\n' "${FILES[@]}" | grep $FLAGS "$GREP" | cat)"
	if [[ -n "${GREPPED[@]}" ]]; then
		log INFO "Running hook for ${#GREPPED[@]} files"
		"$SCRIPT" "$@" "${GREPPED[@]}"
	fi
}

# examples:
#   joinArray , a "b c" d
#		a,b c,d
#	joinArray $'\n' a "b c" d
#		a<newline>b c<newline>d
#   joinArray " -- " a "b c" d
#		a -- b c -- d
function joinArray() {
	local delim=$1
	shift
	echo -n "$1"
	shift
	printf "%s" "${@/#/$delim}"
}
