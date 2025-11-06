task_branch = "${TEST_BRANCH_NAME}"
def branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()
currentBuild.displayName = "$branch_cutted"

pipeline {
    agent any

    stages {
        stage('Checkout and Verify') {
            steps {
                cleanWs()
                checkout scm

                script {
                    echo "=== WORKSPACE VERIFICATION ==="
                    sh """
                        pwd
                        ls -la
                        echo "Gradle wrapper exists: ${fileExists('gradlew')}"
                        echo "Gradle bat exists: ${fileExists('gradlew.bat')}"
                        echo "Gradle folder exists: ${fileExists('gradle/wrapper')}"
                    """
                }
            }
        }

        stage('Setup Gradle') {
            steps {
                sh """
                    chmod +x ./gradlew
                    ./gradlew --version || echo "Gradle version check completed"
                """
            }
        }

        stage('Clean Project') {
            steps {
                sh './gradlew clean'
            }
        }

        stage('Run Tests in Parallel') {
            parallel {
                stage('API Tests') {
                    steps {
                        runTestWithTag("apiTests")
                    }
                }
                stage('UI Tests') {
                    steps {
                        runTestWithTag("uiTests")
                    }
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/**/*.xml'

            script {
                sh 'mkdir -p build/allure-results || true'

                sh 'find . -name "allure-results" -type d | head -5 || echo "No allure results directories found"'

                allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'build/allure-results']]
                ])
            }

            echo "=== BUILD COMPLETED ==="
            script {
                if (currentBuild.result == 'UNSTABLE') {
                    echo "Some tests failed but build continued"
                } else if (currentBuild.result == 'FAILURE') {
                    echo "Build failed"
                } else {
                    echo "Build successful"
                }
            }
        }
    }
}

def runTestWithTag(String tag) {
    try {
        echo "Running ${tag} tests"

        sh """
            ./gradlew ${tag} \
                -Dallure.results.directory=build/allure-results \
                --no-daemon \
                --stacktrace \
                --info
        """

    } catch (err) {
        echo "Some tests failed in ${tag}: ${err}"
        currentBuild.result = 'UNSTABLE'

        sh 'mkdir -p build/allure-results || true'
    }
}

