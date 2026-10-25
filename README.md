# DirectriX

DirectriX is a Windows PowerShell helper for jumping to a directory by name. Instead of navigating through several levels with repeated `cd` commands, call:

```powershell
dirx <directory-name>
```

For example, `dirx Projects` searches the indexed directories for a folder named `Projects` and changes the current PowerShell location to it when there is exactly one match.

## How it works

The PowerShell function runs the Java `cli` module, which asks the navigator to find the requested directory. The navigator looks up the directory name in a map built by recursively scanning the configured directory tree.

The map has directory names as keys and lists of absolute paths as values. For example:

```text
"Projects" -> ["C:\Users\<user>\Projects"]
"A"        -> ["C:\Coding Projekte\Express cd\Playground\A",
               "C:\Coding Projekte\Express cd\Playground\A\A"]
```

Keeping a list for each name allows the index to represent duplicate folder names rather than silently choosing one. The scan starts at `C:\` but only visits the configured top-level locations (`C:\Users`, `C:\Coding Projekte`, `C:\Program Files`, `C:\Program Files (x86)`, and `C:\Python312`). Hidden directories are skipped.

This map is currently an in-memory index, not a persistent cache: the loader and saver are not implemented, so each `dirx` invocation rebuilds the map by scanning the directories which is very consuming at the moment and will be fixed with the release of version 2.0.

## Ambiguous directory names

If a name matches more than one directory, DirectriX does not guess. It reports that the name is ambiguous and prints all matching absolute paths; the current command does not offer a selector or an extra argument to choose one (also shipping with 2.0). Use the full path with `Set-Location` in this case.

If there is no match, the CLI reports that no path was found. PowerShell changes location only when the call to the Java program succeeds and returns a path that still exists as a directory.

## PowerShell setup

Build the Maven modules in dependency order (`util`, `core`, then `cli`) so the shaded CLI JAR is available under `cli\target`. Then dot-source the script in your PowerShell session:

```powershell
. .\cli\src\main\pwsh\change-directory.ps1
dirx Projects
```

Dot-sourcing defines `dirx` in the current session, allowing its `Set-Location` to change your prompt's working directory.
