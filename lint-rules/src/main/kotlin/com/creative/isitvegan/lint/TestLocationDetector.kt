package com.creative.isitvegan.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UImportStatement

class TestLocationDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(UImportStatement::class.java)

    override fun createUastHandler(context: JavaContext): UElementHandler = object : UElementHandler() {
        override fun visitImportStatement(node: UImportStatement) {
            val importPath = node.importReference?.asRenderString() ?: return
            
            // Restricted packages
            val isRestricted = (importPath.startsWith("androidx.compose.ui.test") || 
                              importPath.startsWith("dagger.hilt.android.testing") ||
                              importPath.startsWith("androidx.test.espresso")) &&
                              !importPath.endsWith("HiltAndroidTest") // Allow the annotation
            
            if (isRestricted) {
                val filePath = context.file.path.replace("\\", "/")
                // Allow only in core-engine
                if (!filePath.contains("/core-engine/")) {
                    context.report(
                        ISSUE,
                        node,
                        context.getLocation(node),
                        "Test related code ($importPath) should only be used within the :core-engine module."
                    )
                }
            }
        }
    }

    companion object {
        val ISSUE = Issue.create(
            id = "TestCodeInWrongLocation",
            briefDescription = "Test related code used outside of engine",
            explanation = "To maintain the Robot pattern and abstraction, all raw test/compose-test calls must reside in the :core-engine module. Feature modules should use the provided Robots.",
            category = Category.CORRECTNESS,
            priority = 8,
            severity = Severity.ERROR,
            implementation = Implementation(TestLocationDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }
}
