package je.techtribes.component.activity;

import je.techtribes.domain.Activity;
import je.techtribes.util.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Date;

public class JdbcActivityDao implements ActivityDao {

    private DataSource dataSource;

    public JdbcActivityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void storeActivity(Collection<Activity> activityCollection) {
        Date now = DateUtils.getNow();
        for (Activity activity : activityCollection) {
            JdbcTemplate template = new JdbcTemplate(dataSource);
            template.update("insert into activity (content_source_id, international_talks, local_talks, content, tweets, events, last_activity_datetime, activity_datetime) values (?, ?, ?, ?, ?, ?, ?, ?)",
                    activity.getContentSource().getId(),
                    activity.getNumberOfInternationalTalks(),
                    activity.getNumberOfLocalTalks(),
                    activity.getNumberOfNewsFeedEntries(),
                    activity.getNumberOfTweets(),
                    activity.getNumberOfEvents(),
                    activity.getLastActivityDate(),
                    now
                    );
        }
    }

    @Override
    public Collection<Activity> getRecentActivity() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select content_source_id, international_talks, local_talks, content, tweets, events, last_activity_datetime, activity_datetime from activity where activity_datetime = (select max(activity_datetime) from activity)",
                new ActivityRowMapper());
    }

}
