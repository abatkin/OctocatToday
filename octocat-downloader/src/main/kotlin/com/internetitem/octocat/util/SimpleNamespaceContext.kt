package com.internetitem.octocat.util

import javax.xml.namespace.NamespaceContext

class SimpleNamespaceContext(vararg namespaces: Pair<String, String>) : NamespaceContext {
    val namespaceToAlias = mutableMapOf<String, String>()
    val aliasToNamespace = mutableMapOf<String, String>()

    init {
        namespaces.forEach { namespace -> addPair(namespace) }
    }

    fun addPair(namespace: Pair<String, String>) {
        namespaceToAlias[namespace.first] = namespace.second
        aliasToNamespace[namespace.second] = namespace.first
    }

    override fun getNamespaceURI(prefix: String?) = aliasToNamespace[prefix]

    override fun getPrefix(namespaceURI: String?) = namespaceToAlias[namespaceURI]

    override fun getPrefixes(namespaceURI: String?): MutableIterator<Any?>? = aliasToNamespace.keys.toMutableSet<Any?>().iterator()
}
