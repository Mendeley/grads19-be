#!/usr/bin/env ../../bats/bats

load ../test_helper

cleanFakeRepo() {
	(
		cd ../tmp
		rm -Rf fake_repo.install-hooks
	)
}

createFakeRepo() {
	cleanFakeRepo
	(
		cd ../tmp
		mkdir -p fake_repo.install-hooks/.git
	)
}

assertHooks() {
	(
		local expected=$(cat <<-EOF
			#!/bin/sh

			[ ! -e "scripts/hooks/hooks.sh" ] && exit 0
			chmod u+rx "scripts/hooks/hooks.sh"
			"scripts/hooks/hooks.sh" "\${0##*/}" "\$@"
			EOF
		)
		cd "$1"
		for hook in applypatch-msg commit-msg post-applypatch post-checkout post-commit post-merge \
			post-receive post-rewrite post-update pre-applypatch pre-auto-gc pre-commit \
			prepare-commit-msg pre-push pre-rebase pre-receive push-to-checkout update; do

			expectEqual "$(cat $hook)" "$expected"
		done
	)
}

@test "install-hooks should works normaly on a normal repo" {
	createFakeRepo
	export GITHOOKSDIR=../tmp/fake_repo.install-hooks/.git/hooks
	run filter_control_sequences ../../hooks/install-hooks.sh
	assertHooks ../tmp/fake_repo.install-hooks/.git/hooks
	cleanFakeRepo
	expectEqual "$status" 0
	expectEqual "$output" "INFO(install-hooks.sh): hooks created"
}

@test "install-hooks --quick should install on a new repo" {
	createFakeRepo
	export GITHOOKSDIR=../tmp/fake_repo.install-hooks/.git/hooks
	run filter_control_sequences ../../hooks/install-hooks.sh --quick
	assertHooks ../tmp/fake_repo.install-hooks/.git/hooks
	cleanFakeRepo
	expectEqual "$status" 0
	expectEqual "$output" "INFO(install-hooks.sh): hooks created"
}

@test "install-hooks --quick should not install on an installed repo" {
	createFakeRepo

	export GITHOOKSDIR=../tmp/fake_repo.install-hooks/.git/hooks
	run filter_control_sequences ../../hooks/install-hooks.sh
	expectEqual "$status" 0
	expectEqual "$output" "INFO(install-hooks.sh): hooks created"
	run filter_control_sequences ../../hooks/install-hooks.sh --quick
	cleanFakeRepo
	expectEqual "$status" 0
	expectEqual "$output" ""
}
