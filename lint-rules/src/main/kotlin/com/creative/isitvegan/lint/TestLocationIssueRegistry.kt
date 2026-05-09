package com.creative.isitvegan.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

class TestLocationIssueRegistry : IssueRegistry() {
    override val issues = listOf(TestLocationDetector.ISSUE)
    override val api: Int = CURRENT_API
    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Creative",
        feedbackUrl = "https://github.com/creative/isitvegan/issues",
        contact = "https://github.com/creative/isitvegan"
    )
}
