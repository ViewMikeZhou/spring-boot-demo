package com.hengda.screen.help;

import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseHelp {
    public static ParseHelp formDataParseHelp;
    private final ServletFileUpload upload;
    private Gson gson;

    private ParseHelp() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
    }

    public static ParseHelp getInstance() {
        if (formDataParseHelp == null) {
            synchronized (ParseHelp.class) {
                formDataParseHelp = new ParseHelp();
            }
        }
        return formDataParseHelp;
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public String objectParseToGson(Object obj){
        return getGson().toJson(obj);
    }

    public <T> Map<String, T> parseRequest(HttpServletRequest request) {
        Map<String, T> map;
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            map = new HashMap<>();
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), (T) fileItem.getString("utf-8"));//如果你页面编码是utf-8的
                }
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }


}
