# Release Notes

## DirectriX Version 0.1

Overview of the product changes on the `release/0.1` branch, from the initial commit through `5476ef2` ("Release Version 0.1").

### Added

- Created the initial Maven project with separate `cli`, `core`, and `util` modules, and connected the CLI to the core module.
- Added the first directory-cache components: a map of directory names to paths, cache loader and saver placeholders, and a recursive directory traversal routine.
- Added constants for the `C:\` root and a local test directory.
- Added a PowerShell `dirx` function that invokes the Java CLI and changes to a returned directory.
- Added CLI arguments `a` and `b` to select the test directory or the `C:\` root, with basic handling for missing or invalid arguments.
- Configured the CLI JAR manifest with its entry point and added Maven Shade Plugin configuration to package a standalone JAR.

### Changed

- Moved the Maven revision into `.mvn/maven.config` and used it as the version for the modules and CLI JAR.
- Refined and debugged the recursive cache-building method.

### Known limitations

- The cache loader and saver and cache updates are not implemented.
- The argument parser is an empty placeholder; the CLI currently supports only the fixed `a` and `b` choices.
- Directory traversal is an initial implementation and does not yet handle inaccessible or special directories robustly.

### Commit overview

- `e782b39` — Created the initial CLI and core Maven modules.
- `e0705a1` — Added the core module as a CLI dependency.
- `244e786` — Added the initial cache, loader, and saver classes.
- `51e3022` — Added an empty `ArgumentParser` placeholder.
- `513cfab` — Added the root-directory variable.
- `3651d80` — Added the initial cache logic structure.
- `ee0c96d` — Implemented recursive directory-path indexing.
- `576e219` — Debugged and refactored the cache-building method.
- `c0d686f` — Configured Maven plugins to build a standalone CLI JAR.
- `04842e1` — Added the PowerShell directory-switching function.
- `45d3ff7` — Added argument-based directory selection to the CLI.
- `33678ba` — Centralized the Maven revision and updated version references.
- `5476ef2` — Set the project release version to `0.1`.
