#$version = "0.1-SNAPSHOT"
# read in project version from maven.config
$configPath = Join-Path $PSScriptRoot '..\..\..\..\.mvn\maven.config'
$config = Get-Content -Raw -LiteralPath $configPath

if ($config -notmatch '(?m)(?:^|\s)-Drevision=(?<version>[^\s]+)') {
    throw "Could not find -Drevision in $configPath"
}

$version = $Matches.version
$pathToJar = Join-Path $PSScriptRoot "..\..\..\target\cli-$version.jar"

Write-Host $version

function dirx {
    param(
        [string]$argument
    )

    $target = & java -jar $pathToJar $argument 2>&1 # redirect stderr to stdout

    if ($LASTEXITCODE -ne 0) {
        Write-Host ($target -join [Environment]::NewLine) -ForegroundColor Red
        return
    }

    # Zusätzliche Prüfung: kann sein, dass der Java-Aufruf einen veralteten Cache nutzt
    if ($target -and (Test-Path -LiteralPath $target -PathType Container)) {
        Set-Location -LiteralPath $target
    }
    else {
        Write-Error "Invalid directory: $target"
    }
}

