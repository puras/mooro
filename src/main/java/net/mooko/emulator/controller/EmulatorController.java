package net.mooko.emulator.controller;

import net.mooko.common.holder.ObjectMapperHolder;
import net.mooko.common.json.Converter;
import net.mooko.common.json.JacksonConverter;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author puras <he@puras.me>
 * @since 16/3/3  下午3:38
 */
@RestController
public class EmulatorController {
    @RequestMapping(value ="/**/*", method = RequestMethod.GET)
    public Object get(HttpServletRequest req) {
        return doRest(req);
    }

    @RequestMapping(value ="/**/*", method = RequestMethod.POST)
    public Object post(HttpServletRequest req) {
        return doRest(req);
    }

    @RequestMapping(value ="/**/*", method = RequestMethod.PUT)
    public Object put(HttpServletRequest req) {
        return doRest(req);
    }

    @RequestMapping(value ="/**/*", method = RequestMethod.DELETE)
    public Object del(HttpServletRequest req) {
        return doRest(req);
    }

    private Object doRest(HttpServletRequest req) {
        displayRequestInfo(req);
        String fileName = (getFileName(req));
        System.out.println("File Name is: " + fileName);
        InputStream is = this.getClass().getResourceAsStream("/data/" + fileName);

        if (null == is) {
            return "File is not found.";
        }

        String json = "Parse Error.";
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            json = writer.toString();
            System.out.println("******************************");
            System.out.println("Response Body: ");
            System.out.println(json);
            System.out.println("******************************");
            Converter converter = new JacksonConverter(ObjectMapperHolder.getInstance().getMapper());
            Object obj = converter.convertToObject(json, Object.class);
            return obj;
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
//            json = reader.lines().collect(Collectors.joining());
//            System.out.println(json);
//            Converter converter = new JacksonConverter(ObjectMapperHolder.getInstance().getMapper());
//            Object obj = converter.convertToObject(json, Object.class);
//            return obj;
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return json;
//        }
    }

    private String getFileName(HttpServletRequest req) {
        String url = req.getRequestURI();
        String method = req.getMethod();
        url = url.replaceAll("/", "_");
        if (url.startsWith("_")) {
            url = url.substring(1);
        }

        Map<String, String[]> params = req.getParameterMap();
        String queryString = "";
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = "";
            String[] values = entry.getValue();
            if (null != values && values.length > 0) {
                for (String val : values) {
                    value += val;
                }
            }
            queryString += key + "=" + value + "+";
        }

        if (queryString.endsWith("+")) {
            queryString = queryString.substring(0, queryString.length() - 1);
        }

        if (null != queryString && queryString.length() > 0) {
            url += "||" + queryString;
        }

        return url.toLowerCase() + "-" + method.toLowerCase() + ".json";
    }

    private void displayRequestInfo(HttpServletRequest req) {
        System.out.println("==============================");
        System.out.println("Request URL:" + req.getRequestURI());
        System.out.println("Request Method: " + req.getMethod());
        System.out.println("------------------------------");
        System.out.println("Request Header");
        System.out.println("------------------------------");
        Enumeration<String> enumeration = req.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            System.out.println("Header[" + key + "]=" + req.getHeader(key));
        }
        System.out.println("------------------------------");
        System.out.println("Request Query String");
        System.out.println("------------------------------");
        Map<String, String[]> params = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = "";
            String[] values = entry.getValue();
            if (null != values && values.length > 0) {
                for (String val : values) {
                    value += val;
                }
            }
            System.out.println("QueryString[" + key + "]=" + value);
        }
        if (req.getMethod().toLowerCase().equals("post")) {
            System.out.println("------------------------------");
            System.out.println("Request Body");
            System.out.println("------------------------------");
            try {
                BufferedReader reader = req.getReader();
                String buffer = null;
                while ((buffer = reader.readLine()) != null) {
                    System.out.println(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("==============================");
    }
}
