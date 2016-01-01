package zhou.app.importnew.common;

import java.util.Arrays;
import java.util.List;

import zhou.app.importnew.data.Type;

/**
 * Created by zhou on 16-1-1.
 */
public class Config {

    public static final List<Type> javaTypes, androidTypes, pythonTypes, iosTypes, webTypes, design;

    static {

        javaTypes = Arrays.asList(new Type("全部", null), new Type("资讯", "news"), new Type("Web", "web-development"),
                new Type("架构", "architecture"), new Type("基础技术", "basic"), new Type("书籍", "books"), new Type("教程", "tutorial"));

        androidTypes = Arrays.asList(new Type("所有文章", null), new Type("行业动态", "news"), new Type("技术分享", "tech-share"),
                new Type("产品设计", "product"));

        iosTypes = Arrays.asList(new Type("所有文章", null), new Type("业界动态", "news"), new Type("iOS开发", "ios-dev"),
                new Type("Swift开发", "swift-dev"), new Type("产品推广", "marketing"), new Type("产品设计", "product"));

        pythonTypes = Arrays.asList(new Type("所有文章", null), new Type("观点与动态", "news"), new Type("基础知识", "basic"),
                new Type("系列教程", "guide"), new Type("实践项目", "project"), new Type("工具与框架应用", "tools"));

        webTypes = Arrays.asList(new Type("所有文章", null), new Type("JavaScript", "javascript-2"), new Type("HTML5", "html5"),
                new Type("CSS", "css"));

        design = Arrays.asList(new Type("所有文章", null), new Type("UI设计", "ui-design"), new Type("网页设计", "web-design"),
                new Type("交互设计", "inter-design"), new Type("用户体验", "ue-design"), new Type("设计教程", "guide"),
                new Type("设计职场", "design-career"));
    }

}
