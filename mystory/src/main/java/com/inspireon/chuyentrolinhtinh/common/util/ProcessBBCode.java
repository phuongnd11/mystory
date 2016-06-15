package com.inspireon.chuyentrolinhtinh.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * $Id: ProcessBBCode.java,v 1.18.2.2.4.4 2007/04/17 17:27:08 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 * @author is now Phuong Dang
 */

public class ProcessBBCode{
    private static final String CR_LF = "(?:\r\n|\r|\n)?";

    /** */
    private boolean acceptHTML = false;

    /** */
    private boolean acceptBBCode = true;
    
    private Map<String, String> customSmileys = new TreeMap<String, String>();
    
    private List<String> sortedCode = new ArrayList<String>();
    
    public ProcessBBCode() {
    	sortedCode.add(":whistling:");
    	sortedCode.add(":cheerful:");
    	sortedCode.add(":wallbash:");
    	sortedCode.add(":sideways:");
    	sortedCode.add(":happycry:");
    	sortedCode.add(":sleeping:");
    	sortedCode.add(":naughty:");
    	sortedCode.add(":annoyed:");
    	sortedCode.add(":kenshin:");
    	sortedCode.add(":getlost:");
    	sortedCode.add(":kissing:");
    	sortedCode.add(":correct:");
    	sortedCode.add(":victory:");
    	sortedCode.add(":hapcry:");
    	sortedCode.add(":admire:");
    	sortedCode.add(":pirate:");
    	sortedCode.add(":unsure:");
    	sortedCode.add(":clover:");
    	sortedCode.add(":iykwim:");
    	sortedCode.add(":angel:");
    	sortedCode.add(":sweat:");
    	sortedCode.add(":devil:");
    	sortedCode.add(":ninja:");
    	sortedCode.add(":alien:");
    	sortedCode.add(":devil:");
    	sortedCode.add(":silly:");
    	sortedCode.add(":pouty:");
    	sortedCode.add(":happy:");
    	sortedCode.add(":blush:");
    	sortedCode.add(":angry:");
    	sortedCode.add(":metal:");
    	sortedCode.add(":smile:");
    	sortedCode.add(":dizzy:");
    	sortedCode.add(":heart:");
    	sortedCode.add(":blink:");
    	sortedCode.add(":sleep:");
    	sortedCode.add(":pinch:");
    	sortedCode.add(":skull:");
    	sortedCode.add(":love:");
    	sortedCode.add(":clap:");
    	sortedCode.add(":finn:");
    	sortedCode.add(":cool:");
    	sortedCode.add(":woot:");
    	sortedCode.add(":sick:");
    	sortedCode.add(":ermm:");
    	sortedCode.add(":blue:");
    	sortedCode.add(":rofl:");
    	sortedCode.add(":wink:");
    	sortedCode.add(":wave:");
    	sortedCode.add(":yeah:");
    	sortedCode.add(":yeah:");
    	sortedCode.add(":nod:");
    	sortedCode.add(":hae:");
    	sortedCode.add(":cop:");
    	sortedCode.add(":zzz:");
    	sortedCode.add(":cwy:");
    	sortedCode.add(":sun:");
    	sortedCode.add(":hug:");
    	sortedCode.add(":sad:");
    	sortedCode.add(":lol:");
    	sortedCode.add(":bye:");
    	sortedCode.add(":wub:");
    	sortedCode.add(":yes:");
    	sortedCode.add(":win:");
    	sortedCode.add("o:\\-\\)");
    	sortedCode.add(":\\-o&lt;");
    	sortedCode.add("O:\\-\\)");
    	sortedCode.add(":\\-O&lt;");
    	sortedCode.add(":no:");
    	sortedCode.add(":\\)\\)");
    	sortedCode.add("=\\)\\)");
    	sortedCode.add(":\"&gt;");
    	sortedCode.add(":\\(\\(");
    	sortedCode.add(":/D");
    	sortedCode.add(":\\-h");
    	sortedCode.add("x\\-.");
    	sortedCode.add("\\(:\\|");
    	sortedCode.add("\\[\\-x");
    	sortedCode.add("@\\-\\)");
    	sortedCode.add("S\\-\\(");
    	sortedCode.add(":\\-s");
    	sortedCode.add(":\\-&amp;");
    	sortedCode.add(":\\-\\?");
    	sortedCode.add("8\\-X");
    	sortedCode.add(":v:");
    	sortedCode.add(":\\-\"");
    	sortedCode.add(":n:");
    	sortedCode.add(":y:");
    	sortedCode.add(";\\-\\)");
    	sortedCode.add(":\\-D");
    	sortedCode.add(":\\-\\*");
    	sortedCode.add(":\\-O");
    	sortedCode.add(":\\-o");
    	sortedCode.add("&gt;_&lt;");
    	sortedCode.add("x\\-\\(");
    	sortedCode.add("X\\-\\(");
    	sortedCode.add("x_0");
    	sortedCode.add("x_O");
    	sortedCode.add("o_0");
    	sortedCode.add("o_O");
    	sortedCode.add(":\\-H");
    	sortedCode.add("x\\-/");
    	sortedCode.add("X\\-/");
    	sortedCode.add(":\\-/");
    	sortedCode.add("\\[\\-X");
    	sortedCode.add(":\\-&gt;");
    	sortedCode.add(":\\-S");
    	sortedCode.add("s\\-\\(");
    	sortedCode.add("X\\-.");
    	sortedCode.add(":\\-p");
    	sortedCode.add(":\\-P");
    	sortedCode.add("8\\-x");
    	sortedCode.add(":\\)");
    	sortedCode.add(";\\)");
    	sortedCode.add(":D");
    	sortedCode.add("xD");
    	sortedCode.add(":\\*");
    	sortedCode.add(":O");
    	sortedCode.add(":\\|");
    	sortedCode.add(":\\(");
    	sortedCode.add("x\\(");
    	sortedCode.add(":p");
    	sortedCode.add("&lt;3");
    	sortedCode.add(":&gt;");
    	//sortedCode.add(":/");
    	sortedCode.add(":o");
    	sortedCode.add("X\\(");
    	sortedCode.add("X/");
    	sortedCode.add(":P");

    	customSmileys.put(":whistling:","whistle.gif");
    	customSmileys.put(":cheerful:","tooth.gif");
    	customSmileys.put(":wallbash:","wallbash.gif");
    	customSmileys.put(":sideways:","rofl.gif");
    	customSmileys.put(":happycry:","happy_crying.gif");
    	customSmileys.put(":sleeping:","sleep.gif");
    	customSmileys.put(":naughty:","naughty.gif");
    	customSmileys.put(":annoyed:","annoyed.gif");
    	customSmileys.put(":kenshin:","kenshin.gif");
    	customSmileys.put(":getlost:","getlost.png");
    	customSmileys.put(":kissing:","kiss.gif");
    	customSmileys.put(":correct:","nod.gif");
    	customSmileys.put(":victory:","v.gif");
    	customSmileys.put(":hapcry:","happy_crying.gif");
    	customSmileys.put(":admire:","admire.gif");
    	customSmileys.put(":pirate:","pirate.png");
    	customSmileys.put(":unsure:","hae.gif");
    	customSmileys.put(":clover:","clover.gif");
    	customSmileys.put(":iykwim:","naughty.gif");
    	customSmileys.put(":angel:","angel.png");
    	customSmileys.put(":sweat:","sweatdrop.gif");
    	customSmileys.put(":devil:","devil.gif");
    	customSmileys.put(":ninja:","ninja.gif");
    	customSmileys.put(":alien:","alien.png");
    	customSmileys.put(":devil:","devil.png");
    	customSmileys.put(":silly:","silly.png");
    	customSmileys.put(":pouty:","pouty.png");
    	customSmileys.put(":happy:","happy.png");
    	customSmileys.put(":blush:","blush.gif");
    	customSmileys.put(":angry:","furious.gif");
    	customSmileys.put(":metal:","metal.gif");
    	customSmileys.put(":smile:","smile.gif");
    	customSmileys.put(":dizzy:","dribble.gif");
    	customSmileys.put(":heart:","heart.gif");
    	customSmileys.put(":blink:","naughty.gif");
    	customSmileys.put(":sleep:","sleep.gif");
    	customSmileys.put(":pinch:","pinch.gif");
    	customSmileys.put(":skull:","skull.gif");
    	customSmileys.put(":love:","wub.gif");
    	customSmileys.put(":clap:","clap.gif");
    	customSmileys.put(":finn:","finn.gif");
    	customSmileys.put(":cool:","agent.gif");
    	customSmileys.put(":woot:","w00t.png");
    	customSmileys.put(":sick:","sick.gif");
    	customSmileys.put(":ermm:","think.gif");
    	customSmileys.put(":blue:","blue.gif");
    	customSmileys.put(":rofl:","rofl.gif");
    	customSmileys.put(":wink:","wink.gif");
    	customSmileys.put(":wave:","bye.gif");
    	customSmileys.put(":yeah:","yeah.gif");
    	customSmileys.put(":yeah:","yeah.gif");
    	customSmileys.put(":nod:","nod.gif");
    	customSmileys.put(":hae:","hae.gif");
    	customSmileys.put(":cop:","cop.png");
    	customSmileys.put(":zzz:","sleep.gif");
    	customSmileys.put(":cwy:","scared.gif");
    	customSmileys.put(":sun:","sun.gif");
    	customSmileys.put(":hug:","hug.gif");
    	customSmileys.put(":sad:","sad.png");
    	customSmileys.put(":lol:","laugh.gif");
    	customSmileys.put(":bye:","bye.gif");
    	customSmileys.put(":wub:","wub.gif");
    	customSmileys.put(":yes:","thumbup.gif");
    	customSmileys.put(":win:","v.gif");
    	customSmileys.put("o:\\-\\)","innocent.gif");
    	customSmileys.put(":\\-o&lt;","pray.gif");
    	customSmileys.put("O:\\-\\)","innocent.gif");
    	customSmileys.put(":\\-O&lt;","pray.gif");
    	customSmileys.put(":no:","thumbdown.gif");
    	customSmileys.put(":\\)\\)","laugh.gif");
    	customSmileys.put("=\\)\\)","rofl.gif");
    	customSmileys.put(":\"&gt;","blush.gif");
    	customSmileys.put(":\\(\\(","cry.gif");
    	customSmileys.put(":/D","tooth.gif");
    	customSmileys.put(":\\-h","bye.gif");
    	customSmileys.put("x\\-.","pinch.gif");
    	customSmileys.put("\\(:\\|","yawn.gif");
    	customSmileys.put("\\[\\-x","lac.gif");
    	customSmileys.put("@\\-\\)","hypo.gif");
    	customSmileys.put("S\\-\\(","scared.gif");
    	customSmileys.put(":\\-s","worried.gif");
    	customSmileys.put(":\\-&amp;","sick.gif");
    	customSmileys.put(":\\-\\?","think.gif");
    	customSmileys.put("8\\-X","skull.gif");
    	customSmileys.put(":v:","v.gif");
    	customSmileys.put(":\\-\"","whistle.gif");
    	customSmileys.put(":n:","thumbdown.gif");
    	customSmileys.put(":y:","thumbup.gif");
    	customSmileys.put(";\\-\\)","wink.gif");
    	customSmileys.put(":\\-D","grin.gif");
    	customSmileys.put(":\\-\\*","kiss.gif");
    	customSmileys.put(":\\-O","shocked.png");
    	customSmileys.put(":\\-o","shocked.png");
    	customSmileys.put("&gt;_&lt;","gah.gif");
    	customSmileys.put("x\\-\\(","furious.gif");
    	customSmileys.put("X\\-\\(","furious.gif");
    	customSmileys.put("x_0","dribble.gif");
    	customSmileys.put("x_O","dribble.gif");
    	customSmileys.put("o_0","shocking.gif");
    	customSmileys.put("o_O","shocking.gif");
    	customSmileys.put(":\\-H","bye.gif");
    	customSmileys.put("x\\-/","disgust.gif");
    	customSmileys.put("X\\-/","disgust.gif");
    	//customSmileys.put(":\\-/","duh.gif");
    	customSmileys.put("\\[\\-X","lac.gif");
    	customSmileys.put(":\\-&gt;","proud.gif");
    	customSmileys.put(":\\-S","worried.gif");
    	customSmileys.put("s\\-\\(","scared.gif");
    	customSmileys.put("X\\-.","pinch.gif");
    	customSmileys.put(":\\-p","tongue.gif");
    	customSmileys.put(":\\-P","tongue.gif");
    	customSmileys.put("8\\-x","skull.gif");
    	customSmileys.put(":\\)","smile.gif");
    	customSmileys.put(";\\)","wink.gif");
    	customSmileys.put(":D","grin.gif");
    	customSmileys.put("xD","xd.gif");
    	customSmileys.put(":\\*","kiss.gif");
    	customSmileys.put(":O","shocked.png");
    	customSmileys.put(":\\|","pouty.png");
    	customSmileys.put(":\\(","sad.png");
    	customSmileys.put("x\\(","furious.gif");
    	customSmileys.put(":p","tongue.gif");
    	customSmileys.put("&lt;3","heart.gif");
    	customSmileys.put(":&gt;","proud.gif");
    	//customSmileys.put(":/","duh.gif");
    	customSmileys.put(":o","shocked.png");
    	customSmileys.put("X\\(","furious.gif");
    	customSmileys.put("X/","disgust.gif");
    	customSmileys.put(":P","tongue.gif");


	}
    
