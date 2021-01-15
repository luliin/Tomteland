/**
 * Created by Julia Wigenstedt
 * Date: 2021-01-13
 * Time: 13:38
 * Project: Tomteland
 * Copyright: MIT
 */

//The recursive functions (required for this assignment) can be found on row 47 and row 59.
fun main() {
    getSantaList().run {
        addSubordinatesToEverySanta(this)
        runProgram(this)
    }
    println("Tack för besöket!")
}

private fun runProgram(santaList: List<Santa>) {
    printWelcomeMessage()
    var isRunning = true
    while (isRunning) {
        printMenu()
        isRunning = menuSelection(santaList)
    }
}

private fun addSubordinatesToEverySanta(santaList: List<Santa>) {
    subordinatesToBoss(santaList, getSanta(santaList, "tomten"), 2, 2)
    subordinatesToBoss(santaList, getSanta(santaList, "glader"), 3, 4)
    subordinatesToBoss(santaList, getSanta(santaList, "butter"), 4, 7)
    subordinatesToBoss(santaList, getSanta(santaList, "trötter"), 1, 11)
    subordinatesToBoss(santaList, getSanta(santaList, "skumtomten"), 1, 12)
    subordinatesToBoss(santaList, getSanta(santaList, "räven"), 2, 13)
    subordinatesToBoss(santaList, getSanta(santaList, "myran"), 1, 15)
}

private fun subordinatesToBoss(list: List<Santa>, santa: Santa, howManyToAdd: Int, startWithId: Int) {
    santa.subordinates = list.subList(startWithId - 1, startWithId - 1 + howManyToAdd)
}

// We find Santa object with a chosen name. If object is null we return a new Santa object
private fun getSanta(list: List<Santa>, name: String) = list.find {
    it.name.equals(name, true)
    } ?: Santa("Det finns ingen tomte med det namnet! Försök igen.", 0)

//Recursive function
private fun getSubordinates(santa: Santa): MutableList<Santa> {
    val listToReturn: MutableList<Santa> = mutableListOf()
    return if (santa.subordinates.isEmpty()) listToReturn else {
        santa.subordinates.forEach { s ->
            listToReturn += s
            listToReturn += getSubordinates(s)
        }
        listToReturn.sortedBy { it.id }.toMutableList()
    }
}

// Recursive function
private fun getBosses(santa: Santa): MutableList<Santa> {
    val listToReturn = mutableListOf<Santa>()
    return if (santa.boss == null) listToReturn else {
        listToReturn += santa.boss!!
        listToReturn += getBosses(santa.boss!!)
        listToReturn
    }
}

private fun printSubordinates(santa: Santa) = if (santa.id != 0) {
    val subordinates = getSubordinates(santa)
    when (subordinates.size) {
        0 -> println("$santa är inte chef över någon.")
        else -> println(subordinates.joinStringWithAnd(", ", "$santa är chef över: \n", "."))
    }
} else println(santa)

private fun printBosses(santa: Santa) = if (santa.id != 0) {
    val bosses = getBosses(santa)
    when (bosses.size) {
        0 -> println("$santa är den allra högsta chefen!")
        1 -> println(bosses.joinStringWithAnd(", ", "$santa \bs chef är: \n", "."))
        else -> println(bosses.joinStringWithAnd(", ", "$santa \bs chefer är: \n", "."))
    }
} else println(santa)

private fun keepSearching(): Boolean {
    println("Vill du forsätta? (J/N)")
    return readLine().equals("j", true)
}

private fun printAllSantas(santaList: List<Santa>) =
    println("${santaList.joinStringWithAnd(", ", "I Tomteland jobbar:\n", ".")}\n")

private fun printWelcomeMessage() = println("Välkommen till Tomteland!")

private fun printMenu() =
    println(
        """
            |
            |Vad vill du göra?
            |
            |    1. Söka efter en tomtes underordnade
            |    2. Söka efter en tomtes chefer
            |    3. Avsluta
            |
            |Gör ditt val (1-3) och tryck enter:
        """.trimMargin()
    )

private fun menuSelection(santaList: List<Santa>): Boolean {
    when (readLine()) {
        "1" -> subordinateMenu(santaList)
        "2" -> bossMenu(santaList)
        "3" -> return exitMenu()
        else -> {
            println("Ogiltigt val. Försök igen!")
        }
    }
    return keepSearching()
}

private fun bossMenu(santaList: List<Santa>) {
    printAllSantas(santaList)
    println("Vilken tomtes chefer vill du se?")
    val selection = readLine()
    val santa = getSanta(santaList, selection!!)
    printBosses(santa)
}

private fun subordinateMenu(santaList: List<Santa>) {
    printAllSantas(santaList)
    println("Vilken tomtes underordnade vill du se?")
    val selection = readLine()
    val santa = getSanta(santaList, selection!!)
    printSubordinates(santa)
}

private fun exitMenu(): Boolean {
    println("Är du säker på att du vill avsluta? (J/N)")
    return !readLine().equals("j", true)
}

private fun getSantaList() = listOf(
    Santa("Tomten", 1),
    Santa("Glader", 2),
    Santa("Butter", 3),
    Santa("Tröger", 4),
    Santa("Trötter", 5),
    Santa("Blyger", 6),
    Santa("Rådjuret", 7),
    Santa("Nyckelpigan", 8),
    Santa("Haren", 9),
    Santa("Räven", 10),
    Santa("Skumtomten", 11),
    Santa("Dammråttan", 12),
    Santa("Gråsuggan", 13),
    Santa("Myran", 14),
    Santa("Bladlusen", 15)
)

//joinToString extend function där "och" blir separator mellan näst sista och sista elementet (om det är fler än ett element).
private fun <T> Iterable<T>.joinStringWithAnd(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append(prefix)
    var count = 0
    for (element in this) {
        if (++count == this.count() && count > 1) stringBuilder.append(" och ")
        else if (count > 1) stringBuilder.append(separator)
        if (limit < 0 || count <= limit) {
            stringBuilder.append(element)
        } else break
    }
    stringBuilder.append(postfix)
    return stringBuilder.toString()
}