<div class="contentSection">
    <div class="sectionHeading">Blog entries, etc</div>

    <%@ include file="/WEB-INF/fragments/newsFeedEntries.jspf" %>

    <div class="pagingLinks">
        <c:if test="${currentPage > 1}">
            <a href="/content/${currentPage-1}">&lt; Newer</a> |
        </c:if>
        Page ${currentPage}
        <c:if test="${currentPage < maxPage}">
            | <a href="/content/${currentPage+1}">Older &gt;</a>
        </c:if>
    </div>

    <script src="/static/js/highlight-content-by-same-author.js"></script>
</div>