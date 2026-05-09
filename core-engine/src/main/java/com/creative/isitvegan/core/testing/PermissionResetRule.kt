package com.creative.isitvegan.core.testing

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class PermissionResetRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                val instrumentation = InstrumentationRegistry.getInstrumentation()
                val device = UiDevice.getInstance(instrumentation)
                val packageName = instrumentation.targetContext.packageName
                // Reset permissions BEFORE the test starts (and before Activity launches)
                device.executeShellCommand("pm reset-permissions $packageName")
                base.evaluate()
            }
        }
    }
}
