package com.verse.app;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.verse.common.util.JwtTokenUtils;
import org.apache.commons.io.FileUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class CommonApplicationTests {

    @Test
    void contextLoads() {


    }

    @Test
    void testHttpComponentsGet() {
        String url = "https://so.gushiwen.cn/gushi/tangshi.aspx";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse execute = httpClient.execute(new HttpGet(url));
            HttpEntity entity = execute.getEntity();
            String s = EntityUtils.toString(entity);

            String[] split = s.split("\n");
            for (String s1 : split) {
                if(s1.startsWith("<span><a href=")) {
                    String[] split1 = s1.split("\"");
                    String url2 = "https://so.gushiwen.cn" + split1[1];
                    try {
                        testGetShi(url2);
                    }catch (Exception e) {
                        System.out.println(e);
                        System.out.println(url2);
                    }

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetShi(String url) {
//        String url = "https://so.gushiwen.cn/shiwenv_8d889937d1fe.aspx";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse execute = httpClient.execute(new HttpGet(url));
            HttpEntity entity = execute.getEntity();
            String s = EntityUtils.toString(entity);
            String[] split = s.split("\n");
            File file = new File("C:\\project\\temp\\poem.txt");
            for (int i = 0; i < split.length; i++) {
                if(split[i].startsWith("<textarea")) {
                    String title = split[i].substring(split[i].indexOf("《") + 1, split[i].indexOf("》"));
                    String content = split[i].split(">")[1].substring(0, split[i].split(">")[1].indexOf("——"));
                    String author = split[i].substring(split[i].indexOf("——") + 2, split[i].indexOf("《"));

                    String[] split1 = author.split("·");
                    String dynasty = split1[0];
                    if(dynasty.contains("代")) {
                        dynasty.replace("代", "朝");
                    }
                    author = split1[1];
                    String info = "{\"title\":" + "\""+title+"\"" + "," + "\"content\":" + "\"" + content + "\"," + "\"author\":"
                            + "\"" + author + "\"" + "\"dynasty\":" + "\"" + dynasty + "\"},\n";
//                    System.out.println(title + "    " + content + "    " + "    " + author);
                    FileUtils.writeStringToFile(file, info, StandardCharsets.UTF_8, true);
                    break;
                }
            }

//            System.out.println(s);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testGetShi() throws IOException {
        String url = "https://so.gushiwen.cn/shiwenv_f635f8f8edb7.aspx";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse execute = httpClient.execute(new HttpGet(url));
        HttpEntity entity = execute.getEntity();
        String s = EntityUtils.toString(entity);

        System.out.println(s);


    }

    @Test
    public void testJWT() throws Exception {
        String caicia = JwtTokenUtils.createToken("caicia");
        DecodedJWT verify = JwtTokenUtils.verify(caicia);
        System.out.println(verify.getClaim("username").asString());
    }

}
