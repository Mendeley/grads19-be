
all: lint test

.PHONY: lint test

clean:
	rm -rf node_modules

node_modules: package.json
	./scripts/tools/check-runtimes.sh
	npm install
	touch $@

test:
	./scripts/tools/test.sh

lint:
	echo "No lints"


# To be run in Webkins
release:node_modules
	./scripts/pipeline/release.sh
