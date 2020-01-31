const ERROR = 2;
const WARN = 1;
const OFF = 0;

const MAX_PARAMS = 5;
const MAX_LINE_LENGTH = 120;
const MAX_NESTED_CALLBACKS = 3;
const MAX_STATEMENTS = 50;

const TESTS_MAX_NESTED_CALLBACKS = 4;

const globalDictionary = require("./lint-dictionary.js"); // eslint-disable-line @typescript-eslint/no-var-requires
let projectDictionary;
try {
	// eslint-disable-next-line node/no-missing-require, node/no-unpublished-require
	projectDictionary = require("../../../../lint-dictionary.js");
} catch (error) {
	projectDictionary = [];
}

const lintDictionary = [...globalDictionary, ...projectDictionary].filter((item) => typeof item === "string");

module.exports = {
	plugins: [
		"@typescript-eslint",
		"filenames",
		"import",
		"jest",
		"jsdoc",
		"prettier",
		"promise",
		"react",
		"spellcheck"
	],

	// Stop ESLint from looking for a configuration file in parent folders
	root: true,
	extends: [
		"eslint:recommended",
		"plugin:react/recommended",
		"plugin:node/recommended",
		"plugin:unicorn/recommended",
		"plugin:@typescript-eslint/recommended",
		"prettier",
		"prettier/@typescript-eslint"
	],
	settings: {
		react: {
			version: "^16.5.1"
		}
	},
	parser: "@typescript-eslint/parser",
	parserOptions: {
		ecmaVersion: 2018,
		sourceType: "module"
	},
	rules: {
		// Development warnings
		"no-warning-comments": WARN,

		// Collides with TSLint
		"sort-imports": OFF,
		"no-void": OFF, // Used to consume an expression returning a void Promise
		// Collides with "no-floating-promises" from TSLint
		"class-methods-use-this": OFF, // We should not check for "this" in public methods.

		// Collides with prettier
		indent: OFF,
		"no-tabs": OFF,
		"quote-props": OFF,
		"array-element-newline": OFF,
		"no-mixed-operators": OFF,
		"comma-dangle": OFF,
		"lines-around-comment": OFF,
		"function-paren-newline": OFF,
		"implicit-arrow-linebreak": OFF,

		// Ignored
		"no-inline-comments": OFF, // Disallow use of comments on the same line as code
		"line-comment-position": OFF, // Disallow comments like on this document
		"sort-keys": OFF, // Key order may be useful for code readability
		"sort-vars": OFF, // Disallow var foo, bar; force var bar, foo; - Auto-fix
		"no-plusplus": OFF, // Disallow bar++
		"no-ternary": OFF, // Disallow foo?ok_foo:no_foo
		"no-continue": OFF, // Disallow continue on loops
		"dot-notation": OFF, // Disallow foo["bar"], force foo.bar - Auto-fix
		"no-extra-parens": OFF, // Disallow (boo+bar)+1, force foo+bar+1 - Auto-fix
		"guard-for-in": OFF, // Force if({}.hasOwnProperty.call(foo, key)) on for(key in foo)
		radix: OFF, // Disallow parseInt("123"), force parseInt("123", 10)
		"no-labels": OFF, // Disallow labels on loops
		"newline-per-chained-call": OFF, // Disallow fo.ob.ar., force fo.ob\n.ar - Auto-fix
		"vars-on-top": OFF, // Disallow for(var i...
		"id-blacklist": OFF, // This cannot be used as it will influence external APIs
		"id-length": OFF, // This will be enforced with id-match
		"no-div-regex": OFF, // Collides with no-useless-escape when a regexp starts with =
		"no-implicit-globals": OFF, // We use commonjs. Force window.MyClass = function() {}
		"object-property-newline": OFF, // Prettier conflict
		"multiline-comment-style": OFF, // Useful to comment and uncomment blocks on the IDE
		"lines-between-class-members": OFF, // It forces lines between properties on a class
		"no-useless-constructor": OFF, // It does not allow to create private constructors
		"init-declarations": OFF, // Not needed for try-catch or if-else blocks
		"prefer-destructuring": OFF, // We don't always to destructure arrays,
		"no-underscore-dangle": OFF, // We have to use it from external libraries.
		"no-invalid-this": OFF, // It's not working correctly in Typescript.
		"callback-return": OFF, // It doesn't work on try-catch and if statements
		camelcase: OFF, // It doesn't work well with typescript interfaces for external APIs

		// Bugs
		"no-undef": OFF, // Bug on typescript-eslint-parser, marks class properties as undefined
		"no-unused-vars": OFF, // Typescript import issue when used as types

		// Configured auto-fixes
		"dot-location": [ERROR, "property"],
		yoda: [ERROR, "never", {onlyEquality: true}],
		"one-var": [ERROR, "never"],
		"one-var-declaration-per-line": [ERROR, "always"],
		"operator-linebreak": [ERROR, "after"],
		quotes: [ERROR, "double", {avoidEscape: true}],

		// Configured errors
		"valid-jsdoc": [
			ERROR,
			{
				requireParamType: false,
				requireReturnType: false
			}
		],
		"max-len": [ERROR, {code: MAX_LINE_LENGTH, ignoreRegExpLiterals: true}],
		"no-return-assign": [ERROR, "always"],
		strict: [ERROR, "global"],
		"max-nested-callbacks": [ERROR, MAX_NESTED_CALLBACKS],
		"max-statements": [ERROR, MAX_STATEMENTS],
		"func-style": [ERROR, "declaration", {allowArrowFunctions: true}],
		"consistent-this": [ERROR, "self"],
		"no-cond-assign": [ERROR, "always"],
		"id-match": [
			ERROR,
			"^(_?_?[A-Za-z][A-Za-z0-9]{2,39}|[A-Z][A-Z0-9_]{2,39}|[$_]|id|fs)$",
			{onlyDeclarations: true}
		],
		"no-shadow": [
			ERROR,
			{
				builtinGlobals: true,
				hoist: "all"
			}
		],
		"space-before-function-paren": [
			"error",
			{
				anonymous: "always",
				named: "never",
				asyncArrow: "always"
			}
		],
		"padded-blocks": [ERROR, "never"],
		"no-empty-function": [ERROR, {allow: ["constructors"]}],
		"no-magic-numbers": [
			ERROR,
			{
				ignoreArrayIndexes: true,
				ignore: [0, 1]
			}
		],
		"max-params": [ERROR, MAX_PARAMS],
		"capitalized-comments": [
			ERROR,
			"always",
			{
				ignorePattern: "tslint",
				ignoreConsecutiveComments: true
			}
		],
		"object-curly-spacing": [ERROR, "never"],

		// Plugin rules
		"@typescript-eslint/explicit-function-return-type": [ERROR, {allowExpressions: true}],
		"@typescript-eslint/no-explicit-any": ERROR,
		"@typescript-eslint/no-unused-vars": [ERROR, {vars: "all", args: "none", ignoreRestSiblings: false}],
		"@typescript-eslint/camelcase": [ERROR, {properties: "never"}],

		"spellcheck/spell-checker": [
			ERROR,
			{
				comments: true,
				strings: true,
				identifiers: false,
				templates: true,
				lang: "en_GB",
				skipWords: lintDictionary,
				skipIfMatch: ["-[a-zA-Z]+-", "^[a-zA-Z0-9_=/+-]+$", "https?://[^s]*", "^[^ ]*(/[^ ]*)+$"],
				skipWordIfMatch: ["[0-9]"],
				minLength: 3
			}
		],
		"node/no-unpublished-require": [
			"error",
			{
				allowModules: ["chalk", "dotenv", "node-watch", "sass"]
			}
		],
		"node/no-unsupported-features/es-syntax": OFF,
		"node/no-unsupported-features/es-builtins": ["error", {version: "10.x"}],
		"node/no-unsupported-features/node-builtins": ["error", {version: "10.x"}],
		"unicorn/filename-case": OFF,
		"jsdoc/check-param-names": ERROR,
		"jsdoc/check-tag-names": ERROR,
		"jsdoc/check-types": ERROR,
		"jsdoc/newline-after-description": ERROR,
		"jsdoc/no-undefined-types": ERROR,
		"jsdoc/require-description": OFF,
		"jsdoc/require-description-complete-sentence": ERROR,
		"jsdoc/require-hyphen-before-param-description": ERROR,
		"jsdoc/require-param": ERROR,
		"jsdoc/require-param-description": ERROR,
		"jsdoc/require-param-name": ERROR,
		"jsdoc/require-returns-description": ERROR,
		"jsdoc/valid-types": ERROR,
		"multiline-ternary": OFF,
		"react/forbid-component-props": [ERROR, {forbid: ["style"]}], // Allow className
		"react/jsx-closing-bracket-location": OFF,
		"react/jsx-filename-extension": [ERROR, {extensions: [".jsx", ".tsx"]}],
		"react/no-set-state": OFF, // Might want to revise this in the future.
		"react/jsx-indent": [ERROR, "tab"],
		"react/jsx-indent-props": OFF,
		"react/sort-comp": OFF, // Needs to be reviewed later.
		"react/jsx-max-depth": OFF,
		"react/jsx-max-props-per-line": OFF,
		"react/jsx-one-expression-per-line": OFF,
		"react/jsx-sort-props": OFF,
		"react/no-danger": OFF, // We need to find an alternative for dangerouslySetInnerHTML.
		"react/jsx-no-literals": OFF,
		"react/destructuring-assignment": OFF,
		// TODO: We have to revisit this rule
		"filenames/match-regex": [ERROR, "^.*|[A-Z][a-z0-9]*([A-Z][a-z0-9]*)*(.spec|.test)*$", true],
		"filenames/match-exported": OFF,
		"filenames/no-index": OFF,
		"no-undefined": OFF // We keep undefined, we don't use null
	},
	overrides: [
		{
			files: ["**/.*.js"],
			rules: {
				"@typescript-eslint/no-var-requires": OFF,
				"global-require": OFF,
				"node/no-missing-require": OFF,
				"unicorn/catch-error-name": OFF,
				"node/no-unpublished-require": OFF
			}
		},
		{
			files: ["**/*.config.js"],
			rules: {
				"filenames/match-regex": [ERROR, "^[a-z]+.config$", true]
			}
		},
		{
			files: ["**/*.md"],
			plugins: ["markdown"],
			rules: {
				"no-magic-numbers": OFF, // Allow magic numbers on mocks/tests/examples
				"require-jsdoc": OFF, // Don't force jsdoc on mocks/tests/examples
				"jsdoc/require-description-complete-sentence": OFF,
				"jsdoc/newline-after-description": OFF,
				"jsdoc/require-description": OFF
			}
		},
		{
			files: ["**/*.ts{x,}"],
			rules: {
				"new-cap": OFF // Doesn't work with Typescript decorators
			}
		},
		{
			files: ["**/__*__/*.ts{x,}"],
			rules: {
				"no-magic-numbers": OFF, // Allow magic numbers on mocks/tests/examples
				"require-jsdoc": OFF, // Don't force jsdoc on mocks/tests/examples
				"jsdoc/require-description-complete-sentence": OFF,
				"jsdoc/newline-after-description": OFF,
				"jsdoc/require-description": OFF
			}
		},
		{
			files: ["**/__examples__/{**/,}*.ts{x,}"],
			rules: {
				"no-console": OFF
			},
			globals: {
				setInterval: true,
				clearInterval: true,
				setTimeout: true,
				clearTimeout: true,
				console: true
			}
		},
		{
			files: ["**/__mocks__/{**/,}*.ts{x,}"],
			rules: {
				"id-match": OFF, // Allow any id name to match the original API
				"max-classes-per-file": OFF, // On mocks sometimes is useful
				"class-methods-use-this": OFF, // Mock methods do not always use "this" keyword
				"no-empty-function": OFF, // We need empty functions on mocks
				"no-undefined": OFF, // We need to simulate uninitialized values in mocks
				"filenames/match-regex": OFF // We need to define names exactly as the library names in mocks
			}
		},
		{
			files: ["**/__tests__/{**/,}*.test.ts{x,}"],
			env: {"jest/globals": true},
			rules: {
				"no-empty": OFF,
				"max-lines-per-function": OFF, // Does not apply to test functions
				"max-nested-callbacks": [ERROR, TESTS_MAX_NESTED_CALLBACKS],
				"jest/consistent-test-it": ERROR,
				"jest/expect-expect": ERROR,
				"jest/lowercase-name": OFF,
				"jest/no-disabled-tests": ERROR,
				"jest/no-focused-tests": ERROR,
				"jest/no-hooks": OFF,
				"jest/no-identical-title": ERROR,
				"jest/no-jasmine-globals": ERROR,
				"jest/no-jest-import": ERROR,
				"jest/no-large-snapshots": ERROR,
				"jest/no-test-prefixes": ERROR,
				"jest/no-test-return-statement": ERROR,
				"jest/prefer-to-be-null": ERROR,
				"jest/prefer-to-be-undefined": ERROR,
				"jest/prefer-to-have-length": ERROR,
				"jest/valid-describe": ERROR,
				"jest/valid-expect": ERROR,
				"jest/prefer-expect-assertions": OFF,
				"jest/valid-expect-in-promise": ERROR,
				"jest/prefer-inline-snapshots": ERROR,
				"jest/prefer-strict-equal": ERROR,
				"@typescript-eslint/no-explicit-any": OFF,
				"@typescript-eslint/no-object-literal-type-assertion": [
					"error",
					{
						allowAsParameter: true // Allow type assertion in call and new expression, default false
					}
				]
			}
		}
	]
};
