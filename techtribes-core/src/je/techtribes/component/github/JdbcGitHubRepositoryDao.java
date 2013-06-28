package je.techtribes.component.github;

import je.techtribes.domain.ContentSource;
import je.techtribes.domain.GitHubRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

class JdbcGitHubRepositoryDao implements GitHubRepositoryDao {

    private DataSource dataSource;

    JdbcGitHubRepositoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<GitHubRepository> getRepositories() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select github_repo.content_source_id, github_repo.name, github_repo.description, github_repo.url from github_repo, content_source where github_repo.content_source_id = content_source.id order by name",
                new GitHubRepositoryRowMapper());
    }

    @Override
    public List<GitHubRepository> getRepositories(ContentSource contentSource) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select content_source_id, name, description, url from github_repo where content_source_id = ? order by name",
                new Object[] { contentSource.getId() },
                new GitHubRepositoryRowMapper());
    }

    @Override
    public void setRepositories(List<GitHubRepository> repositories, ContentSource contentSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update("delete from github_repo where content_source_id = ?", contentSource.getId());

        for (GitHubRepository repository : repositories) {
            template.update("insert into github_repo (content_source_id, name, description, url) values (?, ?, ?, ?)",
                repository.getContentSource().getId(),
                repository.getName(),
                repository.getDescription(),
                repository.getUrl());
        }
    }

}
