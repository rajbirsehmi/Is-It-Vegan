package com.creative.isitvegan.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UImportStatement
import org.jetbrains.uast.UElement

class TestEngineDetector : Detector(), SourceCodeScanner {

    companion object {
        private val FORBIDDEN_CLASSES = listOf(
            "androidx.compose.ui.test.junit4.ComposeContentTestRule",
            "androidx.compose.ui.test.junit4.ComposeTestRule",
            "androidx.compose.ui.test.junit4.createComposeRule",
            "androidx.compose.ui.test.junit4.createAndroidComposeRule"
        )

        val ISSUE = Issue.create(
            id = "UseTestEngineOnly",
            briefDescription = "Forbidden use of raw testing APIs",
            explanation = "Testing APIs like `ComposeContentTestRule` should only be used inside the `:testing:engine` module. Use the established Robots and RobotRunner instead.",
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.ERROR,
            implementation = Implementation(
                TestEngineDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UImportStatement::class.java, UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                val importString = node.importReference?.asSourceString() ?: return
                if (FORBIDDEN_CLASSES.any { importString.contains(it) }) {
                    if (!isInsideEngineModule(context)) {
                        context.report(ISSUE, node, context.getLocation(node), "Use the testing engine instead of importing raw testing APIs")
                    }
                }
            }

            override fun visitCallExpression(node: UCallExpression) {
                val methodName = node.methodName ?: return
                if (methodName == "createComposeRule" || methodName == "createAndroidComposeRule") {
                    if (!isInsideEngineModule(context)) {
                        context.report(ISSUE, node, context.getLocation(node), "Use the testing engine instead of calling raw testing APIs")
                    }
                }
            }
        }
    }

    private fun isInsideEngineModule(context: JavaContext): Boolean {
        // Simple check based on the file path or package
        val path = context.file.absolutePath.replace("\\", "/")
        return path.contains("/testing/engine/")
    }
}
