package pl.treksoft.kvision.form

import com.github.snabbdom.VNode
import pl.treksoft.kvision.core.Widget
import pl.treksoft.kvision.snabbdom.StringBoolPair
import pl.treksoft.kvision.snabbdom.StringPair

open class CheckBoxInput(override var value: Boolean = false,
                         name: String? = null, disabled: Boolean = false, id: String? = null,
                         classes: Set<String> = setOf()) : Widget(classes), BoolFormField {

    init {
        this.id = id
    }

    @Suppress("LeakingThis")
    var startValue: Boolean = value
        set(value) {
            field = value
            this.value = value
            refresh()
        }
    var name: String? = name
        set(value) {
            field = value
            refresh()
        }
    override var disabled: Boolean = disabled
        set(value) {
            field = value
            refresh()
        }
    override var size: INPUTSIZE? = null
        set(value) {
            field = value
            refresh()
        }

    override fun render(): VNode {
        return kvh("input")
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        size?.let {
            cl.add(it.className to true)
        }
        return cl
    }

    override fun getSnAttrs(): List<StringPair> {
        val sn = super.getSnAttrs().toMutableList()
        sn.add("type" to "checkbox")
        if (startValue) {
            sn.add("checked" to "checked")
        }
        name?.let {
            sn.add("name" to it)
        }
        if (disabled) {
            sn.add("disabled" to "disabled")
        }
        return sn
    }

    override fun afterInsert(node: VNode) {
        this.getElementJQuery()?.on("change", { _, _ ->
            val v = getElementJQuery()?.prop("checked") as Boolean?
            value = (v == true)
            true
        })
        this.getElementJQuery()?.on("click", { _, _ ->
            val v = getElementJQuery()?.prop("checked") as Boolean?
            value = (v == true)
            true
        })
    }
}