    /**
     * @return acceptBBCode
     */
    public boolean getAcceptBBCode() {
        return acceptBBCode;
    }

    /**
     * @param acceptBBCode the new acceptBBCode value
     */
    public void setAcceptBBCode(boolean acceptBBCode) {
        this.acceptBBCode = acceptBBCode;
    }

    /**
     * @return htmlAccepted
     */
    public boolean getAcceptHTML() {
        return acceptHTML;
    }

    /**
     * @param acceptHTML the new acceptHTML value
     */
    public void setAcceptHTML(boolean acceptHTML) {
        this.acceptHTML = acceptHTML;
    }
    
    class Codee implements Comparable<Codee>{
    	
    	String code;
    	
    	String simpleCode;
    	
    	String image;

    	public Codee(String simpleCode, String code, String image){
    		this.simpleCode = simpleCode;
    		this.code = code;
    		this.image = image;
    	}
    	
		@Override
		public int compareTo(Codee o) {
			return this.simpleCode.length() > o.simpleCode.length() ? -1 : 1;
		}
    	
    	
    }
    
    public static void main(String[] args) throws IOException {
    	
    	File f = new File("C:/Users/Phuong/Desktop/tang_phuong.js");
    	
    	BufferedReader br = new BufferedReader(new FileReader(f));
    	TreeSet<Codee> treeLine = new TreeSet<Codee>();
    	
    	try {
            String line = br.readLine();

            while (line != null) {
                
            	int separateIndex = line.lastIndexOf(':');
            	
            	int source = line.lastIndexOf('/');
            	
            	String code = line.substring(1, separateIndex-1);
            	String simpleCode = code;
            	String image = line.substring(source+1, line.length()-2);
                code = replaceAll(code, "&<>)(|[]\"*?^$-".toCharArray(), new String[] { "&amp;", "&lt;", "&gt;", "\\\\)", "\\\\(", "\\\\|", "\\\\[", 
                	      "\\\\]","\\\"", "\\\\*", "\\\\?", "\\\\^",  "\\\\$", "\\\\-"});
                
                treeLine.add(new ProcessBBCode().new Codee(simpleCode, code, image));
                
                line = br.readLine();
            }
            
        } finally {
            br.close();
        }
    	
    	for(Codee codee : treeLine){
    		
    		System.out.println("customSmileys.put(\"" + codee.code + "\",\"" + codee.image + "\");");
//    		System.out.println("sortedCode.add(\"" + codee.code + "\");");
    	}
//    	System.out.println(new ProcessBBCode().preparePostText("[b]dasfdsfds[/b]\n"
//    						+ "[b]gfdgfdg[/b]\n"
//    						+ "[b]gfdgfdg[i]gfdgfdgfd[/i][/b]\n"
//    						+ "[b][i]gfdgfdgfd[/i][/b]\n"
//    						+ "[i][b]hghgfhgfjhgj [/b][/i][s][sub][u][i][b]jhgjhg[/b][/i][/u][/sub][u][i][b]jhkjhkhgfh[/b][/i][/u]hghgf[/s]hgfhgfhg[size=7]hgfhgfhgfjhgkj:O8-):alien:[/size]\n"));
    	
    	
  	/*System.out.println(new ProcessBBCode().clearBBCode("[b]bbb[/b]"
    			+ "[i]iiiiii[/i]"
    			+ "[u]uuu[/u]"
    			+ "[s]ssss[/s]"
    			+ "[sub]xxxx2[/sub]"
    			+ "[sup]x2222[/sup]"
    			+ "[center][sup]center[/sup][/center]"
    			+ "[left]"
    			+ "[quote]quote quote[/quote]"

    			+ "[code]code code[/code]"
    			+ "[sup]:):ermm::angel:"
    			+ "[/sup][/left]"
    			+ "[left][sup][youtube]tkJXtv7d_7I[/youtube]"
    			+ "[/sup][/left]"

    			+ "[left][img]http://localhost:8080/image/c781b682250a1549cdb7f00b0ecaa078_full[/img][/left]"
    			+ "[color=#cc6633]xxxxxxxxxxxxxxx[/color]"
    			));*/

	}

