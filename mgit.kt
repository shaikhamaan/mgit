// imports
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// Class to initialize empty mgit repository
class Init(filePath: String) {
    init {
        // Create a .mgit directory if mgit repository is not already initialized
        try {
            val path: Path = Files.createDirectory(Paths.get(filePath))
            println("Initialized empty mGit repository in $path")
        } // Reinitialized a .mgit directory if mgit repository is not already initialized
        catch (e: IOException) {
            val path = Paths.get("").toAbsolutePath().toString()
            println("Reinitialized existing mGit repository in $path/.mgit/")
        }
    }
}

fun main(args: Array<String>) {
    // Checking arguments
    if(args[0] == "init") {
        // Adding mgit directory to current working directory
        val path = Paths.get("").toAbsolutePath().toString() + "/.mgit"

        // Init class to initialize repository
        Init(path)
    }
}
