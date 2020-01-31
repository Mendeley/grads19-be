#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

prettiercaller() {
	if git diff --name-status "$@" | grep -q "."; then
		echo "WARNING: Files with unstaged changed cannot be formated:"
		(
			echo "$@"
			git diff --name-status | awk '{ print $2 }'
		) | sort | uniq -d
	else
		nx prettier --write "$@" >/dev/null
	fi
	nx prettier --list-different "$@" | empty "File not formatted by prettier:"
	git add "$@"
}

runscriptfiles "\.(ts|js|tsx|jsx|json|md)\$" prettiercaller
