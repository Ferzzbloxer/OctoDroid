package com.gh4a.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HtmlUtilsTest {

    @Test
    public void relativeLinkInRootReadme__doesNotContainDoubleSlash() {
        String html = "<a href=\"CONTRIBUTING.md\">contributing</a>";
        String result = HtmlUtils.rewriteRelativeUrls(html, "owner", "repo", "main", "");

        assertThat(result, equalTo(
                "<a href=\"https://github.com/owner/repo/blob/main/CONTRIBUTING.md\">contributing</a>"));
    }

    @Test
    public void relativeLinkInSubfolder__resolvesRelativeToFolder() {
        String html = "<a href=\"other.md\">other</a>";
        String result = HtmlUtils.rewriteRelativeUrls(html, "owner", "repo", "main", "docs");

        assertThat(result, equalTo(
                "<a href=\"https://github.com/owner/repo/blob/main/docs/other.md\">other</a>"));
    }

    @Test
    public void rootRelativeLink__isResolvedAgainstRepoRoot() {
        String html = "<a href=\"/CONTRIBUTING.md\">contributing</a>";
        String result = HtmlUtils.rewriteRelativeUrls(html, "owner", "repo", "main", "docs");

        assertThat(result, equalTo(
                "<a href=\"https://github.com/owner/repo/blob/main/CONTRIBUTING.md\">contributing</a>"));
    }

    @Test
    public void relativeImageInRootReadme__usesRawGithubusercontentDomain() {
        String html = "<img src=\"logo.png\">";
        String result = HtmlUtils.rewriteRelativeUrls(html, "owner", "repo", "main", "");

        assertThat(result, equalTo(
                "<img src=\"https://raw.githubusercontent.com/owner/repo/main/logo.png\">"));
    }

    @Test
    public void absoluteLink__isLeftUntouched() {
        String html = "<a href=\"https://example.com/page\">page</a>";
        String result = HtmlUtils.rewriteRelativeUrls(html, "owner", "repo", "main", "");

        assertThat(result, equalTo(html));
    }

    @Test
    public void anchorLink__isLeftUntouched() {
        String html = "<a href=\"#section\">section</a>";
        String result = HtmlUtils.rewriteRelativeUrls(html, "owner", "repo", "main", "");

        assertThat(result, equalTo(html));
    }
}