    /**
     * @param texto
     * @return TODO unuseful parameters.
     */
    public String preparePostText(String texto) {
        if (!getAcceptHTML()) {
            texto = escapeHtml(texto);
        }
        if (getAcceptBBCode()) {
            texto = process(texto);
        }
        return texto;
    }
    
    /**
     * @param texto
     * @return TODO unuseful parameters.
     */
    public String prepareExcerpt(String texto) {
        if (!getAcceptHTML()) {
            texto = escapeHtml(texto);
        }
        if (getAcceptBBCode()) {
            texto = clearBBCode(texto);
        }
        return texto;
    }
    
    private String clearBBCode(String string){
    	
    	string = string.replaceAll("(\r\n|\n\r|\n|\r)", " ");

        StringBuffer buffer = new StringBuffer(string);

        String str = buffer.toString();

        // [color]
        str = str.replaceAll("\\[color=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/color\\]",
            "$2");

        // [size]
        str = str.replaceAll("\\[size=['\"]?([0-9]|[1-2][0-9])['\"]?\\](.*?)\\[/size\\]",
            "$2");

        // [b][u][i][s]
        str = str.replaceAll("\\[b\\](.*?)\\[/b\\]", "$1");
        str = str.replaceAll("\\[u\\](.*?)\\[/u\\]", "$1");
        str = str.replaceAll("\\[i\\](.*?)\\[/i\\]", "$1");
        str = str.replaceAll("\\[s\\](.*?)\\[/s\\]", "$1");

        // [sub][sup]
        str = str.replaceAll("\\[sup\\](.*?)\\[/sup\\]", "$1");
        str = str.replaceAll("\\[sub\\](.*?)\\[/sub\\]", "$1");
        
        // [img]
        str = str.replaceAll("\\[img\\](.*?)\\[/img\\]", "");

        // [url]
        str = str.replaceAll("\\[url\\](.*?)\\[/url\\]", "$1");
        str = str.replaceAll("\\[url=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/url\\]",
            "$1");

        // [email]
        str = str.replaceAll("\\[email\\](.*?)\\[/email\\]", "$1");

        // [youtube]
       // str = str.replaceAll("\\[youtube\\](.*?)\\[/youtube\\]", "<object width=\"195\" height=\"95\"><param name=\"movie\" value=\"http://www.youtube.com/v/$1\"></param><embed src=\"http://www.youtube.com/v/$1\" type=\"application/x-shockwave-flash\" width=\"195\" height=\"95\"></embed></object>");
        
        str = str.replaceAll("\\[youtube\\](.*?)\\[/youtube\\]", "$1");
        
        // smileys
//        for(String smile : smileys){
//        	str = str.replaceAll(":" + smile + ":", "<img src=\"/resources/imgs/emoticons/" + smile + ".png" + "\" data-sceditor-emoticon=\":" + smile 
//        			+":\" alt=\":" + smile + ":\" title=\":" + smile +":\">");
//        }
        
//        for(Map.Entry entry : customSmileys.entrySet()){
//        	str = str.replaceAll(entry.getKey().toString(), "<img src=\"/resources/imgs/emoticons/" + entry.getValue().toString() + ".png" + "\" data-sceditor-emoticon=\":" + entry.getValue().toString() 
//        			+":\" alt=\":" + entry.getValue().toString() + ":\" title=\":" + entry.getValue().toString() +":\">");
//        }
        
        // [quote]
        str = str.replaceAll("\\[quote\\](.*?)\\[/quote\\]", "$1");
        
        // [code]
        str = str.replaceAll("\\[code\\](.*?)\\[/code\\]", "$1");
        
        // [align]
        str = str.replaceAll("\\[center\\](.*?)\\[/center\\]", "$1");
        str = str.replaceAll("\\[left\\](.*?)\\[/left\\]", "$1");
        str = str.replaceAll("\\[right\\](.*?)\\[/right\\]", "$1");
        str = str.replaceAll("\\[justify\\](.*?)\\[/justify\\]", "$1");
        
        return str;
    }

