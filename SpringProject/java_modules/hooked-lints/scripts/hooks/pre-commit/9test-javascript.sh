#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh

if [ -e package.json ] && grep -q '"test":' package.json; then
	runscript npm run test
fi
