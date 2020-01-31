#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

checkSpaces() {
	readarray -t GREPPED <<<"$(joinArray $'\n' "$@" | grep -vE '(package(-lock)?.json|/vendor/)' | cat)"
	if [[ -n "${GREPPED[@]}" ]]; then
		grep -l "^	* [^\*]" "${GREPPED[@]}" | empty "Files with indentation spaces:"

		grep -l "[	 ]$" "${GREPPED[@]}" | empty "Files with trailing spaces:"
	fi
}

runscriptfiles "\.(tex|scss|json|js|i18n|html|es6)\$" checkSpaces

