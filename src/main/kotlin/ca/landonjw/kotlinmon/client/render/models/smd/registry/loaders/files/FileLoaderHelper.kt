package ca.landonjw.kotlinmon.client.render.models.smd.registry.loaders.files

import ca.landonjw.kotlinmon.Kotlinmon
import net.minecraft.util.ResourceLocation
import java.io.InputStream
import java.util.regex.Matcher
import java.util.regex.Pattern

internal fun readLinesFromResource(location: ResourceLocation): List<String> {
    getResourceStream(location).use { stream ->
        stream.bufferedReader(Charsets.UTF_8).use { reader ->
            return reader.readLines()
        }
    }
}

private fun getResourceStream(location: ResourceLocation): InputStream {
    val namespace = location.namespace
    val path = location.path
    val stream = Kotlinmon::class.java.getResourceAsStream("/assets/$namespace/$path")
    return stream
}

private val smdLineRegex: Pattern = Pattern.compile("([^\"]\\S*|\".+?\")\\s*")

internal fun String.splitSmdValues(): List<String> {
    val matchList = mutableListOf<String>()
    val regexMatcher: Matcher = smdLineRegex.matcher(this)
    while (regexMatcher.find()) {
        matchList.add(regexMatcher.group(0).replace("\"", ""))
    }
    return matchList.filter { it.isNotBlank() }.map { it.trim() }
}