#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

LIMIT=2M

testBigFiles() {
	find "$@" -size +$LIMIT 2>/dev/null | empty "Files staged for commit bigger than the $LIMIT limit:"
}

runscriptfiles ".*" testBigFiles
