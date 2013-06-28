package je.techtribes.component.badge;

import je.techtribes.component.AbstractComponent;
import je.techtribes.component.contentsource.ContentSourceComponent;
import je.techtribes.domain.ContentSource;
import je.techtribes.domain.badge.AwardedBadge;
import je.techtribes.util.PageSize;

import java.util.List;

class BadgeComponentImpl extends AbstractComponent implements BadgeComponent {

    private BadgeDao badgeDao;
    private ContentSourceComponent contentSourceComponent;

    BadgeComponentImpl(BadgeDao badgeDao, ContentSourceComponent contentSourceComponent) {
        this.badgeDao = badgeDao;
        this.contentSourceComponent = contentSourceComponent;
    }

    @Override
    public List<AwardedBadge> getAwardedBadges() {
        try {
            List<AwardedBadge> awardedBadges = badgeDao.getBadges();
            enrich(awardedBadges);

            return awardedBadges;
        } catch (Exception e) {
            BadgeException be = new BadgeException("Error while loading badges", e);
            logError(be);
            throw be;
        }
    }

    @Override
    public List<AwardedBadge> getAwardedBadges(ContentSource contentSource) {
        try {
            List<AwardedBadge> awardedBadges = badgeDao.getBadges(contentSource);
            enrich(awardedBadges);

            return awardedBadges;
        } catch (Exception e) {
            BadgeException be = new BadgeException("Error while loading badges for content source with ID " + contentSource.getId(), e);
            logError(be);
            throw be;
        }
    }

    @Override
    public void add(AwardedBadge badge) {
        try {
            badgeDao.add(badge);
        } catch (Exception e) {
            BadgeException be = new BadgeException("Error while loading badges", e);
            logError(be);
            throw be;
        }
    }

    @Override
    public List<AwardedBadge> getRecentAwardedBadges(int pageSize) {
        try {
            PageSize.validatePageSize(pageSize);
            List<AwardedBadge> awardedBadges = badgeDao.getRecentAwardedBadges(pageSize);
            enrich(awardedBadges);

            return awardedBadges;
        } catch (Exception e) {
            BadgeException be = new BadgeException("Error getting recent badge winners", e);
            logError(be);
            throw be;
        }
    }

    private void enrich(List<AwardedBadge> awardedBadges) {
        for (AwardedBadge awardedBadge : awardedBadges) {
            ContentSource contentSource = contentSourceComponent.findById(awardedBadge.getContentSourceId());
            awardedBadge.setContentSource(contentSource);
        }
    }

}
