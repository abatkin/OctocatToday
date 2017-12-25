package com.internetitem.octocat

import com.internetitem.octocat.dataModel.OctocatFeed
import com.internetitem.octocat.util.SimpleNamespaceContext
import com.internetitem.octocat.util.createDocumentBuilder
import com.internetitem.octocat.util.toZonedDateTime
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.SyndFeedInput
import org.jsoup.Jsoup
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory


object FeedParser {

    val ATOM_NAMESPACE = "http://www.w3.org/2005/Atom"
    val ATOM_NAMESPACE_PREFIX = "atom"
    val HTML_ENTRY_PATH = "/atom:feed/atom:entry/atom:content[@type='html']"

    fun parseFeed(feedBytes: ByteArray): OctocatFeed {
        val items = bytesToItems(feedBytes)
        val sortedItems = items.sortedByDescending(OctocatFeed.OctocatFeedItem::updated)
        return OctocatFeed(sortedItems)

    }

    private fun bytesToItems(feedBytes: ByteArray): List<OctocatFeed.OctocatFeedItem> {
        val doc = parseXml(feedBytes)
        fixupHtml(doc)
        val feed = SyndFeedInput().build(doc)
        val itemList = feed.entries.map { entry ->
            OctocatFeed.OctocatFeedItem(entry.title, entry.link, entry.updatedDate.toZonedDateTime(), extractImageFromFeedEntry(entry))
        }
        return itemList
    }

    private fun parseXml(feedText: ByteArray): Document {
        val builder = createDocumentBuilder()
        return builder.parse(ByteArrayInputStream(feedText))
    }

    // This is due to a bug in the GitHub Octodex feed
    // The type for each entry is "html" even though it contains
    // unescaped HTML
    private fun fixupHtml(doc: Document) {
        val xpath = XPathFactory.newInstance().newXPath()
        xpath.namespaceContext = SimpleNamespaceContext(ATOM_NAMESPACE to ATOM_NAMESPACE_PREFIX)
        val rootElement = doc.documentElement
        val nodes = xpath.evaluate(HTML_ENTRY_PATH, rootElement, XPathConstants.NODESET) as NodeList
        for (i in 0 until nodes.length) {
            val element = nodes.item(i) as Element
            val text = element.textContent
            if (!text.contains('<')) {
                element.setAttribute("type", "xhtml")
            }
        }
    }

    private fun extractImageFromFeedEntry(entry: SyndEntry): String {
        val content = extractFeedEntryContent(entry)
        return Jsoup.parse(content).select("div a img").attr("src")
    }

    private fun extractFeedEntryContent(syndEntry: SyndEntry): String {
        return syndEntry.contents
                .filter { entry -> entry.type == "xhtml" || entry.type == "html" }
                .map { entry -> entry.value }
                .first()
    }
}

