#!/usr/bin/env ../../bats/bats

load ../test_helper

cleanFakeRepos() {
	(
		cd ../tmp
		rm -Rf fake_repo.lib
	)
}

createFakeRepos() {
	cleanFakeRepos
	(
		cd ../tmp
		mkdir -p fake_repo.lib/.git/modules/subrepos/subrepo/
		mkdir -p fake_repo.lib/subrepos/subrepo
		echo "gitdir: ../../.git/modules/subrepos/subrepo" >fake_repo.lib/subrepos/subrepo/.git
	)
}


@test "In lib.sh log should format output" {
	RED="$(echo -e "\033[0;31m")"
	YELLOW="$(echo -e "\033[0;33m")"
	CYAN="$(echo -e "\033[0;36m")"
	WHITE="$(echo -e "\033[1;37m")"
	RESET="$(echo -e "\033[0m")"

	run runlib ../../common/lib.sh log INFO test info
	expectEqual "$status" 0
	expectEqual "$output" "${CYAN}INFO(bats-exec-test):${WHITE} test info${RESET}"

	run runlib ../../common/lib.sh log WARN test warn
	expectEqual "$status" 0
	expectEqual "$output" "${YELLOW}WARN(bats-exec-test):${WHITE} test warn${RESET}"

	run runlib ../../common/lib.sh log ERROR test error
	expectEqual "$status" 0
	expectEqual "$output" "${RED}ERROR(bats-exec-test):${WHITE} test error${RESET}"
}

@test "In lib.sh error should log an error and exit" {
	run filter_control_sequences runlib ../../common/lib.sh error test
	expectEqual "$status" 1
	expectEqual "$output" "ERROR(bats-exec-test): test"
}

@test "In lib.sh fake_realpath should remove indirestions" {
	mkdir -p ../tmp/test_realpath/subdir
	local expected=$(runlib ../../common/lib.sh fake_realpath ../tmp/test_realpath/subdir)
	run runlib ../../common/lib.sh fake_realpath ../tmp/test_realpath/../test_realpath/subdir
	(cd ../tmp; rmdir -p test_realpath/subdir)

	expectEqual "$status" 0
	expectEqual "$output" "$expected"
	echo "$output" | grep -q tmp/test_realpath/subdir
}

@test "In lib.sh getRepoRootPath should find the correct repo path" {
	local expected=$(cd ../../../; pwd)
	run runlib ../../common/lib.sh getRepoRootPath
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}

@test "In lib.sh getRepoRootPath should not find any repo on /tmp" {
	realpath() { echo /tmp; }
	run filter_control_sequences runlib ../../common/lib.sh getRepoRootPath
	expectEqual "$status" 1
	expectEqual "$output" "ERROR(bats-exec-test): Not in a git repository"
}

@test "In lib.sh getScriptGitPath should return the relative script path inside the repository" {
	run filter_control_sequences runlib ../../common/lib.sh getScriptGitPath
	expectEqual "$status" 0
	expectEqual "$output" "scripts/bats-core/libexec/bats-core/bats-exec-test"
}

@test "In lib.sh getScriptGitPath should return an error if script not inside a git repository" {
	runGetScriptGitPath() {
		realpath() { echo /test/script;	}
		getRepoRootPath() { echo /tmp; }
		getScriptGitPath
	}
	run filter_control_sequences runlib ../../common/lib.sh runGetScriptGitPath
	expectEqual "$status" 1
	expectEqual "$output" "ERROR(bats-exec-test): Script not inside a git repository"
}

@test "In lib.sh getGitHooksDir should return the hooks dir on a normal git repo" {
	createFakeRepos
	runGetGitHooksDir() {
		getRepoRootPath() { echo ../tmp/fake_repo.lib; }
		getGitHooksDir
	}
	run filter_control_sequences runlib ../../common/lib.sh runGetGitHooksDir
	cleanFakeRepos
	expectEqual "$status" 0
	expectEqual "$output" "../tmp/fake_repo.lib/.git/hooks"
}

@test "In lib.sh getGitHooksDir should return the hooks dir on a git module repo" {
	createFakeRepos
	local expected=$(realpath ../tmp/fake_repo.lib/.git/modules/subrepos/subrepo/hooks)
	runGetGitHooksDir() {
		getRepoRootPath() { echo ../tmp/fake_repo.lib/subrepos/subrepo; }
		getGitHooksDir
	}
	run filter_control_sequences runlib ../../common/lib.sh runGetGitHooksDir
	cleanFakeRepos
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}
