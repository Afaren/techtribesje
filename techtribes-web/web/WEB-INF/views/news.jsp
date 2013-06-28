<div class="newsSection">
    <div class="sectionHeading">News</div>

    <%@ include file="/WEB-INF/fragments/news.jspf" %>

    <div class="pagingLinks">
        <c:if test="${currentPage > 1}">
            <a href="/news/${currentPage-1}">&lt; Newer</a> |
        </c:if>
        Page ${currentPage}
        <c:if test="${currentPage < maxPage}">
            | <a href="/news/${currentPage+1}">Older &gt;</a>
        </c:if>
    </div>
</div>