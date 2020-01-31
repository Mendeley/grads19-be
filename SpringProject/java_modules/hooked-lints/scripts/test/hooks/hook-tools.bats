#!/usr/bin/env ../../bats/bats

load ../test_helper

myscript() { echo -n "<"; joinArray ">,<" "$@"; echo -n ">"; }

@test "In hook-tools.sh nx should run the command requested by the user" {
	cd ../tmp/
	rm -Rf fake_repo.hook-tools
	mkdir -p fake_repo.hook-tools/node_modules/{.bin/,import/node_modules/.bin}
	cd fake_repo.hook-tools

	callnx() {
		HOOKED_LINTS_DIR=./node_modules/import
		nx "$@"
	}

	local expected="WARN(bats-exec-test): Can't execute testcmd, not found either in ./node_modules/import/node_modules/.bin/testcmd or node_modules/.bin/testcmd"
	run filter_control_sequences runlib ../../../hooks/hook-tools.sh callnx testcmd p1 "p space" p3
	expectEqual "$status" 0
	expectEqual "$output" "$expected"

	echo -e '#!/bin/sh\necho called bin1 $#,$1,$2,$3,$4' > node_modules/.bin/testcmd
	chmod 700 node_modules/.bin/testcmd
	run filter_control_sequences runlib ../../../hooks/hook-tools.sh callnx testcmd p1 "p space" p3
	expectEqual "$status" 0
	expectEqual "$output" "called bin1 3,p1,p space,p3,"

	echo -e '#!/bin/sh\necho called bin2 $#,$1,$2,$3,$4' > node_modules/import/node_modules/.bin/testcmd
	chmod 700 node_modules/import/node_modules/.bin/testcmd
	run filter_control_sequences runlib ../../../hooks/hook-tools.sh callnx testcmd p1 "p space" p3
	expectEqual "$status" 0
	expectEqual "$output" "called bin2 3,p1,p space,p3,"

	cd ..
	rm -Rf fake_repo.hook-tools
}


@test "In hook-tools.sh empty not give any error if no input is received" {
	run filter_control_sequences runlib ../../hooks/hook-tools.sh empty test <<<$(echo -n)
	expectEqual "$status" 0
	expectEqual "$output" ""
}

@test "In hook-tools.sh empty print the given error if output is received" {
	local expected=$(echo -e "ERROR(bats-exec-test): test message\nERROR(bats-exec-test): line1\nERROR(bats-exec-test): line2\nERROR(bats-exec-test): line3")
	run filter_control_sequences runlib ../../hooks/hook-tools.sh empty test message <<<"$(echo -e "line1\nline2\nline3")"
	echo "$output" = "$expected"
	expectEqual "$status" 1
	expectEqual "$output" "$expected"
}

@test "In hook-tools.sh runscript should execute the passed script with all the arguments" {
	local expected=$(echo -e "INFO(bats-exec-test): Running hook: myscript param1 param with spaces param3\n<param1>,<param with spaces>,<param3>")
	run filter_control_sequences runlib ../../hooks/hook-tools.sh runscript myscript param1 "param with spaces" param3
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}

testRunner() {
	FILES=( f1 "f space" f3 )
	runscriptfiles "$@" myscript p1 "p space" p3
}

@test "In hook-tools.sh runscriptfiles with a .* rgx should call the script with all the files" {
	local expected=$(echo -e "INFO(bats-exec-test): Running hook for 3 files\n<p1>,<p space>,<p3>,<f1>,<f space>,<f3>")
	run filter_control_sequences runlib ../../hooks/hook-tools.sh testRunner ".*"
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}

@test "In hook-tools.sh runscriptfiles with a rgx that only returns one file should call the script that file" {
	local expected=$(echo -e "INFO(bats-exec-test): Running hook for 1 files\n<p1>,<p space>,<p3>,<f space>")
	run filter_control_sequences runlib ../../hooks/hook-tools.sh testRunner space
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}

@test "In hook-tools.sh runscriptfiles with -v and a rgx that inverted returns two file should call the script two files" {
	local expected=$(echo -e "INFO(bats-exec-test): Running hook for 2 files\n<p1>,<p space>,<p3>,<f1>,<f3>")
	run filter_control_sequences runlib ../../hooks/hook-tools.sh testRunner -v space
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}

@test "In hook-tools.sh runscriptfiles with rgx that does not return anything should not call the script" {
	run filter_control_sequences runlib ../../hooks/hook-tools.sh testRunner dummy
	expectEqual "$status" 0
	expectEqual "$output" ""
}

testJoinArray() {
	local expected="$1"
	shift
	run filter_control_sequences runlib ../../hooks/hook-tools.sh joinArray "$@"
	expectEqual "$status" 0
	expectEqual "$output" "$expected"
}

@test "In hook-tools.sh joinArray should join the array with the delimiter" {
	testJoinArray "a,b c,d" , a "b c" d
	testJoinArray "$(echo -e "a\nb c\nd")" $'\n' a "b c" d
	testJoinArray "a -- b c -- d" " -- " a "b c" d
}
