/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package webserver;

import java.util.*;
/**
 *
 * @author Lenovo
 */
public class ContentTypeMapping {

    HashMap<String,String> extensionAndType = new HashMap<String,String>();
    private static ContentTypeMapping type;

    public static ContentTypeMapping getConentTypeMapper()
    {
        if(type == null)
        {
            type = new ContentTypeMapping();
        }
        return type;
    }

    private ContentTypeMapping()
    {
        extensionAndType.put("html","text/html");
        extensionAndType.put("css","text/css");
        extensionAndType.put("js","application/x-javascript");

        extensionAndType.put("jpg","image/jpeg");
        extensionAndType.put("png","image/png");
        extensionAndType.put("gif","image/gif");
        
        
       
    }

    public String getContentType(String extension)
    {
        return extensionAndType.get(extension);
    }
}
