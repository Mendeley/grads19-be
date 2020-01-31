#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh


runscriptfiles "\.(sass|scss|css)\$" nx stylelint
