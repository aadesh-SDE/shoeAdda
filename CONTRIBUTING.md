# Contributing to ShoeAdda

## Branches
This is a one-person project, so there are no pull requests. Work on a short branch, then merge it into `main` yourself and push `main`.
- `main` is always buildable. Merge into it only after the build passes.
- Name branches `type/short-description`, for example `feature/cart`, `fix/price-rounding`, `docs/tech-stack`.
- One feature or one fix per branch. Merge with `git merge --ff-only` (or a normal merge), then delete the branch.
- If more people join later, switch to pull requests and protect `main`.

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

## Before merging into main
- The app builds and the tests pass.
- Lint and formatting checks pass.
- New text is in `strings.xml` and translated in all six languages (English, Hindi, Marathi, Gujarati, Tamil, Telugu).
- Screens match `design/` and use Material 3 components (48dp minimum touch targets).

## Architecture rules
- Every feature has `data`, `domain` and `presentation` packages (see `FOLDER_STRUCTURE.md`).
- `presentation` uses only `domain`. `domain` is plain Kotlin.
- A feature may use another feature's `domain`, never its `data` or `presentation`.
- Create a feature's folders only when you build that feature. Do not add empty folders.
