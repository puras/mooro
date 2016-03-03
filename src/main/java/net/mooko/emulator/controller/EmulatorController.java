package net.mooko.emulator.controller;

import net.mooko.common.holder.ObjectMapperHolder;
import net.mooko.common.json.Converter;
import net.mooko.common.json.JacksonConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

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
        String fileName = (getFileName(req));
        System.out.println("File Name is: " + fileName);
        InputStream is = this.getClass().getResourceAsStream("/data/" + fileName);

        if (null == is) {
            return "File is not found.";
        }

        String json = "Parse Error.";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            json = reader.lines().collect(Collectors.joining());
            System.out.println(json);
            Converter converter = new JacksonConverter(ObjectMapperHolder.getInstance().getMapper());
            Object obj = converter.convertToObject(json, Object.class);
            return obj;
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
    }

    private String getFileName(HttpServletRequest req) {
        String url = req.getRequestURI();
        String method = req.getMethod();
        url = url.replaceAll("/", "_");
        if (url.startsWith("_")) {
            url = url.substring(1);
        }
        return url.toLowerCase() + "-" + method.toLowerCase() + ".json";
    }
}
