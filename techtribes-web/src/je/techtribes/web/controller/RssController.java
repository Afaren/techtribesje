package je.techtribes.web.controller;

import je.techtribes.domain.ContentItem;
import je.techtribes.component.newsfeedentry.NewsFeedEntryComponent;
import je.techtribes.util.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.List;

@Controller
public class RssController extends AbstractController {

    private NewsFeedEntryComponent newsFeedEntryComponent;

    @Autowired
    public RssController(NewsFeedEntryComponent newsFeedEntryComponent) {
        this.newsFeedEntryComponent = newsFeedEntryComponent;
    }

    @RequestMapping(value = "/rss.xml", method = RequestMethod.GET)
	public View viewRecentNews(ModelMap model) {
        List<? extends ContentItem> newsFeedEntries = newsFeedEntryComponent.getRecentNewsFeedEntries(1, PageSize.RECENT_NEWS_FEED_ENTRIES);

        model.addAttribute("newsFeedEntries", newsFeedEntries);

        return new InternalResourceView("/WEB-INF/views-xml/rss.jsp");
	}

}
