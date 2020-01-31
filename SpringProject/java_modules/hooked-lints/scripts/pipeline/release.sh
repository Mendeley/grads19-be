#!/bin/bash

###############################################################################
######################### RELEASE SCRIPT ######################################
###############################################################################

#
# WARNING
#
# This script is intended to be run from `make release`.
#
# DESCRIPTION
#
# If the version number in `package.json` does not exist as a Git tag,
# then that version number will become the next Git tag.
#
# If the version number in `package.json` is equal to the ***latest*** Git tag,
# then the patch number is incremented and the new version number will become the next Git tag.
#
# If the version number in `package.json` already exists as a Git tag AND is not the latest Git tag,
# then the release script is aborted.
#
# This means that in order to make a minor or major release, you must manually update the version number
# in `package.json` AND commit the change in your pull request.
#
# There is nothing to do for a patch release.
#
# When all is good, the release script will use the new version number as the next Git tag
# and publish the package to the npm registry. (Either public or private.)
#
#
#


if [[ ! -z "$(git status -s)" ]]; then
	echo "Release script: your working directory is not clean"
	echo "Release script: see Git status & diff below"
	echo
	echo
	git status
	git diff
	echo
	echo
	echo "Release script: abort"
	exit 1
fi

# Absolute path to this project package.json file
PACKAGE_JSON=$(pwd)/package.json

# Latest tag from Git.
git fetch --tags
CUR_TAG=$(git tag | tr -d v | sort -n -t. -k 1,1 -k 2,2 -k 3,3 | tail -1)

# Tag as defined in package.json
PKG_TAG=$(node -p -e "require('$PACKAGE_JSON').version")

# By default the tag as defined in package.json will be the new tag.
NEW_TAG="${PKG_TAG}"

# The version in `package.json` matches the latest Git tag
# So we assume a patch release and must bump the patch number.
if [[ "${PKG_TAG}" == "${CUR_TAG}" ]]; then
	echo "Release script: making a patch release from ${PKG_TAG}"
	PKG_TAG_MAJOR_NUMBER=$(node -p -e "require('${PACKAGE_JSON}').version.split('.')[0]") # 7 in '7.1.3'
	PKG_TAG_MINOR_NUMBER=$(node -p -e "require('${PACKAGE_JSON}').version.split('.')[1]") # 1 in '7.1.3'
	PKG_TAG_PATCH_NUMBER=$(node -p -e "require('${PACKAGE_JSON}').version.split('.')[2]") # 3 in '7.1.3'
	NEW_TAG="${PKG_TAG_MAJOR_NUMBER}.${PKG_TAG_MINOR_NUMBER}.$((PKG_TAG_PATCH_NUMBER + 1))"
	echo "Release script: patch release set to ${NEW_TAG}"
	npm --no-git-tag-version version patch
fi

# Now we check that the proposed new tag doesn't exist already.
if [[ ! -z "$(git tag | tr -d v | sort -n -t. -k 1,1 -k 2,2 -k 3,3 | grep $NEW_TAG)" ]]; then
	echo "Release script: tag ${NEW_TAG} already exists"
	echo "Release script: list of existing tags follows"
	echo
	echo
	git tag | tr -d v | sort -n -t. -k 1,1 -k 2,2 -k 3,3
	echo
	echo
	echo "Release script: abort"
	exit 1
fi

# At this stage, a non-clean working directory means that we need to commit the patch number change
if [[ ! -z "$(git status -s)" ]]; then
	git commit -a -m "Bump patch number to ${NEW_TAG}"
	git push origin HEAD:master
fi

git tag "${NEW_TAG}"
if [[ $? -ne 0 ]]; then
	echo "Release script: git tag ${NEW_TAG} has failed"
	exit 1
fi

git push origin --tags
if [[ $? -ne 0 ]]; then
	echo "Release script: git push origin --tags has failed"
	exit 1
fi

npm publish
if [[ $? -ne 0 ]]; then
	echo "Release script: publishing to npm registry has failed"
	exit 1
fi

echo "PROJECT_VERSION=${NEW_TAG}" >>.build.properties

echo "Release script: published v${NEW_TAG}"
echo "Release script: finished"
