package com.inspireon.mystory.common.util;


public class HTMLUtils {

/*	public static final PolicyFactory sanitizer = 
        	new HtmlPolicyBuilder()
            .allowStandardUrlProtocols()
            // Allow title="..." on any element.
            .allowAttributes("title").globally()
            // Allow href="..." on <a> elements.
            .allowAttributes("href").onElements("a")
            // Defeat link spammers.
            .requireRelNofollowOnLinks()
            // Allow lang= with an alphabetic value on any element.
            .allowAttributes("lang").matching(Pattern.compile("[a-zA-Z]{2,20}"))
                .globally()
            // The align attribute on <p> elements can have any value below.
            .allowAttributes("align")
                .matching(true, "center", "left", "right", "justify", "char")
                .onElements("p")
            // These elements are allowed.
            .allowElements(
                "a", "p", "div", "i", "b", "em", "blockquote", "tt", "strong",
                "br", "ul", "ol", "li", "pre", "code", "img")
            // Custom slashdot tags.
            // These could be rewritten in the sanitizer using an ElementPolicy.
            .allowElements("quote", "ecode")
            .toFactory().and(Sanitizers.IMAGES);
	
	public static String sanitize(String htmlString){
		return sanitizer.sanitize(htmlString);
	}*/
}
