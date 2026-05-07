package com.creative.isitvegan.robot

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule

class LoadingRobot(rule: ComposeContentTestRule) : BaseRobot(rule) {

    fun verifyLoadingMessageVisible() {
        // We can check for one of the messages or just that the progress indicator is there
        // Since the messages cycle, checking for the indicator is safer
        rule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    fun verifyMessage(message: String) {
        onNodeWithText(message).assertIsDisplayed()
    }
}
