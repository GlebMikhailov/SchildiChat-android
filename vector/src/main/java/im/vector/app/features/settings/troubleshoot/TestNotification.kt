/*
 * Copyright 2018 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package im.vector.app.features.settings.troubleshoot

import android.content.Context
import im.vector.app.core.resources.StringProvider
import im.vector.app.core.utils.startNotificationSettingsIntent
import im.vector.app.features.notifications.utils.NotificationUtils
import im.vector.lib.strings.CommonStrings
import javax.inject.Inject

/**
 * Checks if notifications can be displayed and clicked by the user.
 */
class TestNotification @Inject constructor(
        private val context: Context,
        private val notificationUtils: NotificationUtils,
        private val stringProvider: StringProvider
) :
        TroubleshootTest(CommonStrings.settings_troubleshoot_test_notification_title) {

    override fun perform(testParameters: TestParameters) {
        // Display the notification right now
        notificationUtils.commonUtils.displayDiagnosticNotification()
        description = stringProvider.getString(CommonStrings.settings_troubleshoot_test_notification_notice)

        quickFix = object : TroubleshootQuickFix(CommonStrings.open_settings) {
            override fun doFix() {
                startNotificationSettingsIntent(context, testParameters.activityResultLauncher)
            }
        }

        status = TestStatus.WAITING_FOR_USER
    }

    override fun onNotificationClicked() {
        description = stringProvider.getString(CommonStrings.settings_troubleshoot_test_notification_notification_clicked)
        quickFix = null
        status = TestStatus.SUCCESS
    }
}
