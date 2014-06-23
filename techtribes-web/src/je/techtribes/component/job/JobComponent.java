package je.techtribes.component.job;

import com.structurizr.annotation.Component;
import je.techtribes.domain.ContentSource;
import je.techtribes.domain.Job;

import java.util.List;

@Component(description = "Provides access to information about recently posted jobs.")
public interface JobComponent {

    /**
     * Gets a list of the most recently posted jobs, latest first.
     */
    List<Job> getRecentJobs(int pageSize);

    /**
     * Gets a list of the most recently posted jobs, latest first, for the given ContentSource.
     * Optionally, expired jobs can be included.
     */
    List<Job> getRecentJobs(ContentSource contentSource, int pageSize, boolean includeExpiredJobs);

}
