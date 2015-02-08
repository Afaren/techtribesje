package je.techtribes;

import com.structurizr.Workspace;
import com.structurizr.model.*;

public class CreateBaseModel extends AbstractUtility {

    public static void main(String[] args) throws Exception {
        Workspace workspace = new Workspace("techtribes.je", "The software architecture model for the techtribes.je system");
        Model model = workspace.getModel();
        SoftwareSystem techTribes = model.addSoftwareSystem(Location.Internal, "techtribes.je", "techtribes.je is the only way to keep up to date with the IT, tech and digital sector in Jersey and Guernsey, Channel Islands");

        Person anonymousUser = model.addPerson(Location.External, "Anonymous User", "Anybody on the web.");
        Person authenticatedUser = model.addPerson(Location.External, "Aggregated User", "A user or business with content that is aggregated into the website.");
        Person adminUser = model.addPerson(Location.External, "Administration User", "A system administration user.");
        anonymousUser.uses(techTribes, "View people, tribes (businesses, communities and interest groups), content, events, jobs, etc from the local tech, digital and IT sector.");
        authenticatedUser.uses(techTribes, "Manage user profile and tribe membership.");
        adminUser.uses(techTribes, "Add people, add tribes and manage tribe membership.");

        SoftwareSystem twitter = model.addSoftwareSystem(Location.External, "Twitter", "twitter.com");
        techTribes.uses(twitter, "Gets profile information and tweets from.");

        SoftwareSystem gitHub = model.addSoftwareSystem(Location.External, "GitHub", "github.com");
        techTribes.uses(gitHub, "Gets information about public code repositories from.");

        SoftwareSystem blogs = model.addSoftwareSystem(Location.External, "Blogs", "RSS and Atom feeds");
        techTribes.uses(blogs, "Gets content using RSS and Atom feeds from.");

        Container webApplication = techTribes.addContainer("Web Application", "Allows users to view people, tribes, content, events, jobs, etc from the local tech, digital and IT sector.", "Apache Tomcat 7.x");
        Container contentUpdater = techTribes.addContainer("Content Updater", "Updates profiles, tweets, GitHub repos and content on a scheduled basis.", "Standalone Java 7 application");
        Container relationalDatabase = techTribes.addContainer("Relational Database", "Stores people, tribes, tribe membership, talks, events, jobs, badges, GitHub repos, etc.", "MySQL 5.5.x");
        Container noSqlStore = techTribes.addContainer("NoSQL Data Store", "Stores content from RSS/Atom feeds (blog posts) and tweets.", "MongoDB 2.2.x");
        Container fileSystem = techTribes.addContainer("File System", "Stores search indexes.", "Local disk");

        anonymousUser.uses(webApplication, "View people, tribes (businesses, communities and interest groups), content, events, jobs, etc from the local tech, digital and IT sector.");
        authenticatedUser.uses(webApplication, "Manage user profile and tribe membership.");
        adminUser.uses(webApplication, "Add people, add tribes and manage tribe membership.");

        webApplication.uses(relationalDatabase, "Reads from and writes data to");
        webApplication.uses(noSqlStore, "Reads from");
        webApplication.uses(fileSystem, "Reads from");

        contentUpdater.uses(relationalDatabase, "Reads from and writes data to");
        contentUpdater.uses(noSqlStore, "Reads from and writes data to");
        contentUpdater.uses(fileSystem, "Writes to");
        contentUpdater.uses(twitter, "Gets profile information and tweets from.");
        contentUpdater.uses(gitHub, "Gets information about public code repositories from.");
        contentUpdater.uses(blogs, "Gets content using RSS and Atom feeds from.");

        writeToFile(workspace);
    }

}
