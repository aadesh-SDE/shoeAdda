# Contributing to ShoeAdda

## Branches
- `main` is always buildable. Do not commit to it directly; open a pull request.
- Name branches `type/short-description`, for example `feature/cart`, `fix/price-rounding`, `chore/repo-setup`, `docs/tech-stack`.
- One feature or one fix per branch and per pull request.

## Commit messages
Use Conventional Commits: `type: short summary` in the present tense.

| Type | Use for |
|---|---|
| `feat` | a new feature or screen |
| `fix` | a bug fix |
| `refactor` | code change with no behaviour change |
| `test` | adding or changing tests |
| `docs` | documentation only |
| `chore` | build, tooling, dependencies, repo setup |

Example: `feat: add cart screen and coupon validation`

Do not add `Co-Authored-By` lines or tool attribution to commits or pull requests.

## Pull requests
Before opening one:
- The app builds and the tests pass.
- Lint and formatting checks pass.
- New text is in `strings.xml` and translated in all six languages (English, Hindi, Marathi, Gujarati, Tamil, Telugu).
- Screens match `design/` and use Material 3 components (48dp minimum touch targets).

## Architecture rules
- Every feature has `data`, `domain` and `presentation` packages (see `FOLDER_STRUCTURE.md`).
- `presentation` uses only `domain`. `domain` is plain Kotlin.
- A feature may use another feature's `domain`, never its `data` or `presentation`.
- Create a feature's folders only when you build that feature. Do not add empty folders.
