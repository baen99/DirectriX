$version = "0.1-SNAPSHOT"
$pathToJar = "C:\Coding Projekte\Express cd\cli\target\cli-$version.jar"

function dirx {
    $target = & java -jar $pathToJar

    if ($LASTEXITCODE -ne 0) {
        Write-Error "Java program failed."
        return
    }

    if ($target -and (Test-Path -LiteralPath $target -PathType Container)) {
        Set-Location -LiteralPath $target
    }
    else {
        Write-Error "Invalid directory: $target"
    }
}

