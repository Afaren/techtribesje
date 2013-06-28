package je.techtribes.component.talk;

import je.techtribes.domain.ContentSource;
import je.techtribes.domain.Talk;
import je.techtribes.util.ContentSourceToIdConverter;
import je.techtribes.util.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

class JdbcTalkDao implements TalkDao {

    private DataSource dataSource;

    JdbcTalkDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Talk> getTalks(Date end) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select id, name, description, type, event_name, city, country, content_source_id, url, talk_date, slides_url, video_url from talk where talk_date <= ? order by talk_date desc",
                new Object[]{end},
                new TalkRowMapper());
    }

    @Override
    public List<Talk> getTalksByYear(int year) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        Date start = DateUtils.getStartOfYear(year);
        Date end = DateUtils.getEndOfYear(year);

        return select.query("select id, name, description, type, event_name, city, country, content_source_id, url, talk_date, slides_url, video_url from talk where talk_date between ? and ? order by talk_date desc",
                new Object[] { start, end },
                new TalkRowMapper());
    }

    @Override
    public List<Talk> getTalks(ContentSource contentSource) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select id, name, description, type, event_name, city, country, content_source_id, url, talk_date, slides_url, video_url from talk where content_source_id = ? order by talk_date desc",
                new Object[]{contentSource.getId()},
                new TalkRowMapper());
    }

    @Override
    public List<Talk> getTalks(Collection<ContentSource> contentSources) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        StringBuilder buf = new StringBuilder();
        buf.append("select id, name, description, type, event_name, city, country, content_source_id, url, talk_date, slides_url, video_url from talk where content_source_id in (");
        for (int i = 1; i <= contentSources.size(); i++) {
            buf.append("?");
            if (i < contentSources.size()) {
                buf.append(",");
            }
        }
        buf.append(") order by talk_date desc");

        Collection<Integer> contentSourceIds = new ContentSourceToIdConverter().getIds(contentSources);
        return select.query(buf.toString(), contentSourceIds.toArray(), new TalkRowMapper());
    }

    @Override
    public Talk getTalk(int id) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.queryForObject("select id, name, description, type, event_name, city, country, content_source_id, url, talk_date, slides_url, video_url from talk where id = ?",
                new Object[]{id},
                new TalkRowMapper());
    }

    @Override
    public long getNumberOfLocalTalks(int id, Date start, Date end) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.queryForLong(
                "select count(*) from talk where content_source_id = ? and talk_date between ? and ? and country in ('Jersey', 'Guernsey')",
                id, start, end);
    }

    @Override
    public long getNumberOfInternationalTalks(int id, Date start, Date end) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.queryForLong(
                "select count(*) from talk where content_source_id = ? and talk_date between ? and ? and country not in ('Jersey', 'Guernsey')",
                id, start, end);
    }

}
