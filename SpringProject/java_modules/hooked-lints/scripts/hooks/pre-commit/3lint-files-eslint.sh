#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh


runscriptfiles "\.(ts|js|tsx|jsx)\$" nx eslint --ignore-pattern '!.*'
