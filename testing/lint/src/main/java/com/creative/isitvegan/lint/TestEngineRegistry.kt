package com.creative.isitvegan.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API

class TestEngineRegistry : IssueRegistry() {
    override val issues = listOf(TestEngineDetector.ISSUE)
    override val api: Int = CURRENT_API
    override val minApi: Int = 10 // works with older lint versions
}
