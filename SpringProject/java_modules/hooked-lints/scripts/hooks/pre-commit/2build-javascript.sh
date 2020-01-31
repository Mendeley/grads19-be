#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

if [ -e package.json ] && grep -q '"build":' package.json; then
	runscript npm run build
fi
