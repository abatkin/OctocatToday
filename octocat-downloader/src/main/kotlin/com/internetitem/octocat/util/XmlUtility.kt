package com.internetitem.octocat.util

import org.w3c.dom.Document
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun dumpDocument(doc: Document): String {
    val transformer = TransformerFactory.newInstance().newTransformer()
    transformer.setOutputProperty(OutputKeys.INDENT, "yes")
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
    val result = StreamResult(StringWriter())
    val source = DOMSource(doc)
    transformer.transform(source, result)
    return result.writer.toString()
}

fun createDocumentBuilder(): DocumentBuilder {
    val factory = DocumentBuilderFactory.newInstance()
    factory.isNamespaceAware = true
    factory.isCoalescing = true
    factory.isIgnoringComments = true

    return factory.newDocumentBuilder()
}
