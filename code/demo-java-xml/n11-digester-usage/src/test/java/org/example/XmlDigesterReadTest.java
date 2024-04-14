package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester3.BeanPropertySetterRule;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.Rule;
import org.example.entity.MyContent;
import org.example.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class XmlDigesterReadTest {
    @Test
    void test() {
        Digester digester = new Digester();
        try {
            File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), "message001.xml");
            Assertions.assertTrue(file.exists());
            // 映射
            // 当使用 addObjectCreate() 方法时，会创建一个对象进栈，许多重要的方法都是相对于栈顶元素或次栈顶元素来进行的。
            digester.addObjectCreate("my-content", MyContent.class); // 入栈
            digester.addObjectCreate("my-content/posts", ArrayList.class); // 入栈
            digester.addObjectCreate("my-content/posts/post", Post.class); // 入栈
            digester.addSetNext("my-content/posts/post", "add"); // 出栈
            digester.addSetNext("my-content/posts", "setPosts"); // 出栈
            // 设置元素值映射
            digester.addBeanPropertySetter("my-content/web-site", "webSite");
            digester.addBeanPropertySetter("my-content/description", "description");
            digester.addBeanPropertySetter("my-content/posts/post/title", "title");
            // 设置元素上的属性值映射
            digester.addSetProperties("my-content/posts/post", "id", "id");
            // 添加自定义映射规则
            final String random = UUID.randomUUID().toString();
            digester.addRule("my-content/owner", new BeanPropertySetterRule("owner"));
            digester.addRule("my-content/posts/post/content", new Rule() {
                @Override
                public void body(String namespace, String name, String text) throws Exception {
                    System.out.println("name = " + name);
                    System.out.println("text = " + text);
                    BeanUtils.populate(getDigester().peek(), new HashMap() {
                        {
                            this.put("content", text+random);
                        }
                    });
                }
            });

            // 解析
            MyContent parse = digester.parse(file);
            log.info("{}", parse);
            // 校验
            Assertions.assertEquals("http://www.example.org", parse.getWebSite());
            Assertions.assertEquals("steven", parse.getOwner());
            Assertions.assertEquals(2, parse.getPosts().size());
            Assertions.assertArrayEquals(new String[] {"P001", "P002"}, parse.getPosts().stream().map(Post::getId).toArray());
            Assertions.assertArrayEquals(new String[] {"文章标题01", "文章标题02"}, parse.getPosts().stream().map(Post::getTitle).toArray());
            Assertions.assertArrayEquals(new String[] {"hello world 01！"+random, "hello world 02！"+random}, parse.getPosts().stream().map(Post::getContent).toArray());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