    /**
     * @param string
     * @return HTML-formated message
     */
    private String process(String string) {
    	string = string.replaceAll("(\r\n|\n\r|\n|\r)", "<br>");

        StringBuffer buffer = new StringBuffer(string);
        processCode(buffer);

        processNestedTags(buffer,
            "quote",
            "<blockquote>",
            "</blockquote>",
            "<blockquote>",
            "</blockquote>",
            "[*]",
            false,
            true,
            true);

        processNestedTags(buffer,
            "list",
            "<ol type=\"{BBCODE_PARAM}\">",
            "</ol>",
            "<ul>",
            "</ul>",
            "<li>",
            true,
            true,
            true);

        String str = buffer.toString();

        //str = str.replaceAll("(\r\n|\n\r|\n|\r)", "<br>");

        // [color]
        str = str.replaceAll("\\[color=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/color\\]",
            "<span style='color:$1'>$2</span>");

        // [size]
        str = str.replaceAll("\\[size=['\"]?([0-9]|[1-4][0-9])['\"]?\\](.*?)\\[/size\\]",
            "<span style='font-size:$1px'>$2</span>");

        // [b][u][i][s]
        str = str.replaceAll("\\[b\\](.*?)\\[/b\\]", "<b>$1</b>");
        str = str.replaceAll("\\[u\\](.*?)\\[/u\\]", "<u>$1</u>");
        str = str.replaceAll("\\[i\\](.*?)\\[/i\\]", "<i>$1</i>");
        str = str.replaceAll("\\[s\\](.*?)\\[/s\\]", "<strike>$1</strike>");

        // [sub][sup]
        str = str.replaceAll("\\[sup\\](.*?)\\[/sup\\]", "<sup>$1</sup>");
        str = str.replaceAll("\\[sub\\](.*?)\\[/sub\\]", "<sub>$1</sub>");
        
        // [img]
        str = str.replaceAll("\\[img\\](.*?)\\[/img\\]", "<img src='$1' border='0' alt='0'>");

        // [url]
        str = str.replaceAll("\\[url\\](.*?)\\[/url\\]", "<a href='$1' target='_blank'>$1</a>");
        str = str.replaceAll("\\[url=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/url\\]",
            "<a href=\"$1\" target=\"_new\">$2</a>");

        // [email]
        str = str.replaceAll("\\[email\\](.*?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");
        
        // [youtube]
        str = str.replaceAll("\\[youtube\\](.*?)\\[/youtube\\]", "<object width=\"640\" height=\"380\"><param name=\"movie\" value=\"http://www.youtube.com/v/$1\"></param><embed src=\"http://www.youtube.com/v/$1\" type=\"application/x-shockwave-flash\" width=\"640\" height=\"380\"></embed></object>");
        
//        for(Map.Entry entry : customSmileys.entrySet()){
//        	str = str.replaceAll(entry.getKey().toString(), "<img src=\"/resources/imgs/emoticons/" + entry.getValue().toString() + "\" data-sceditor-emoticon=\"" + entry.getValue().toString() 
//        			+"\" alt=\"" + entry.getValue().toString() + "\" title=\"" + entry.getKey().toString() +"\">");
//        }
        
        for(String sCode : sortedCode){
        	str = str.replaceAll(sCode, "<img src='/resources/imgs/emoticons/" + customSmileys.get(sCode) + "' data-sceditor-emoticon='" + customSmileys.get(sCode) 
        			+"' alt='" + customSmileys.get(sCode) + "' title='" + customSmileys.get(sCode) +"'>");
        }
        
        // [quote]
        str = str.replaceAll("\\[quote\\](.*?)\\[/quote\\]", "<blockquote>$1</blockquote>");
        
        // [align]
        str = str.replaceAll("\\[center\\](.*?)\\[/center\\]", "<div style=\"text-align: center\">$1</div>");
        str = str.replaceAll("\\[left\\](.*?)\\[/left\\]", "<div style=\"text-align: left\">$1</div>");
        str = str.replaceAll("\\[right\\](.*?)\\[/right\\]", "<div style=\"text-align: right\">$1</div>");
        str = str.replaceAll("\\[justify\\](.*?)\\[/justify\\]", "<div style=\"text-align: justify\">$1</div>");
        
        return str;
    }
    
