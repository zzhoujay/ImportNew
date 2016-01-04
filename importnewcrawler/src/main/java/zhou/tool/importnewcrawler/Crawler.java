package zhou.tool.importnewcrawler;

import com.squareup.okhttp.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhou on 16-1-1.
 */
public final class Crawler implements Serializable {

    private String URL;
    private String category;

    public Crawler(String siteUrl, String category) {
        this.category = category;
        URL = siteUrl + "/%s/page/%d";
    }

    private static final Pattern pattern_time1 = Pattern.compile(".* (\\w{4}/\\w{2}/\\w{2})");
    private static final Pattern pattern_time2 = Pattern.compile("^(\\w{4}/\\w{2}/\\w{2})");

    public List<PostItem> getPostByCategory(int page, String category) throws IOException {
        return parsePostList(loadPostPageByCategory(page, category));
    }

    public List<PostItem> getPostByTag(int page, String tag) throws IOException {
        return parsePostList(loadPostPageByTag(page, tag));
    }

    public Post getPost(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        return parsePost(NetworkManager.getInstance().requestString(request));
    }

    private String loadPostPageByCategory(int page, String category) throws IOException {
        String url;
        if (category == null) {
            url = String.format(URL, "all-posts", page);
        } else {
            url = String.format(URL, String.format("%s/%s", this.category, category), page);
        }
        Request request = new Request.Builder().get().url(url).build();
        return NetworkManager.getInstance().requestString(request);
    }

    private String loadPostPageByTag(int page, String tag) throws IOException {
        String url = String.format(URL, String.format("tag/%s", tag), page);
        Request request = new Request.Builder().get().url(url).build();
        return NetworkManager.getInstance().requestString(request);
    }

    private List<PostItem> parsePostList(String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();
        Element archive = body.getElementById("archive");
        Elements posts = archive.getElementsByClass("post");
        List<PostItem> postItems = new ArrayList<PostItem>(posts.size());
        for (Element element : posts) {
            postItems.add(paresElementToPostItem(element));
        }
        return postItems;
    }

    private Post parsePost(String html) {
        return parseElementToPost(Jsoup.parse(html).body());
    }

    private PostItem paresElementToPostItem(Element element) {
        Element postThumb = element.getElementsByClass("post-thumb").first();
        String thumb = postThumb.getElementsByTag("img").first().attr("src");
        String thumbTitle = postThumb.getElementsByTag("a").first().attr("title");

        Element postMeta = element.getElementsByClass("post-meta").first();

        Element p = postMeta.getElementsByTag("p").first();

        String time = "2012/11/11";

        Matcher matcher = pattern_time1.matcher(p.text());
        if (matcher.find()) {
            time = matcher.group(1);
        }

        Elements pas = p.getElementsByTag("a");

        Element pa1 = pas.first();
        String title = pa1.attr("title");
        String href = pa1.attr("href");

        Element pa2 = pas.get(1);
        String categoryHref = pa2.attr("href");
        String categoryTag = categoryHref.substring(categoryHref.lastIndexOf("/") + 1);
        String category = pa2.text();

        String content = postMeta.getElementsByTag("span").first().getElementsByTag("p").first().text();

        String reply;
        if (pas.size() >= 3) {
            Element pa3 = pas.last();
            reply = pa3.text();
        } else {
            reply = "0条评论";
        }

        return new PostItem(thumb, thumbTitle, title, time, category, categoryTag, reply, content, href);
    }

    private Post parseElementToPost(Element body) {
        Element root = body.getElementsByClass("grid-8").first().getElementsByTag("div").first();

        String title = root.getElementsByClass("entry-header").first().getAllElements().first().text();

        Element meta = root.getElementsByClass("entry-meta").first().getElementsByClass("entry-meta-hide-on-mobile").first();

        String time = "2012/11/11";
        Matcher matcher = pattern_time2.matcher(meta.text());
        if (matcher.find()) {
            time = matcher.group(1);
        }

        Elements metaAs = meta.getElementsByTag("a");

        Element a = metaAs.first();
        String categoryHref = a.attr("href");
        String categoryTag = categoryHref.substring(categoryHref.lastIndexOf("/") + 1);
        String category = a.text();

        String reply = metaAs.get(1).text();

        List<String> tags = new ArrayList<String>(3);
        for (int i = 2, size = metaAs.size(); i < size; i++) {
            tags.add(metaAs.get(i).text());
        }

        Element entry = root.getElementsByClass("entry").first();

        entry.removeClass("copyright-area");
        entry.removeClass("author-bio");

        String content = entry.html();

        return new Post(null, null, title, time, category, categoryTag, reply, content, null, tags);
    }

}
