task_branch = "${TEST_BRANCH_NAME}"
def branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()
currentBuild.displayName = "$branch_cutted"
base_git_url = "https://github.com/wallensteinMorgan/KotlinAT.git"

node {
    withEnv(["branch=${branch_cutted}", "base_url=${base_git_url}"]) {
        stage("Checkout Branch") {
            if (!"$branch_cutted".contains("main")) {
                try {
                    getProject("$base_git_url", "$branch_cutted")
                } catch (err) {
                    echo "Failed get branch $branch_cutted"
                    throw ("${err}")
                }
            } else {
                echo "Current branch is main"
            }
        }

        try {
            parallel getTestStages(["apiTests", "uiTests"])
        } finally {
            stage ("Allure") {
                generateAllure()
            }
        }
    }
}

def getTestStages(testTags) {
    def stages = [:]
    testTags.each { tag ->
        stages["${tag}"] = {
            runTestWithTag(tag)
        }
    }
    return stages
}

def runTestWithTag(String tag) {
    try {
        echo "Running ${tag} tests"

        // Проверяем есть ли gradlew, если нет - используем системный gradle
        if (fileExists('gradlew')) {
            sh """
                chmod +x ./gradlew
                ./gradlew clean ${tag}
            """
        } else {
            echo "gradlew not found, using system gradle"
            sh "gradle clean ${tag}"
        }
    } catch (err) {
        echo "Some tests failed in ${tag}: ${err}"
        currentBuild.result = 'UNSTABLE'
    }
}

def getProject(String repo, String branch) {
    cleanWs()
    checkout scm: [
            $class: 'GitSCM',
            branches: [[name: branch]],
            userRemoteConfigs: [[url: repo]]
    ]
}

def generateAllure() {
    allure([
            includeProperties: true,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'build/allure-results']]
    ])
}