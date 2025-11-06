// Получаем имя ветки, если параметр TEST_BRANCH_NAME не задан — используем main
def task_branch = env.TEST_BRANCH_NAME ?: "main"
def branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()

currentBuild.displayName = branch_cutted
def base_git_url = "https://github.com/wallensteinMorgan/KotlinAT.git"

node {
    withEnv(["branch=${branch_cutted}", "base_url=${base_git_url}"]) {

        stage("Debug Workspace") {
            echo "Workspace: ${env.WORKSPACE}"
            // Для Windows
            bat "dir \"${env.WORKSPACE}\""
        }

        stage("Checkout Branch") {
            if (!branch_cutted.contains("main")) {
                try {
                    getProject(base_git_url, branch_cutted)
                } catch (err) {
                    echo "Failed to get branch ${branch_cutted}"
                    error("${err}")
                }
            } else {
                echo "Current branch is main"
            }
        }

        stage("Run Tests") {
            parallel getTestStages(["apiTests", "uiTests"])
        }

        stage("Allure Report") {
            generateAllure()
        }
    }
}

def getTestStages(testTags) {
    def stages = [:]
    testTags.each { tag ->
        stages[tag] = {
            runTestWithTag(tag)
        }
    }
    return stages
}

def runTestWithTag(String tag) {
    try {
        // На Windows используем gradlew.bat
        bat """
            cd /d "${env.WORKSPACE}"
            gradlew.bat ${tag}
        """
    } finally {
        echo "Finished tests for ${tag}"
    }
}

def getProject(String repo, String branch) {
    cleanWs()
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              userRemoteConfigs: [[url: repo]]])
}

def generateAllure() {
    allure([
            includeProperties: true,
            jdk              : '',
            properties       : [],
            reportBuildPolicy: 'ALWAYS',
            results          : [[path: 'build/allure-results']]
    ])
}