    /**
     * @param string
     * @return HTML-formated message
     */
    private String processExcerpt(String string) {
    	string = string.replaceAll("(\r\n|\n\r|\n|\r)", "<br>");

        StringBuffer buffer = new StringBuffer(string);
        processCode(buffer);

        processNestedTags(buffer,
            "quote",
            "<blockquote>",
            "</blockquote>",
            "<blockquote>",
            "</blockquote>",
            "[*]",
            false,
            true,
            true);

        processNestedTags(buffer,
            "list",
            "<ol type=\"{BBCODE_PARAM}\">",
            "</ol>",
            "<ul>",
            "</ul>",
            "<li>",
            true,
            true,
            true);

        String str = buffer.toString();

        //str = str.replaceAll("(\r\n|\n\r|\n|\r)", "<br>");

        // [color]
        str = str.replaceAll("\\[color=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/color\\]",
            "<span style='color:$1'>$2</span>");

        // [size]
        str = str.replaceAll("\\[size=['\"]?([0-9]|[1-2][0-9])['\"]?\\](.*?)\\[/size\\]",
            "<span style='font-size:$1px'>$2</span>");

        // [b][u][i][s]
        str = str.replaceAll("\\[b\\](.*?)\\[/b\\]", "<b>$1</b>");
        str = str.replaceAll("\\[u\\](.*?)\\[/u\\]", "<u>$1</u>");
        str = str.replaceAll("\\[i\\](.*?)\\[/i\\]", "<i>$1</i>");
        str = str.replaceAll("\\[s\\](.*?)\\[/s\\]", "<strike>$1</strike>");

        // [sub][sup]
        str = str.replaceAll("\\[sup\\](.*?)\\[/sup\\]", "<sup>$1</sup>");
        str = str.replaceAll("\\[sub\\](.*?)\\[/sub\\]", "<sub>$1</sub>");
        
        // [img]
        str = str.replaceAll("\\[img\\](.*?)\\[/img\\]", "<img src='$1' border='0' alt='0'>");

        // [url]
        str = str.replaceAll("\\[url\\](.*?)\\[/url\\]", "<a href='$1' target='_blank'>$1</a>");
        str = str.replaceAll("\\[url=['\"]?(.*?[^'\"])['\"]?\\](.*?)\\[/url\\]",
            "<a href=\"$1\" target=\"_new\">$2</a>");

        // [email]
        str = str.replaceAll("\\[email\\](.*?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");

        // [youtube]
       // str = str.replaceAll("\\[youtube\\](.*?)\\[/youtube\\]", "<object width=\"195\" height=\"95\"><param name=\"movie\" value=\"http://www.youtube.com/v/$1\"></param><embed src=\"http://www.youtube.com/v/$1\" type=\"application/x-shockwave-flash\" width=\"195\" height=\"95\"></embed></object>");
        
        str = str.replaceAll("\\[youtube\\](.*?)\\[/youtube\\]", "<img src=\"http://i1.ytimg.com/vi/$1/mqdefault.jpg\" data-thumb=\"//i1.ytimg.com/vi/$1/mqdefault.jpg\" alt=\"Thumbnail\" width=\"175\" data-group-key=\"thumb-group-0\">");
        
        
//      for(Map.Entry entry : customSmileys.entrySet()){
//    	str = str.replaceAll(entry.getKey().toString(), "<img src=\"/resources/imgs/emoticons/" + entry.getValue().toString() + "\" data-sceditor-emoticon=\"" + entry.getValue().toString() 
//    			+"\" alt=\"" + entry.getValue().toString() + "\" title=\"" + entry.getKey().toString() +"\">");
//    }
    
        for(String sCode : sortedCode){
        	str = str.replaceAll(sCode, "<img src='/resources/imgs/emoticons/" + customSmileys.get(sCode) + "' data-sceditor-emoticon='" + customSmileys.get(sCode) 
        			+"' alt='" + customSmileys.get(sCode) + "' title='" + customSmileys.get(sCode) +"'>");
        }
        
        // [quote]
        str = str.replaceAll("\\[quote\\](.*?)\\[/quote\\]", "<blockquote>$1</blockquote>");
        
        // [align]
        str = str.replaceAll("\\[center\\](.*?)\\[/center\\]", "<div style=\"text-align: center\">$1</div>");
        str = str.replaceAll("\\[left\\](.*?)\\[/left\\]", "<div style=\"text-align: left\">$1</div>");
        str = str.replaceAll("\\[right\\](.*?)\\[/right\\]", "<div style=\"text-align: right\">$1</div>");
        str = str.replaceAll("\\[justify\\](.*?)\\[/justify\\]", "<div style=\"text-align: justify\">$1</div>");
        
        return str;
    }

