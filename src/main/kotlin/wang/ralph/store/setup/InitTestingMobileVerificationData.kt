package wang.ralph.store.setup

import org.jetbrains.exposed.sql.SchemaUtils
import wang.ralph.store.models.utils.MobileVerifications

fun initTestingMobileVerificationData() {
    SchemaUtils.drop(MobileVerifications)
    SchemaUtils.create(MobileVerifications)
}
