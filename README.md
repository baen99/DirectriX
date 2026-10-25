# Release Notes

## DirectriX Version 1.0

Overview of changes from commit `202ca41` ("Versionsanhebung auf 1.0-Snapshot") through the current release branch:

### Added

- Added directory navigation by name through the `dirx` PowerShell command and the new `Navigator`.
- Added handling for missing directory names and ambiguous matches. Ambiguous-path errors include the matching paths.
- Added a shared `AbstractDirectrixException` base class and `AmbiguousPathException`.
- Expanded the directory cache to recursively map directory names to their absolute paths.
- Added Windows `C:\` root scanning, restricted to a configured list of top-level folders. Hidden child directories are excluded.
- Added PowerShell handling for Java failures: captured error output is displayed in red, and successful results are checked as directories before changing location.

### Changed

- Renamed the cache implementation from `Cache` to `CacheWrapper` and wired the core module to the `util` module.
- Core startup now loads and prints the directory cache.

### Known limitations

- The configured scan roots are hard-coded.
- Cache loading and saving are still placeholders; the directory tree is scanned when no cache is available.