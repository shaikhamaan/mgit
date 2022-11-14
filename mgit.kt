// imports
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest

// constants
const val STREAM_BUFFER_LENGTH = 1024
val DIGITS_UPPER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

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

// Class to generate file hash in sha-1
class GenerateHash() {
    // Function to update digest
    fun updateDigest(digest: MessageDigest, data: InputStream): MessageDigest {
        val buffer = ByteArray(STREAM_BUFFER_LENGTH)
        var read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)
        while (read > -1) {
            digest.update(buffer, 0, read)
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH)
        }
        return digest
    }

    // Function to encode data to hex
    fun encodeHex(data: ByteArray): CharArray {
        var toDigits = DIGITS_UPPER
        val l = data.size
        val out = CharArray(l shl 1)
        var i = 0
        var j = 0
        while (i < l) {
            out[j++] = toDigits[0xF0 and data[i].toInt() ushr 4]
            out[j++] = toDigits[0x0F and data[i].toInt()]
            i++
        }
        return out
    }

    // Function to generate the hash
    fun getHash(filePath: String): String {
        var file = File(filePath)
        var digest = MessageDigest.getInstance("SHA-1")
        val fis = FileInputStream(file)
        val byteArray = updateDigest(digest, fis).digest()
        fis.close()
        val hexCode = encodeHex(byteArray)
        return String(hexCode)
    }
}

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Too few arguments!")
        println("Run mgit --help to see usage")
    } else {
        // Checking arguments
        if (args[0] == "--help") {
            println("Usage: mgit [options] <argument>")
            println("Where options include:\n")
            println("\tinit \t\tto initialize an empty mgit repository")
            println("\thash-object \tto save hashed file in objects directory")
            println("\tcat-file \tto print the content of hashed file")
        }
        if (args[0] == "init") {
            // Adding mgit directory to current working directory
            val path = Paths.get("").toAbsolutePath().toString() + "/.mgit"

            // Init class to initialize repository
            Init(path)
        }

        if (args[0] == "hash-object" && args.size > 1) {
            // generate hash
            val path = Paths.get("").toAbsolutePath().toString()
            val newHash = GenerateHash()
            val hash = newHash.getHash(path + "/" + args[1])
            //creating a directory to store objects 
            try{
                  Files.createDirectory(Paths.get(path + "/.mgit/objects"))
            } catch (e: IOException) {

            }

            // Creating hash file
            try {
                val hashPath = Files.createFile(Paths.get(path + "/.mgit/objects/$hash"))
                val fileContent = File(path + "/" + args[1])
                File(hashPath.toString()).writeText(fileContent.readText())
                println("File successfully hashed!")
                println("Hash: $hash")
            } catch (e: IOException) {
                println(e)
                println("Hash already exists!")
                println("Hash: $hash")
            }
        } 
        if(args[0] == "hash-object" && args.size == 1) {
            println("hash-object requires file as an argument")
        }

        if (args[0] == "cat-file" && args.size > 1) {
            val path = Paths.get("").toAbsolutePath().toString()
            try {
                val fileContent = File(path + "/.mgit/objects/" + args[1]).readText()
                println(fileContent)
            } catch (e: IOException) {
                println("Invalid argument!")
                println("File doest not exist")
                println("To hash a file use command \"mgit hash-object <file-name>\"")
            }
        } 
        if(args[0] == "cat-file" && args.size == 1) {
            println("cat-file requires hash as an argument")
        }
    }
}
