plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

// 开发：./gradlew :shared:check
// 测试：同上，commonTest + androidUnitTest
// 发布：./gradlew publishModule
tasks.register("publishModule") {
    group = "kmp-demo"
    description = "发布 shared 到 mavenLocal，并把 XCFramework 拷到 dist/"
    dependsOn(":shared:publishToMavenLocal", ":shared:publishXcframeworkToDist")
}
