<div class="sectionHeading">Code</div>

<p>
    Some of the locals like to get their hands dirty with a bit of coding but these aren't your typical 9-5'ers. Take a look at what they've been up to...
</p>

<hr />

<c:forEach var="reposForCoder" items="${reposByCoder}">
    <div class="row person">
        <div class="span1">
            <a href="/people/${reposForCoder.contentSource.shortName}"><img src="${reposForCoder.contentSource.profileImageUrl}" alt="Profile image" class="profileImage" /></a>
        </div>
        <div class="span10">
            <a href="/people/${reposForCoder.contentSource.shortName}">${reposForCoder.contentSource.name}</a>
            <c:if test="${not empty reposForCoder.contentSource.profile}">
            <p>
                ${reposForCoder.contentSource.profile}
            </p>
            </c:if>

            <table class="table table-striped" style="margin-top: 32px;">
            <tbody>
            <c:forEach var="repo" items="${reposForCoder.gitHubRepositories}">
            <tr>
                <td style="border-top: none;"><a href="${repo.url}" target="_blank"><img src="/static/img/techtribes/github.png" alt="GitHub" class="profileImageSmall" title="GitHub" /></a>&nbsp;<a href="${repo.url}" target="_blank">${repo.name}</a></td>
                <td style="border-top: none;" width="66%">${repo.description}</td>
            </tr>
            </c:forEach>
            </tbody>
            </table>
        </div>
        <div class="span1">
            &nbsp;
        </div>
    </div>
</c:forEach>