    private static void processCode(StringBuffer buffer) {
        int start = buffer.indexOf("[code]");
        int end;

        for (; (start >= 0) && (start < buffer.length()); start = buffer.indexOf("[code]", end)) {

            end = buffer.indexOf("[/code]", start);

            if (end < 0) {
                break;
            }

            end += "[/code]".length();

            String content = buffer.substring(start + "[code]".length(), end - "[/code]".length());
            content = escapeBBcode(content);

           
           /* String replacement = "<!-- [ -code- ] --></span>" //
                + "<table width='90%' cellspacing='1' cellpadding='3' border='0' align='center'>"
                + "<tr><td><span class='genmed'><b>Code:</b></span></td></tr>"
                + "<tr><td class='code'>"
                + content
                + "</td></tr></table><span class='postbody'><!-- [/ -code- ] -->";*/
            
            String replacement = "<code>" + content + "</code>";
            
            content = content.replaceAll("<br>", "\n");
            
            /*String replacement = "<!-- [ -code- ] --></span>" //
                + "<textarea name=\"code\" id=\"code\" class=\"java\" rows=\"15\" cols=\"100\">"
                + content
                + "</textarea><span class='postbody'><!-- [/ -code- ] -->";            
            */
            buffer.replace(start, end, replacement);

            end = start + replacement.length();
        }
    }

