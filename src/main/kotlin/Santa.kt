/**
 * Created by Julia Wigenstedt
 * Date: 2021-01-13
 * Time: 13:08
 * Project: Tomteland
 * Copyright: MIT
 */
class Santa(var name: String, var id: Int) {
    var boss: Santa? = null
        set(value) {
            field = value
        }

    var subordinates: List<Santa> = listOf()
        set(value) {
            field = value
            field.forEach{
                    s -> s.boss=this
            }
        }

    override fun toString(): String {
        return this.name
    }
}