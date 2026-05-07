package com.creative.isitvegan.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule

/**
 * RobotRunner provides helper functions to initialize and run Robots
 * in a clean, readable DSL style.
 */

/**
 * Launches a robot of type [T]. 
 * T must have a constructor that accepts [ComposeContentTestRule].
 */
inline fun <reified T : BaseRobot> launchRobot(
    rule: ComposeContentTestRule,
    crossinline block: T.() -> Unit
): T {
    val constructor = T::class.java.getConstructor(ComposeContentTestRule::class.java)
    val robot = constructor.newInstance(rule)
    robot.apply(block)
    return robot
}