    /**
     * @param content
     * @return -
     */
    public static String escapeBBcode(String content) {
        // escaping single characters
        content = replaceAll(content, "[]\t".toCharArray(), new String[] { "&#91;",
            "&#93;",
            "&nbsp; &nbsp;" });

        // taking off start and end line breaks
        content = content.replaceAll("\\A\r\n|\\A\r|\\A\n|\r\n\\z|\r\\z|\n\\z", "");

        // replacing spaces for &nbsp; to keep indentation
        content = content.replaceAll("  ", "&nbsp; ");
        content = content.replaceAll("  ", " &nbsp;");

        return content;
    }

    /**
     * @param content
     * @return -
     */
    private static String escapeHtml(String content) {
        // escaping single characters
        content = replaceAll(content, "&<>".toCharArray(), new String[] { "&amp;", "&lt;", "&gt;" });

        // replacing line breaks for <br>
        //content = content.replaceAll("\r\n", "<br>");
        //content = replaceAll(content, "\n\r".toCharArray(), new String[] { "<br>", "<br>" });

        return content;
    }

    private static String replaceAll(String str, char[] chars, String[] replacement) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean matched = false;
            for (int j = 0; j < chars.length; j++) {
                if (c == chars[j]) {
                    buffer.append(replacement[j]);
                    matched = true;
                }
            }
            if (!matched) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * @param buffer
     * @param tagName
     * @param openSubstWithParam
     * @param closeSubstWithParam
     * @param openSubstWithoutParam
     * @param closeSubstWithoutParam
     * @param internalSubst
     * @param processInternalTags
     * @param acceptParam
     * @param requiresQuotedParam
     */
    private static void processNestedTags(
        StringBuffer buffer,
        String tagName,
        String openSubstWithParam,
        String closeSubstWithParam,
        String openSubstWithoutParam,
        String closeSubstWithoutParam,
        String internalSubst,
        boolean processInternalTags,
        boolean acceptParam,
        boolean requiresQuotedParam) {
        String str = buffer.toString();

        Stack openStack = new Stack();
        Set subsOpen = new HashSet();
        Set subsClose = new HashSet();
        Set subsInternal = new HashSet();

        String openTag = CR_LF + "\\["
            + tagName
            + (acceptParam ? (requiresQuotedParam ? "(?:=\"(.*?)\")?" : "(?:=\"?(.*?)\"?)?") : "")
            + "\\]"
            + CR_LF;
        String closeTag = CR_LF + "\\[/" + tagName + "\\]" + CR_LF;
        String internTag = CR_LF + "\\[\\*\\]" + CR_LF;

        String patternString = "(" + openTag + ")|(" + closeTag + ")";

        if (processInternalTags) {
            patternString += "|(" + internTag + ")";
        }

        Pattern tagsPattern = Pattern.compile(patternString);
        Matcher matcher = tagsPattern.matcher(str);

        int openTagGroup;
        int paramGroup;
        int closeTagGroup;
        int internalTagGroup;

        if (acceptParam) {
            openTagGroup = 1;
            paramGroup = 2;
            closeTagGroup = 3;
            internalTagGroup = 4;
        } else {
            openTagGroup = 1;
            paramGroup = -1; // INFO
            closeTagGroup = 2;
            internalTagGroup = 3;
        }

        while (matcher.find()) {
            int length = matcher.end() - matcher.start();
            MutableCharSequence matchedSeq = new MutableCharSequence(str, matcher.start(), length);

            // test opening tags
            if (matcher.group(openTagGroup) != null) {
                if (acceptParam && (matcher.group(paramGroup) != null)) {
                    matchedSeq.param = matcher.group(paramGroup);
                }

                openStack.push(matchedSeq);

                // test closing tags
            } else if ((matcher.group(closeTagGroup) != null) && !openStack.isEmpty()) {
                MutableCharSequence openSeq = (MutableCharSequence) openStack.pop();

                if (acceptParam) {
                    matchedSeq.param = openSeq.param;
                }

                subsOpen.add(openSeq);
                subsClose.add(matchedSeq);

                // test internal tags
            } else if (processInternalTags && (matcher.group(internalTagGroup) != null)
                && (!openStack.isEmpty())) {
                subsInternal.add(matchedSeq);
            } else {
                // assert (false);
            }
        }

        LinkedList subst = new LinkedList();
        subst.addAll(subsOpen);
        subst.addAll(subsClose);
        subst.addAll(subsInternal);

        Collections.sort(subst, new Comparator() {
            public int compare(Object o1, Object o2) {
                MutableCharSequence s1 = (MutableCharSequence) o1;
                MutableCharSequence s2 = (MutableCharSequence) o2;
                return -(s1.start - s2.start);
            }
        });

        buffer.delete(0, buffer.length());

        int start = 0;

        while (!subst.isEmpty()) {
            MutableCharSequence seq = (MutableCharSequence) subst.removeLast();
            buffer.append(str.substring(start, seq.start));

            if (subsClose.contains(seq)) {
                if (seq.param != null) {
                    buffer.append(closeSubstWithParam);
                } else {
                    buffer.append(closeSubstWithoutParam);
                }
            } else if (subsInternal.contains(seq)) {
                buffer.append(internalSubst);
            } else if (subsOpen.contains(seq)) {
                Matcher m = Pattern.compile(openTag).matcher(str.substring(seq.start,
                    seq.start + seq.length));

                if (m.matches()) {
                    if (acceptParam && (seq.param != null)) {
                        buffer.append( //
                            openSubstWithParam.replaceAll("\\{BBCODE_PARAM\\}", seq.param));
                    } else {
                        buffer.append(openSubstWithoutParam);
                    }
                }
            }

            start = seq.start + seq.length;
        }

        buffer.append(str.substring(start));
    }

