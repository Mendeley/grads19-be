cd $(realpath "$BATS_TEST_DIRNAME")

filter_control_sequences() {
	"$@" 2>&1 | sed "s/$(echo -e "\033")\[[0-9;]*m//g"
	exit "${PIPESTATUS[0]}"
}

runlib() {
	(
		source $1
		shift
		"$@" 2>&1
	) | cat
	exit "${PIPESTATUS[0]}"
}

expectEqual() {
	local received="$1"
	local expected="$2"
	if [ "$received" != "$expected" ]; then
		echo
		echo -e "expected:\n$expected"
		echo
		echo -e "received:\n$received"
		echo
		return 1
	fi
}
