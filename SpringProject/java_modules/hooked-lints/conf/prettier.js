module.exports = {
	endOfLine: "auto",
	printWidth: 120,
	tabWidth: 4,
	useTabs: true,
	semi: true,
	singleQuote: false,
	trailingComma: "none",
	bracketSpacing: false,
	jsxBracketSameLine: true,
	arrowParens: "always",
	overrides: [
		{
			files: ["*.ts", "*.tsx"],
			options: {
				parser: "typescript",
				trailingComma: "all"
			}
		},
		{
			files: ["*.json"],
			options: {parser: "json"}
		},
		{
			files: ["*.md"],
			options: {parser: "markdown"}
		}
	]
};