    static class MutableCharSequence implements CharSequence {
        /** */
        public CharSequence base;

        /** */
        public int start;

        /** */
        public int length;

        /** */
        public String param = null;

        /**
         */
        public MutableCharSequence() {
            //
        }

        /**
         * @param base
         * @param start
         * @param length
         */
        public MutableCharSequence(CharSequence base, int start, int length) {
            reset(base, start, length);
        }

        /**
         * @see java.lang.CharSequence#length()
         */
        public int length() {
            return this.length;
        }

        /**
         * @see java.lang.CharSequence#charAt(int)
         */
        public char charAt(int index) {
            return this.base.charAt(this.start + index);
        }

        /**
         * @see java.lang.CharSequence#subSequence(int, int)
         */
        public CharSequence subSequence(int pStart, int end) {
            return new MutableCharSequence(this.base,
                this.start + pStart,
                this.start + (end - pStart));
        }

        /**
         * @param pBase
         * @param pStart
         * @param pLength
         * @return -
         */
        public CharSequence reset(CharSequence pBase, int pStart, int pLength) {
            this.base = pBase;
            this.start = pStart;
            this.length = pLength;

            return this;
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            StringBuffer sb = new StringBuffer();

            for (int i = this.start; i < (this.start + this.length); i++) {
                sb.append(this.base.charAt(i));
            }

            return sb.toString();
        }

    }
    
}
